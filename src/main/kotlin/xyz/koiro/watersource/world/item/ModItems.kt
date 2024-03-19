package xyz.koiro.watersource.world.item

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import xyz.koiro.watersource.AutoGenData
import xyz.koiro.watersource.WaterSource

object ModItems {
    @AutoGenData(enLang = "Natural Filter", cnLang = "自然滤网")
    val NATURAL_FILTER = registerItem("natural_filter", Filter(FabricItemSettings().maxCount(1)))

    @AutoGenData(enLang = "Paper Filter", cnLang = "纸滤网")
    val PAPER_FILTER = registerItem("paper_filter", Filter(FabricItemSettings().maxCount(1)))
    fun active() {}
    private fun registerItem(registryName: String, item: Item): Item {
        return Registry.register(Registries.ITEM, Identifier(WaterSource.MODID, registryName), item)
    }

    fun reflectAutoGenDataItems(): Iterable<Pair<Item, AutoGenData>> =
        ModItems::class.java.getDeclaredFields().filter {
            val item = it.get(null) as? Item
            val isItem = item != null
            val isAutoGenData = it.getAnnotation(AutoGenData::class.java) != null
            isItem && isAutoGenData
        }.map {
            val item = it.get(null) as Item
            val autoGenData = it.getAnnotation(AutoGenData::class.java)
            Pair(item, autoGenData)
        }
}