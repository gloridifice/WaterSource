package xyz.koiro.watersource.world.fluid

import net.minecraft.fluid.Fluid
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import xyz.koiro.watersource.WaterSource

object ModFluids {
    val PURIFIED_WATER = regFluid("purified_water") { PurifiedWaterFluid.Still() }
    val FLOWING_PURIFIED_WATER = regFluid("flowing_purified_water") { PurifiedWaterFluid.Flowing() }

    fun active(){}
    fun regFluid(id: String, entry: () -> Fluid) : Fluid{
        return Registry.register(Registries.FLUID, WaterSource.identifier(id), entry.invoke())
    }
}