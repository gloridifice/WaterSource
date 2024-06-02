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
    id: Identifier, strainer: Ingredient
) : StrainerFilteringRecipe(id, CraftingRecipeCategory.MISC, strainer) {
    override fun matchInput(stack: ItemStack): Boolean {
        return stack.getOrCreateFluidStorageData()?.fluid == inFluid
    }

    override fun getCost(ctx: Ctx): Int {
        return ctx.input.getOrCreateFluidStorageData()?.let { fluidStorageData ->
            (ctx.strainer.item as? Strainer)?.calCostDamage(ctx.strainer, fluidStorageData.amount)
        } ?: 0
    }

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

    override fun getSerializer(): RecipeSerializer<*> {
        return ModRecipes.STRAINER_FILTERING_FLUID_SERIALIZER
    }

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