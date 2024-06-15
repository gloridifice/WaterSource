package xyz.koiro.watersource.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.minecraft.fluid.Fluid
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Ingredient
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import xyz.koiro.watersource.api.serialize.IngredientFormat
import xyz.koiro.watersource.identifier

class FilterRecipeData(
    val inputFluid: Fluid,
    val outputFluid: Fluid,
    val strainer: Ingredient,
) {

    fun match(fluidIn: Fluid, strainerStack: ItemStack): Boolean {
        return fluidIn == this.inputFluid && strainer.test(strainerStack)
    }

    fun format(): Format {
        return Format(
            IngredientFormat.fromIngredient(strainer),
            inputFluid.identifier().toString(),
            outputFluid.identifier().toString()
        )
    }

    constructor(format: Format) : this(
        Registries.FLUID.get(Identifier.tryParse(format.inputFluid)),
        Registries.FLUID.get(Identifier.tryParse(format.outputFluid)),
        format.strainer.toIngredient()
    )


    @Serializable
    class Format(
        val strainer: IngredientFormat,
        @SerialName("input_fluid")
        val inputFluid: String,
        @SerialName("output_fluid")
        val outputFluid: String
    )
}