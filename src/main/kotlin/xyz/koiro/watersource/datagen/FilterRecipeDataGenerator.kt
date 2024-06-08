package xyz.koiro.watersource.datagen

import net.minecraft.data.DataOutput
import net.minecraft.fluid.Fluids
import net.minecraft.recipe.Ingredient
import xyz.koiro.watersource.WaterSource
import xyz.koiro.watersource.data.FilterRecipeData
import xyz.koiro.watersource.datagen.provider.FilterRecipeDataProvider
import xyz.koiro.watersource.world.fluid.ModFluids
import xyz.koiro.watersource.world.tag.ModTags

class FilterRecipeDataGenerator(output: DataOutput) : FilterRecipeDataProvider(output) {
    override fun addData(adder: DataAdder<FilterRecipeData>) {
        adder.add(
            WaterSource.identifier("water_to_purified_water"),
            FilterRecipeData(
                Fluids.WATER,
                ModFluids.PURIFIED_WATER,
                Ingredient.fromTag(ModTags.Item.PURIFICATION_STRAINER)
            )
        )
    }
}