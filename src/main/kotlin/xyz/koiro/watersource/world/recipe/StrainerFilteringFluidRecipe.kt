package xyz.koiro.watersource.world.recipe

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.fluid.Fluid
import net.minecraft.inventory.RecipeInputInventory
import net.minecraft.item.ItemStack
import net.minecraft.network.RegistryByteBuf
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.book.CraftingRecipeCategory
import net.minecraft.registry.Registries
import net.minecraft.registry.RegistryWrapper
import xyz.koiro.watersource.api.storage.getOrCreateFluidStorageData
import xyz.koiro.watersource.api.storage.modifyFluidStorage
import xyz.koiro.watersource.api.identifier
import xyz.koiro.watersource.api.toIdentifier
import xyz.koiro.watersource.world.item.Strainer

class StrainerFilteringFluidRecipe(
    val inFluid: Fluid, val outFluid: Fluid,
    strainer: Ingredient
) : StrainerFilteringRecipe(CraftingRecipeCategory.MISC, strainer) {
    override fun matchInput(stack: ItemStack): Boolean {
        return stack.getOrCreateFluidStorageData()?.fluid == inFluid
    }

    override fun getCost(ctx: Ctx): Int {
        return ctx.input.getOrCreateFluidStorageData()?.let { fluidStorageData ->
            (ctx.strainer.item as? Strainer)?.calCostDamage(ctx.strainer, fluidStorageData.amount)
        } ?: 0
    }

    override fun craft(inventory: RecipeInputInventory, lookup: RegistryWrapper.WrapperLookup?): ItemStack {
        getInputAndStrainer(inventory)?.let { ctx ->
            val input = ctx.input

            val output = input.copy()
            output.modifyFluidStorage { _, fluidStorageData ->
                fluidStorageData.fluid = outFluid
            }
            return output
        }
        return ItemStack.EMPTY
    }


    override fun getSerializer(): RecipeSerializer<*> {
        return ModRecipes.STRAINER_FILTERING_FLUID_SERIALIZER
    }

    class Serializer : StrainerFilteringRecipe.Serializer<StrainerFilteringFluidRecipe>() {
        val codec = RecordCodecBuilder.mapCodec { instance ->
            val grouped =
                instance.group(
                    Codec.STRING.fieldOf("fluidIn")
                        .forGetter<StrainerFilteringFluidRecipe> { it.inFluid.identifier().toString() },
                    Codec.STRING.fieldOf("fluidOut")
                        .forGetter<StrainerFilteringFluidRecipe> { it.outFluid.identifier().toString() },
                    Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("strainer")
                        .forGetter<StrainerFilteringFluidRecipe> { it.strainer }
                )
            return@mapCodec grouped.apply(instance) { fluidInId, fluidOutId, strainer ->
                StrainerFilteringFluidRecipe(
                    Registries.FLUID.get(fluidInId.toIdentifier()),
                    Registries.FLUID.get(fluidOutId.toIdentifier()),
                    strainer
                )
            }
        }

        override fun codec(): MapCodec<StrainerFilteringFluidRecipe> {
            return codec
        }

        override fun read(buf: RegistryByteBuf): StrainerFilteringFluidRecipe {
            val inId = buf.readString()
            val outId = buf.readString()
            val strainer = Ingredient.PACKET_CODEC.decode(buf)
            return StrainerFilteringFluidRecipe(Registries.FLUID.get(inId.toIdentifier()), Registries.FLUID.get(outId.toIdentifier()), strainer)
        }

        override fun write(buf: RegistryByteBuf, recipe: StrainerFilteringFluidRecipe) {
            buf.writeString(recipe.inFluid.identifier().toString())
            buf.writeString(recipe.outFluid.identifier().toString())
            Ingredient.PACKET_CODEC.encode(buf, recipe.strainer)
        }
    }
}