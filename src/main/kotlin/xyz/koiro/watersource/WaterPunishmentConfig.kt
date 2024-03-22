package xyz.koiro.watersource

import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects

object WaterPunishmentConfig {
    
    private val DURATION = 50;
    fun getPunishmentStatusEffectsSix(difficulty: WaterSource.ModDifficulty): Iterable<StatusEffectInstance>{
        return when(difficulty){
            WaterSource.ModDifficulty.PEACEFUL -> listOf()
            WaterSource.ModDifficulty.EASY -> listOf()
            WaterSource.ModDifficulty.NORMAL -> listOf(StatusEffectInstance(StatusEffects.SLOWNESS, DURATION), StatusEffectInstance(StatusEffects.WEAKNESS, DURATION))
            WaterSource.ModDifficulty.HARD -> listOf(StatusEffectInstance(StatusEffects.SLOWNESS, DURATION, 1), StatusEffectInstance(StatusEffects.WEAKNESS, DURATION, 1))
        }
    }

    fun getPunishmentStatusEffectsZero(difficulty: WaterSource.ModDifficulty): Iterable<StatusEffectInstance>{
        return when(difficulty){
            WaterSource.ModDifficulty.PEACEFUL -> listOf()
            WaterSource.ModDifficulty.EASY -> listOf(StatusEffectInstance(StatusEffects.SLOWNESS, DURATION), StatusEffectInstance(StatusEffects.WEAKNESS, DURATION, 2))
            WaterSource.ModDifficulty.NORMAL -> listOf(StatusEffectInstance(StatusEffects.SLOWNESS, DURATION, 1), StatusEffectInstance(StatusEffects.WEAKNESS, DURATION, 2))
            WaterSource.ModDifficulty.HARD -> listOf(StatusEffectInstance(StatusEffects.SLOWNESS, DURATION, 2), StatusEffectInstance(StatusEffects.WEAKNESS, DURATION, 2))
        }
    }
}