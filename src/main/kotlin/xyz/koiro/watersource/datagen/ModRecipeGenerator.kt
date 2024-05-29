package xyz.koiro.watersource.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.recipe.Ingredient
import net.minecraft.util.Identifier
import xyz.koiro.watersource.WaterSource
import xyz.koiro.watersource.datagen.recipe.StrainerFilteringFluidRecipeJsonBuilder
import xyz.koiro.watersource.world.fluid.ModFluids
import xyz.koiro.watersource.world.tag.ModTags
import java.util.function.Consumer

class ModRecipeGenerator(output: FabricDataOutput?) : FabricRecipeProvider(output) {
    override fun generate(exporter: Consumer<RecipeJsonProvider>?) {
        genSFFRecipe(
            exporter,
            WaterSource.identifier("water_to_purified_water"),
            Fluids.WATER,
            ModFluids.PURIFIED_WATER,
            Ingredient.fromTag(ModTags.Item.PURIFICATION_STRAINER)
        )
    }

    private fun genSFFRecipe(
        exporter: Consumer<RecipeJsonProvider>?,
        id: Identifier,
        inFluid: Fluid,
        outFluid: Fluid,
        strainer: Ingredient
    ) {
        StrainerFilteringFluidRecipeJsonBuilder(id, outFluid, inFluid, strainer).offerTo(exporter, id)
    }
}