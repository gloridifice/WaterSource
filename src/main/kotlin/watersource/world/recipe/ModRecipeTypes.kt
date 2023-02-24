package watersource.world.recipe

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate
import thedarkcolour.kotlinforforge.forge.registerObject
import watersource.WaterSource
import watersource.world.recipe.type.WaterLevelRecipe

object ModRecipeTypes {

    val REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, WaterSource.ID)


    val WATER_LEVEL by registry<WaterLevelRecipe>("water_level")

    fun <T : Recipe<*>?> registry(name: String): ObjectHolderDelegate<RecipeType<T>> {
        return REGISTRY.registerObject(name) { RecipeType.simple(ResourceLocation(WaterSource.ID, name)) }
    }

}