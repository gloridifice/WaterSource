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
import xyz.koiro.watersource.api.SerializeUtils
import xyz.koiro.watersource.api.getOrCreateFluidStorageData
import xyz.koiro.watersource.api.modifyFluidStorage
import xyz.koiro.watersource.identifier
import xyz.koiro.watersource.world.item.Strainer
import kotlin.math.cos

class StrainerFilteringItemRecipe(
    val input: Ingredient,
    val output: ItemStack,
    val cost: Int,
    id: Identifier,
    strainer: Ingredient
) : StrainerFilteringRecipe(id, CraftingRecipeCategory.MISC, strainer) {
    override fun matchInput(stack: ItemStack): Boolean {
        return input.test(stack)
    }

    override fun getCost(ctx: Ctx): Int {
        return cost
    }

    override fun craft(inventory: RecipeInputInventory, registryManager: DynamicRegistryManager): ItemStack {
        return output.copy()
    }

    override fun getSerializer(): RecipeSerializer<*> {
        return ModRecipes.STRAINER_FILTERING_ITEM_SERIALIZER
    }

    class Serializer : RecipeSerializer<StrainerFilteringItemRecipe> {
        override fun read(id: Identifier, json: JsonObject): StrainerFilteringItemRecipe {
            val input = Ingredient.fromJson(json.get("input"))
            val output = SerializeUtils.jsonObjectToItemStack(json.getAsJsonObject("output"))
            val cost = json.get("cost").asInt
            val strainer = Ingredient.fromJson(json.get("strainer"))
            return StrainerFilteringItemRecipe(input, output!!, cost, id, strainer)
        }

        override fun read(id: Identifier, buf: PacketByteBuf): StrainerFilteringItemRecipe {
            val input = Ingredient.fromPacket(buf)
            val output = buf.readItemStack()
            val cost = buf.readInt()
            val strainer = Ingredient.fromPacket(buf)
            return StrainerFilteringItemRecipe(input, output, cost, id, strainer)
        }

        override fun write(buf: PacketByteBuf, recipe: StrainerFilteringItemRecipe) {
            recipe.input.write(buf)
            buf.writeItemStack(recipe.output)
            buf.writeInt(recipe.cost)
            recipe.strainer.write(buf)
        }
    }
}