package xyz.koiro.watersource.datagen

import net.minecraft.data.DataOutput
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.fluid.Fluids
import net.minecraft.item.Items
import xyz.koiro.watersource.WaterSource
import xyz.koiro.watersource.datagen.provider.HydrationDataProvider
import xyz.koiro.watersource.world.effect.ModStatusEffects

class HydrationDataGenerator(output: DataOutput): HydrationDataProvider(output) {
    override fun addData(adder: HydrationDataAdder) {
        adder.add(WaterSource.identifier("item_apple"), item(Items.APPLE, 1, 1))
        adder.add(WaterSource.identifier("fluid_water"), fluid(Fluids.WATER, 2, 0, StatusEffectInstance(ModStatusEffects.THIRSTY, 1200)))
    }
}