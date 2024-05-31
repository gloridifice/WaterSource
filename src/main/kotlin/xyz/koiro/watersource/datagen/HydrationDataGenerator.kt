package xyz.koiro.watersource.datagen

import net.minecraft.data.DataOutput
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.fluid.Fluids
import net.minecraft.item.Items
import xyz.koiro.watersource.WaterSource
import xyz.koiro.watersource.datagen.provider.HydrationDataProvider
import xyz.koiro.watersource.world.effect.ModStatusEffects
import xyz.koiro.watersource.world.fluid.ModFluids
import xyz.koiro.watersource.world.item.ModItems

class HydrationDataGenerator(output: DataOutput) : HydrationDataProvider(output) {
    override fun addData(adder: HydrationDataAdder) {
        // WaterSource -----------------------
        adder.add(
            WaterSource.identifier("fluid_water"),
            fluid(Fluids.WATER, 2, 0, StatusEffectInstance(ModStatusEffects.THIRSTY, 1200))
        )
        adder.add(
            WaterSource.identifier("fluid_purified_water"),
            fluid(ModFluids.PURIFIED_WATER, 4, 6)
        )
        adder.add(
            WaterSource.identifier("item_purified_water_bottle"),
            item(ModItems.PURIFIED_WATER_BOTTLE, 4, 6)
        )

        // Vanilla -----------------------
        adder.add(WaterSource.identifier("item_apple"), item(Items.APPLE, 1, 2))
    }
}