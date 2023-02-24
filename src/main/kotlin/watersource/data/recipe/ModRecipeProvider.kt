package watersource.data.recipe

import net.minecraft.data.DataGenerator
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.tags.ItemTags
import net.minecraft.world.level.material.Fluids
import watersource.data.recipe.builder.WaterLevelRecipeBuilder
import watersource.world.level.item.ModItems
import watersource.world.recipe.type.WaterLevelRecipe
import java.util.function.Consumer

class ModRecipeProvider(generator: DataGenerator) : RecipeProvider(generator) {
    override fun buildCraftingRecipes(consumer: Consumer<FinishedRecipe>) {
        //ShapedRecipeBuilder.shaped(ModItems.WOODEN_CUP).pattern("x x").define('x', ItemTags.PLANKS).save(consumer)
        WaterLevelRecipeBuilder.fluid(Fluids.WATER, 6f, 0.5f).save(consumer)
    }
}