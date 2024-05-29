package xyz.koiro.watersource.world.recipe

import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import xyz.koiro.watersource.WaterSource

object ModRecipes {

    fun initialize(){
    }

    val STRAINER_FILTERING_FLUID_TYPE = registerRecipeType("strainer_filtering_fluid_type", StrainerFilteringFluidRecipe.Type())
    val STRAINER_FILTERING_FLUID_SERIALIZER = registerRecipeSerializer("strainer_filtering_fluid_serializer", StrainerFilteringFluidRecipe.Serializer())

    private fun registerRecipeType(registryName: String, recipeType: RecipeType<*>): RecipeType<*> {
        return Registry.register(Registries.RECIPE_TYPE, Identifier(WaterSource.MODID, registryName), recipeType)
    }

    private fun registerRecipeSerializer(registryName: String, recipeType: RecipeSerializer<*>): RecipeSerializer<*> {
        return Registry.register(Registries.RECIPE_SERIALIZER, Identifier(WaterSource.MODID, registryName), recipeType)
    }
}
