package xyz.koiro.watersource

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.text.Text
import xyz.koiro.watersource.api.insertFluid
import xyz.koiro.watersource.world.fluid.ModFluids
import xyz.koiro.watersource.world.item.ModItems

object ModItemGroups {
    const val MAIN_ITEM_GROUP_TRANSLATION_KEY = "watersource.item_group.main"
    private val list = listOf<ItemStack>(
        ModItems.PURIFIED_WATER_BOTTLE.simpleStack(),
        ModItems.PURIFIED_WATER_BUCKET.simpleStack(),

        // Strainers
        ModItems.PAPER_STRAINER.simpleStack(),
        ModItems.NATURAL_STRAINER.simpleStack(),

        // Drink Containers
        ModItems.LEATHER_WATER_BAG_SMALL.simpleStack(),
        ModItems.LEATHER_WATER_BAG_MEDIUM.simpleStack(),
        ModItems.LEATHER_WATER_BAG_LARGE.simpleStack(),
        ModItems.WOODEN_CUP_EMPTY.simpleStack(),
        run {
            val stack = ItemStack(ModItems.WOODEN_CUP)
            stack.insertFluid(Fluids.WATER) { it.capacity }
            stack
        },
        run {
            val stack = ItemStack(ModItems.WOODEN_CUP)
            stack.insertFluid(ModFluids.PURIFIED_WATER) { it.capacity }
            stack
        },

        ModItems.RAW_POTTERY_CUP.simpleStack(),
        ModItems.POTTERY_CUP_EMPTY.simpleStack(),
        run {
            val stack = ItemStack(ModItems.POTTERY_CUP)
            stack.insertFluid(Fluids.WATER) { it.capacity }
            stack
        },
        run {
            val stack = ItemStack(ModItems.POTTERY_CUP)
            stack.insertFluid(ModFluids.PURIFIED_WATER) { it.capacity }
            stack
        },
    )

    val MAIN_ITEM_GROUP = Registry.register(
        Registries.ITEM_GROUP, WaterSource.identifier("main"), FabricItemGroup.builder()
            .displayName(Text.translatable(MAIN_ITEM_GROUP_TRANSLATION_KEY))
            .entries { context, entries ->
                list.distinct().forEach {
                    entries.add(it)
                }
            }
            .icon { ModItems.PURIFIED_WATER_BOTTLE.simpleStack() }
            .build()
    )

    fun active() {

    }
}