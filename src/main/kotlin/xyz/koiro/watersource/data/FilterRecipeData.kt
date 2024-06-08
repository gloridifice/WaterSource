package xyz.koiro.watersource.data

import kotlinx.serialization.Serializable
import net.minecraft.fluid.Fluid
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Ingredient
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import xyz.koiro.watersource.api.serialize.IngredientFormat
import xyz.koiro.watersource.identifier

class FilterRecipeData(
    val inFluid: Fluid,
    val outFluid: Fluid,
    val strainer: Ingredient,
) {

    fun match(fluidIn: Fluid, strainerStack: ItemStack): Boolean {
        return fluidIn == this.inFluid && strainer.test(strainerStack)
    }

    fun format(): Format {
        return Format(
            IngredientFormat.fromIngredient(strainer),
            inFluid.identifier().toString(),
            outFluid.identifier().toString()
        )
    }

    constructor(format: Format) : this(
        Registries.FLUID.get(Identifier.tryParse(format.fluidIn)),
        Registries.FLUID.get(Identifier.tryParse(format.fluidOut)),
        format.strainer.toIngredient()
    )


    @Serializable
    class Format(
        val strainer: IngredientFormat,
        val fluidIn: String,
        val fluidOut: String
    )
}