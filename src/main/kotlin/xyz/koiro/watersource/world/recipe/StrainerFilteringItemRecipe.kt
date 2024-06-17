package xyz.koiro.watersource.world.recipe

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.inventory.RecipeInputInventory
import net.minecraft.item.ItemStack
import net.minecraft.network.RegistryByteBuf
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.book.CraftingRecipeCategory
import net.minecraft.registry.RegistryWrapper

class StrainerFilteringItemRecipe(
    val input: Ingredient,
    val output: ItemStack,
    val cost: Int,
    strainer: Ingredient
) : StrainerFilteringRecipe(CraftingRecipeCategory.MISC, strainer) {
    override fun matchInput(stack: ItemStack): Boolean {
        return input.test(stack)
    }

    override fun getCost(ctx: Ctx): Int {
        return cost
    }

    override fun craft(inventory: RecipeInputInventory?, lookup: RegistryWrapper.WrapperLookup?): ItemStack {
        return output.copy()
    }

    override fun getSerializer(): RecipeSerializer<*> {
        return ModRecipes.STRAINER_FILTERING_ITEM_SERIALIZER
    }

    class Serializer : StrainerFilteringRecipe.Serializer<StrainerFilteringItemRecipe>() {
        val codec = RecordCodecBuilder.mapCodec { instance ->
            val grouped =
                instance.group(
                    Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("input").forGetter<StrainerFilteringItemRecipe> {
                        it.input
                    },
                    ItemStack.CODEC.fieldOf("output").forGetter<StrainerFilteringItemRecipe> {
                        it.output
                    },
                    Codec.INT.fieldOf("cost").forGetter<StrainerFilteringItemRecipe> {
                        it.cost
                    },
                    Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("strainer")
                        .forGetter<StrainerFilteringItemRecipe> { it.strainer }
                )
            return@mapCodec grouped.apply(instance) { input, output, cost, strainer ->
                StrainerFilteringItemRecipe(
                    input, output, cost, strainer
                )
            }
        }

        override fun codec(): MapCodec<StrainerFilteringItemRecipe> {
            return codec
        }

        override fun read(buf: RegistryByteBuf): StrainerFilteringItemRecipe {
            val input = Ingredient.PACKET_CODEC.decode(buf)
            val output = ItemStack.PACKET_CODEC.decode(buf)
            val cost = buf.readInt()
            val strainer = Ingredient.PACKET_CODEC.decode(buf)
            return StrainerFilteringItemRecipe(input, output, cost, strainer)
        }

        override fun write(buf: RegistryByteBuf, recipe: StrainerFilteringItemRecipe) {
            Ingredient.PACKET_CODEC.encode(buf, recipe.input)
            ItemStack.PACKET_CODEC.encode(buf, recipe.output)
            buf.writeInt(recipe.cost)
            Ingredient.PACKET_CODEC.encode(buf, recipe.strainer)
        }
    }
}