package xyz.koiro.watersource.world.item

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup.ItemApiProvider
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage
import net.fabricmc.fabric.api.transfer.v1.storage.Storage
import net.minecraft.item.Item
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import xyz.koiro.watersource.AutoGenData
import xyz.koiro.watersource.WaterSource

object ModItems {
    @AutoGenData(enLang = "Natural Strainer", cnLang = "自然滤网")
    val NATURAL_STRAINER = registerItem(
        "natural_strainer",
        Strainer(FabricItemSettings().maxCount(1).maxDamage(14))
    )

    @AutoGenData(enLang = "Paper Strainer", cnLang = "纸滤网")
    val PAPER_STRAINER = registerItem(
        "paper_strainer",
        Strainer(FabricItemSettings().maxCount(1).maxDamage(8))
    )

    //todo test
    @AutoGenData(enLang = "Leather Water Bag", cnLang = "皮水袋")
    val LEATHER_WATER_BAG = registerItem(
        "leather_water_bag",
        DrinkableContainer(FabricItemSettings().maxCount(1), 1000)
    )

    fun active() {
    }

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