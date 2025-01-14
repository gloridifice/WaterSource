package xyz.koiro.watersource.datagen

import net.minecraft.data.DataOutput
import net.minecraft.fluid.Fluids
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier
import xyz.koiro.watersource.WSConfig
import xyz.koiro.watersource.WaterSource
import xyz.koiro.watersource.data.HydrationData
import xyz.koiro.watersource.datagen.provider.HydrationDataProvider
import xyz.koiro.watersource.datagen.provider.addDryItemWithAutoId
import xyz.koiro.watersource.datagen.provider.addItemWithAutoId
import xyz.koiro.watersource.world.fluid.ModFluids
import xyz.koiro.watersource.world.item.ModItems

class HydrationDataGenerator(output: DataOutput) : HydrationDataProvider(output) {
    override fun addData(adder: DataAdder<HydrationData>) {
        // WaterSource -----------------------
        adder.add(
            WaterSource.identifier("fluid_water"),
            fluid(Fluids.WATER, 2, 0, WSConfig.getWaterThirstyProbabilityEffect())
        )
        adder.add(
            WaterSource.identifier("fluid_purified_water"),
            fluid(ModFluids.PURIFIED_WATER, 4, 12)
        )
        adder.addItemWithAutoId(ModItems.PURIFIED_WATER_BOTTLE, 4, 12)
        adder.addItemWithAutoId(ModItems.WATERMELON_JUICE, 3, 10)

        // Vanilla -----------------------
        adder.addItemWithAutoId(Items.APPLE, 1, 4)
        adder.addItemWithAutoId(Items.GOLDEN_APPLE, 2, 8)
        adder.addItemWithAutoId(Items.ENCHANTED_GOLDEN_APPLE, 4, 20)
        adder.addItemWithAutoId(Items.MUSHROOM_STEW, 1, 2)
        adder.addItemWithAutoId(Items.SUSPICIOUS_STEW, 1, 2)
        adder.addItemWithAutoId(Items.RABBIT_STEW, 1, 2)
        adder.addItemWithAutoId(Items.BEETROOT_SOUP, 1, 2)
        adder.addItemWithAutoId(Items.MELON_SLICE, 1, 3)
        adder.addItemWithAutoId(Items.GLISTERING_MELON_SLICE, 1, 3)
        adder.addItemWithAutoId(Items.SWEET_BERRIES, 1, 3)
        adder.addItemWithAutoId(Items.GLOW_BERRIES, 1, 3)
        adder.add(
            Identifier("item_potion_water"),
            item(Items.POTION, 2, 0, WSConfig.getWaterThirstyProbabilityEffect()).apply { matchList.add(HydrationData.NBTMatch(
                NbtCompound().apply { this.putString("Potion", "minecraft:water") }
            ))}
        )

        adder.addDryItemWithAutoId(Items.COOKIE, 1)
    }
}