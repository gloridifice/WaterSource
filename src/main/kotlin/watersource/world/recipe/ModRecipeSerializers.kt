package watersource.world.recipe

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate
import thedarkcolour.kotlinforforge.forge.registerObject
import watersource.WaterSource
import watersource.world.recipe.serializer.WaterLevelRecipeSerializer

object ModRecipeSerializers {
    val REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, WaterSource.ID)

    val WATER_LEVEL by REGISTRY.registerObject("water_level") { WaterLevelRecipeSerializer() }

}