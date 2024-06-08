package xyz.koiro.watersource.datagen.provider

import net.minecraft.item.Item
import net.minecraft.util.Identifier
import xyz.koiro.watersource.data.HydrationData
import xyz.koiro.watersource.datagen.provider.HydrationDataProvider.Companion.dryItem
import xyz.koiro.watersource.datagen.provider.HydrationDataProvider.Companion.item
import xyz.koiro.watersource.identifier


fun ModDataProvider.DataAdder<HydrationData>.addItemWithAutoId(item: Item, level: Int, saturation: Int) {
    val itemId = item.identifier()
    dataMap[Identifier(itemId.namespace, "item_${itemId.path}")] = item(item, level, saturation)
}

fun ModDataProvider.DataAdder<HydrationData>.addDryItemWithAutoId(item: Item, dryLevel: Int) {
    val itemId = item.identifier()
    dataMap[Identifier(itemId.namespace, "dry_item_${itemId.path}")] = dryItem(item, dryLevel)
}
