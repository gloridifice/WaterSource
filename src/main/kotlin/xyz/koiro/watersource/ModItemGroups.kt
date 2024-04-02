package xyz.koiro.watersource

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.text.Text
import xyz.koiro.watersource.world.item.ModItems

object ModItemGroups {
    const val MAIN_ITEM_GROUP_TRANSLATION_KEY = "watersource.item_group.main"
    private val list = listOf<ItemStack>(
        ModItems.PAPER_STRAINER.simpleStack(),
        ModItems.NATURAL_STRAINER.simpleStack(),
        ModItems.LEATHER_WATER_BAG.simpleStack(),
        ModItems.PURIFIED_WATER_BUCKET.simpleStack(),
        ModItems.PURIFIED_WATTER_BOTTLE.simpleStack()
    )

    val MAIN_ITEM_GROUP = Registry.register(
        Registries.ITEM_GROUP, WaterSource.identifier("main"), FabricItemGroup.builder()
            .displayName(Text.translatable(MAIN_ITEM_GROUP_TRANSLATION_KEY))
            .entries { context, entries ->
                list.distinct().forEach {
                    entries.add(it)
                }
            }
            .icon { ModItems.PURIFIED_WATTER_BOTTLE.simpleStack() }
            .build()
    )

    fun active() {

    }
}