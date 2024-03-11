package xyz.koiro.watersource.data

import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.minecraft.resource.ResourceType

object ModResourceRegistries {
    fun initialize(){
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(HydrationDataManager.SERVER)
    }
}