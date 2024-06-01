package xyz.koiro.watersource

import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import xyz.koiro.watersource.data.HydrationData
import xyz.koiro.watersource.world.effect.ModStatusEffects

object WSConfig {
    const val UNIT_DRINK_VOLUME = 250L //mB

    //todo move other configs into this file
    fun applyWaterLevelRestoreMultiplier(value: Int, mul: Int): Int {
        return value * (1 + (mul - 1) / 2)
    }

    fun getWaterThirstyProbabilityEffect(): HydrationData.ProbabilityStatusEffectInstance =
        HydrationData.ProbabilityStatusEffectInstance(
            0.8f,
            StatusEffectInstance(ModStatusEffects.THIRSTY, 1200)
        )

    object Exhaustion {
        val SPRINT = 0.05f // per meter
        val JUMP = 0.4f
        val REWARD_HEALTH = 2f
        fun thirstyPerSecond(amplifier: Int): Float {
            return 0.5f + (amplifier + 1) * 0.3f
        }

        fun thirstyMultiplier(amplifier: Int): Float {
            return 1.4f + amplifier.toFloat() * 0.4f
        }
    }

    object Punishment {
        private val DURATION = 50;
        fun getPunishmentStatusEffectsSix(difficulty: WaterSource.ModDifficulty): Iterable<StatusEffectInstance> {
            return when (difficulty) {
                WaterSource.ModDifficulty.PEACEFUL -> listOf()
                WaterSource.ModDifficulty.EASY -> listOf()
                WaterSource.ModDifficulty.NORMAL -> listOf(
                    StatusEffectInstance(StatusEffects.SLOWNESS, DURATION),
                    StatusEffectInstance(StatusEffects.WEAKNESS, DURATION)
                )

                WaterSource.ModDifficulty.HARD -> listOf(
                    StatusEffectInstance(StatusEffects.SLOWNESS, DURATION, 1),
                    StatusEffectInstance(StatusEffects.WEAKNESS, DURATION, 1)
                )
            }
        }

        fun getPunishmentStatusEffectsZero(difficulty: WaterSource.ModDifficulty): Iterable<StatusEffectInstance> {
            return when (difficulty) {
                WaterSource.ModDifficulty.PEACEFUL -> listOf()
                WaterSource.ModDifficulty.EASY -> listOf(
                    StatusEffectInstance(StatusEffects.SLOWNESS, DURATION),
                    StatusEffectInstance(StatusEffects.WEAKNESS, DURATION, 2)
                )

                WaterSource.ModDifficulty.NORMAL -> listOf(
                    StatusEffectInstance(StatusEffects.SLOWNESS, DURATION, 1),
                    StatusEffectInstance(StatusEffects.WEAKNESS, DURATION, 2)
                )

                WaterSource.ModDifficulty.HARD -> listOf(
                    StatusEffectInstance(StatusEffects.SLOWNESS, DURATION, 2),
                    StatusEffectInstance(StatusEffects.WEAKNESS, DURATION, 2)
                )
            }
        }
    }
}

