package xyz.koiro.watersource.world.item

import net.fabricmc.fabric.api.registry.FuelRegistry

object ModFuelRegister {
    fun initialize() {
        FuelRegistry.INSTANCE.add(ModItems.WASTE_STRAINER, 600)
    }
}