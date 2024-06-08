package xyz.koiro.watersource.data

import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.minecraft.resource.ResourceType

object ModResourceRegistries {
    const val HYDRATION_KEY = "hydration"
    const val FILTER_RECIPE_KEY = "filter_recipe"
    fun initialize(){
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(HydrationDataManager)
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(FilterRecipeDataManager)
    }
}