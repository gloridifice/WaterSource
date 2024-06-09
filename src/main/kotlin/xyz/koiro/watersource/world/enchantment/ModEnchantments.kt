package xyz.koiro.watersource.world.enchantment

import net.minecraft.enchantment.Enchantment
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import xyz.koiro.watersource.WaterSource

object ModEnchantments {
    fun active(){}

    val MOISTURIZING = regEnchantment("moisturizing", MoisturizingEnchantment())
    fun regEnchantment(idPath: String, value: Enchantment): Enchantment{
       return Registry.register(Registries.ENCHANTMENT, WaterSource.identifier(idPath), value)
    }
}