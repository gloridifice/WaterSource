package xyz.koiro.watersource.world.recipe

import com.google.gson.JsonObject
import net.minecraft.fluid.Fluid
import net.minecraft.inventory.RecipeInputInventory
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.recipe.SpecialCraftingRecipe
import net.minecraft.recipe.book.CraftingRecipeCategory
import net.minecraft.registry.DynamicRegistryManager
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.World
import xyz.koiro.watersource.api.getOrCreateFluidStorageData
import xyz.koiro.watersource.api.modifyFluidStorage
import xyz.koiro.watersource.identifier
import xyz.koiro.watersource.world.item.Strainer

class StrainerFilteringFluidRecipe(
    val inFluid: Fluid, val outFluid: Fluid,
    val id: Identifier, val strainer: Ingredient
) : SpecialCraftingRecipe(id, CraftingRecipeCategory.MISC) {
    fun getInputAndStrainer(inventory: RecipeInputInventory): Ctx? {
        if (inventory.size() == 2) {
            var strainerStack: ItemStack? = null
            var inputStack: ItemStack? = null
            var sIndex = 0
            var iIndex = 0

            for (i in 0..<inventory.size()) {
                val stack = inventory.getStack(i)
                if (this.strainer.test(strainerStack)) {
                    strainerStack = stack
                    sIndex = i
                }
                if (stack.getOrCreateFluidStorageData()?.fluid == inFluid) {
                    inputStack = stack
                    iIndex = i
                }
            }
            if (inputStack != null && strainerStack != null)
                return Ctx(strainerStack, inputStack, sIndex, iIndex)
        }
        return null;
    }

    override fun matches(inventory: RecipeInputInventory, world: World): Boolean {
        getInputAndStrainer(inventory)?.let { ctx ->
            val input = ctx.input
            val strainer = ctx.strainer

            input.getOrCreateFluidStorageData()?.let { fluidStorageData ->
                (strainer.item as? Strainer)?.calCostDamage(strainer, fluidStorageData.amount)?.let { dmg ->
                    return strainer.damage + dmg < strainer.maxDamage
                }
            }
        }
        return false
    }

    data class Ctx(val strainer: ItemStack, val input: ItemStack, val strainerIndex: Int, val inputIndex: Int)

    override fun craft(inventory: RecipeInputInventory, registryManager: DynamicRegistryManager): ItemStack? {
        getInputAndStrainer(inventory)?.let { ctx ->
            val input = ctx.input

            val output = input.copy()
            output.modifyFluidStorage { _, fluidStorageData ->
                fluidStorageData.copy(fluid = outFluid)
            }
            return output
        }
        return ItemStack.EMPTY
    }

    override fun getRemainder(inventory: RecipeInputInventory): DefaultedList<ItemStack> {
        val defaultedList = super.getRemainder(inventory)
        getInputAndStrainer(inventory)?.let { ctx ->
            ctx.input.getOrCreateFluidStorageData()?.let { fluidStorageData ->
                val remained = (ctx.strainer.item as? Strainer)?.useStrainer(
                    strainerStack = ctx.strainer,
                    volume = fluidStorageData.amount
                ) ?: ItemStack.EMPTY
                defaultedList[ctx.strainerIndex] = remained
            }
        }
        return defaultedList
    }

    override fun fits(width: Int, height: Int): Boolean {
        return true
    }

    override fun getOutput(registryManager: DynamicRegistryManager?): ItemStack {
        return ItemStack.EMPTY
    }

    override fun getId(): Identifier = id

    override fun getSerializer(): RecipeSerializer<*> {
        return ModRecipes.STRAINER_FILTERING_FLUID_SERIALIZER
    }

    override fun getType(): RecipeType<*> {
        return ModRecipes.STRAINER_FILTERING_FLUID_TYPE
    }

    class Type: RecipeType<StrainerFilteringFluidRecipe>

    class Serializer : RecipeSerializer<StrainerFilteringFluidRecipe> {
        override fun read(id: Identifier, json: JsonObject): StrainerFilteringFluidRecipe {
            val fI = Registries.FLUID.get(Identifier.tryParse(json.get("fluidIn").asString))
            val fO = Registries.FLUID.get(Identifier.tryParse(json.get("fluidOut").asString))
            val strainer = Ingredient.fromJson(json.get("strainer"))
            return StrainerFilteringFluidRecipe(fI, fO, id, strainer)
        }

        override fun read(id: Identifier, buf: PacketByteBuf): StrainerFilteringFluidRecipe {
            val fI = Registries.FLUID.get(Identifier.tryParse(buf.readString()))
            val fO = Registries.FLUID.get(Identifier.tryParse(buf.readString()))
            val strainer = Ingredient.fromPacket(buf)
            return StrainerFilteringFluidRecipe(fI, fO, id, strainer)
        }

        override fun write(buf: PacketByteBuf, recipe: StrainerFilteringFluidRecipe) {
            buf.writeString(recipe.inFluid.identifier().toString())
            buf.writeString(recipe.outFluid.identifier().toString())
            recipe.strainer.write(buf)
        }
    }
}