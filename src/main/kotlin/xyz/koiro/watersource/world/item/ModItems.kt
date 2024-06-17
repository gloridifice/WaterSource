package xyz.koiro.watersource.world.item

import net.minecraft.item.BlockItem
import net.minecraft.item.BucketItem
import net.minecraft.item.Item
import net.minecraft.item.Item.Settings
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import xyz.koiro.watersource.AutoGenItemData
import xyz.koiro.watersource.ModelType
import xyz.koiro.watersource.WaterSource
import xyz.koiro.watersource.world.block.ModBlocks
import xyz.koiro.watersource.world.fluid.ModFluids

object ModItems {
    @AutoGenItemData(enLang = "Natural Strainer", cnLang = "自然滤网")
    val NATURAL_STRAINER = registerItem(
        "natural_strainer",
        Strainer(Settings().maxCount(1).maxDamage(16))
    )

    @AutoGenItemData(enLang = "Compressed Strainer", cnLang = "压缩滤网")
    val COMPRESSED_STRAINER = registerItem(
        "compressed_strainer",
        Strainer(Settings().maxCount(1).maxDamage(80))
    )

    @AutoGenItemData(enLang = "Paper Strainer", cnLang = "纸滤网")
    val PAPER_STRAINER = registerItem(
        "paper_strainer",
        Strainer(Settings().maxCount(1).maxDamage(8))
    )

    @AutoGenItemData(enLang = "Waste Strainer", cnLang = "旧滤网")
    val WASTE_STRAINER = registerItem(
        "waste_strainer",
        Item(Settings())
    )

    @AutoGenItemData(enLang = "Leather Water Bag (Small)", cnLang = "皮水袋（小）")
    val LEATHER_WATER_BAG_SMALL = registerItem(
        "leather_water_bag_small",
        DrinkableContainer(Settings().maxCount(1), 1000)
    )

    @AutoGenItemData(enLang = "Leather Water Bag (Medium)", cnLang = "皮水袋（中）")
    val LEATHER_WATER_BAG_MEDIUM = registerItem(
        "leather_water_bag_medium",
        DrinkableContainer(Settings().maxCount(1), 2000)
    )

    @AutoGenItemData(enLang = "Leather Water Bag (Large)", cnLang = "皮水袋（大）")
    val LEATHER_WATER_BAG_LARGE = registerItem(
        "leather_water_bag_large",
        DrinkableContainer(Settings().maxCount(1), 4000, drinkVolumeMultiplier = 2)
    )

    @AutoGenItemData(enLang = "Wooden Cup", cnLang = "木杯子", modelType = ModelType.DontGen)
    val WOODEN_CUP: Item = registerItem(
        "wooden_cup",
        DrinkableContainer(
            Settings().maxCount(1),
            250,
            emptyContainerStack = { ItemStack(WOODEN_CUP_EMPTY) }
        )
    )

    @AutoGenItemData(enLang = "Wooden Cup", cnLang = "木杯子")
    val WOODEN_CUP_EMPTY: Item = registerItem(
        "wooden_cup_empty",
        EmptyFluidContainerItem(Settings()) { ItemStack(WOODEN_CUP) }
    )

    @AutoGenItemData(enLang = "Raw Pottery Cup", cnLang = "陶杯子（未烤制）")
    val RAW_POTTERY_CUP = registerItem("raw_pottery_cup", Item(Settings()))

    @AutoGenItemData(enLang = "Pottery Cup", cnLang = "陶杯子")
    val POTTERY_CUP_EMPTY: Item = registerItem(
        "pottery_cup_empty",
        EmptyFluidContainerItem(Settings()) { ItemStack(POTTERY_CUP) }
    )

    @AutoGenItemData(enLang = "Pottery Cup", cnLang = "陶杯子", modelType = ModelType.DontGen)
    val POTTERY_CUP: Item = registerItem(
        "pottery_cup",
        DrinkableContainer(
            Settings().maxCount(1),
            750,
            emptyContainerStack = { ItemStack(POTTERY_CUP_EMPTY) }
        )
    )

    @AutoGenItemData(enLang = "Purified Water Bucket", cnLang = "净水桶")
    val PURIFIED_WATER_BUCKET =
        registerItem("purified_water_bucket", BucketItem(ModFluids.PURIFIED_WATER, Settings().maxCount(1)))

    @AutoGenItemData(enLang = "Purified Water Bottle", cnLang = "净水瓶")
    val PURIFIED_WATER_BOTTLE =
        registerItem(
            "purified_water_bottle",
            DrinkOnceItem(Settings().maxCount(8), 32) { ItemStack(Items.GLASS_BOTTLE) }
        )

    val WOODEN_FILTER_BLOCK = registerItem(
        "wooden_filter",
        BlockItem(ModBlocks.WOODEN_FILTER, Settings())
    )

    val IRON_FILTER_BLOCK = registerItem(
        "iron_filter",
        BlockItem(ModBlocks.IRON_FILTER, Settings())
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