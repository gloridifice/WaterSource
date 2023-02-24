package watersource.world.recipe

import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import watersource.world.recipe.type.WaterLevelRecipe

object WaterLevelRecipeUtil {
    fun getRecipe(level: Level, itemStack: ItemStack) : WaterLevelRecipe?{
        val waterLevelRecipes = level.recipeManager.getAllRecipesFor(ModRecipeTypes.WATER_LEVEL)
        for (recipe in waterLevelRecipes){
            if (recipe.match(itemStack)){
                return recipe
            }
        }
        return null
    }
}