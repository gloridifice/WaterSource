package xyz.koiro.watersource.data

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.fluid.Fluid
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Ingredient
import net.minecraft.registry.Registries

class FilterRecipeData(
    val inputFluid: Fluid,
    val outputFluid: Fluid,
    val strainer: Ingredient,
) {

    fun match(fluidIn: Fluid, strainerStack: ItemStack): Boolean {
        return fluidIn == this.inputFluid && strainer.test(strainerStack)
    }

    companion object {
        val CODEC: Codec<FilterRecipeData> =
            RecordCodecBuilder.mapCodec { instance ->
                val group = instance.group(
                    Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("strainer").forGetter<FilterRecipeData> { it.strainer },
                    Registries.FLUID.codec.fieldOf("input_fluid").forGetter<FilterRecipeData> { it.inputFluid },
                    Registries.FLUID.codec.fieldOf("output_fluid").forGetter<FilterRecipeData> { it.outputFluid },
                )
                group.apply(instance) { strainer, iF, oF ->
                    FilterRecipeData(iF, oF, strainer)
                }
            }.codec()
    }
}