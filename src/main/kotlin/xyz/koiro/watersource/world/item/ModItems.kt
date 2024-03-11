package xyz.koiro.watersource.world.item

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import xyz.koiro.watersource.WaterSource

object ModItems {
    val NEW_ITEM = registerItem("new_item", Item(FabricItemSettings()))

    fun active(){}
    fun registerItem(registryName: String, item: Item): Item {
        return Registry.register(Registries.ITEM, Identifier(WaterSource.MODID, registryName), item)
    }
}