package xyz.koiro.watersource.world.effect

import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectCategory
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import xyz.koiro.watersource.WaterSource

object ModStatusEffects {
    val THIRSTY = regStatusEffect("thirsty") { CustomEffect(StatusEffectCategory.HARMFUL, 0xE8A655) };

    fun active(){

    }
    fun regStatusEffect(id: String, effect: () -> StatusEffect): StatusEffect {
        return Registry.register(Registries.STATUS_EFFECT, WaterSource.identifier(id), effect())
    }
}