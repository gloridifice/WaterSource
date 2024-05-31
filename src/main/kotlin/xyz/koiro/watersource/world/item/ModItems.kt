package xyz.koiro.watersource.world.item

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.BucketItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import xyz.koiro.watersource.AutoGenItemData
import xyz.koiro.watersource.WaterSource
import xyz.koiro.watersource.world.fluid.ModFluids

object ModItems {
    @AutoGenItemData(enLang = "Natural Strainer", cnLang = "自然滤网")
    val NATURAL_STRAINER = registerItem(
        "natural_strainer",
        Strainer(FabricItemSettings().maxCount(1).maxDamage(14))
    )

    @AutoGenItemData(enLang = "Paper Strainer", cnLang = "纸滤网")
    val PAPER_STRAINER = registerItem(
        "paper_strainer",
        Strainer(FabricItemSettings().maxCount(1).maxDamage(8))
    )

    @AutoGenItemData(enLang = "Waste Strainer", cnLang = "旧滤网")
    val WASTE_STRAINER = registerItem(
        "waste_strainer",
        Item(FabricItemSettings())
    )

    @AutoGenItemData(enLang = "Leather Water Bag", cnLang = "皮水袋")
    val LEATHER_WATER_BAG = registerItem(
        "leather_water_bag",
        DrinkableContainer(FabricItemSettings().maxCount(1), 1000)
    )

    @AutoGenItemData(enLang = "Wooden Cup", cnLang = "木杯子")
    val WOODEN_CUP: Item = registerItem(
        "wooden_cup",
        DrinkableContainer(
            FabricItemSettings().maxCount(1),
            250,
            emptyContainerStack = { ItemStack(WOODEN_CUP_EMPTY) }
        )
    )

    @AutoGenItemData(enLang = "Wooden Cup", cnLang = "木杯子")
    val WOODEN_CUP_EMPTY: Item = registerItem(
        "wooden_cup_empty",
        EmptyDrinkableContainer(FabricItemSettings()) { ItemStack(WOODEN_CUP) }
    )

    @AutoGenItemData(enLang = "Purified Water Bucket", cnLang = "净水桶")
    val PURIFIED_WATER_BUCKET =
        registerItem("purified_water_bucket", BucketItem(ModFluids.PURIFIED_WATER, FabricItemSettings().maxCount(1)))

    @AutoGenItemData(enLang = "Purified Water Bottle", cnLang = "净水瓶")
    val PURIFIED_WATER_BOTTLE =
        registerItem(
            "purified_water_bottle",
            DrinkOnceItem(FabricItemSettings().maxCount(16), 32) { ItemStack(Items.GLASS_BOTTLE) }
        )


    fun active() {
    }

    private fun registerItem(registryName: String, item: Item): Item {
        return Registry.register(Registries.ITEM, Identifier(WaterSource.MODID, registryName), item)
    }

    fun reflectAutoGenDataItems(): Iterable<Pair<Item, AutoGenItemData>> =
        ModItems::class.java.getDeclaredFields().filter {
            val item = it.get(null) as? Item
            val isItem = item != null
            val isAutoGenData = it.getAnnotation(AutoGenItemData::class.java) != null
            isItem && isAutoGenData
        }.map {
            val item = it.get(null) as Item
            val autoGenData = it.getAnnotation(AutoGenItemData::class.java)
            Pair(item, autoGenData)
        }
}