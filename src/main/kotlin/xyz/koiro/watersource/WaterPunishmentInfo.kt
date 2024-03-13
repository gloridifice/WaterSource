package xyz.koiro.watersource

import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects

object WaterPunishmentInfo {
    fun getPunishmentStatusEffectsSix(difficulty: WaterSource.ModDifficulty): Iterable<StatusEffectInstance>{
        return when(difficulty){
            WaterSource.ModDifficulty.PEACEFUL -> listOf()
            WaterSource.ModDifficulty.EASY -> listOf()
            WaterSource.ModDifficulty.NORMAL -> listOf(StatusEffectInstance(StatusEffects.SLOWNESS, 1), StatusEffectInstance(StatusEffects.WEAKNESS, 1))
            WaterSource.ModDifficulty.HARD -> listOf(StatusEffectInstance(StatusEffects.SLOWNESS, 1, 1), StatusEffectInstance(StatusEffects.WEAKNESS, 1, 1))
        }
    }

    fun getPunishmentStatusEffectsZero(difficulty: WaterSource.ModDifficulty): Iterable<StatusEffectInstance>{
        return when(difficulty){
            WaterSource.ModDifficulty.PEACEFUL -> listOf()
            WaterSource.ModDifficulty.EASY -> listOf(StatusEffectInstance(StatusEffects.SLOWNESS, 1), StatusEffectInstance(StatusEffects.WEAKNESS, 2))
            WaterSource.ModDifficulty.NORMAL -> listOf(StatusEffectInstance(StatusEffects.SLOWNESS, 1, 1), StatusEffectInstance(StatusEffects.WEAKNESS, 1, 2))
            WaterSource.ModDifficulty.HARD -> listOf(StatusEffectInstance(StatusEffects.SLOWNESS, 1, 2), StatusEffectInstance(StatusEffects.WEAKNESS, 1, 2))
        }
    }
}