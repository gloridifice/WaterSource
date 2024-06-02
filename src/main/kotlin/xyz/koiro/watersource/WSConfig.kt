package xyz.koiro.watersource

import kotlinx.serialization.Serializable
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import xyz.koiro.watersource.data.HydrationData
import xyz.koiro.watersource.world.effect.ModStatusEffects

object WSConfig {
    @Serializable
    class Format(
        val exhaustion: ExhaustionFormat = ExhaustionFormat(),
        val punishment: PunishmentFormat = PunishmentFormat()
    )

    @Serializable
    class ExhaustionFormat(
        val multiplier: Float = 1f,

        val sprint: Float = 0.05f,
        val jump: Float = 0.4f,
        val healthReward: Float = 2f,

        val thirtyPerSecondRate: Float = 0.3f,
        val thirtyPerSecondOffset: Float = 0.5f,

        val thirtyMultiplierRate: Float = 0.4f,
        val thirtyMultiplierOffset: Float = 1f,
    )

    @Serializable
    class PunishmentFormat(
        val enableLowWaterPunishment: Boolean = true,
        val effectDuration: Int = 50,
        val lightInLowWater: LowWaterPunishmentObject = LowWaterPunishmentObject().apply {
            effectsInDifficulty[WaterSource.ModDifficulty.NORMAL] = arrayListOf(
                EffectObject(StatusEffects.SLOWNESS),
                EffectObject(StatusEffects.WEAKNESS)
            )
            effectsInDifficulty[WaterSource.ModDifficulty.HARD] = arrayListOf(
                EffectObject(StatusEffects.SLOWNESS, 1),
                EffectObject(StatusEffects.WEAKNESS, 1)
            )
        },
        val heavyInLowWater: LowWaterPunishmentObject = LowWaterPunishmentObject().apply {
            effectsInDifficulty[WaterSource.ModDifficulty.EASY] = arrayListOf(
                EffectObject(StatusEffects.SLOWNESS),
                EffectObject(StatusEffects.WEAKNESS, 2)
            )
            effectsInDifficulty[WaterSource.ModDifficulty.NORMAL] = arrayListOf(
                EffectObject(StatusEffects.SLOWNESS, 1),
                EffectObject(StatusEffects.WEAKNESS, 2)
            )
            effectsInDifficulty[WaterSource.ModDifficulty.HARD] = arrayListOf(
                EffectObject(StatusEffects.SLOWNESS, 2),
                EffectObject(StatusEffects.WEAKNESS, 2)
            )
        }
    )

    @Serializable
    class LowWaterPunishmentObject(
        val enable: Boolean = true,
        val effectsInDifficulty: HashMap<WaterSource.ModDifficulty, List<EffectObject>> = HashMap()
    ) {
        fun getEffectList(duration: Int, difficulty: WaterSource.ModDifficulty): List<StatusEffectInstance> {
            if (!enable) return emptyList()
            val raw = effectsInDifficulty[difficulty]
            return raw?.map {
                StatusEffectInstance(
                    Registries.STATUS_EFFECT.get(Identifier.tryParse(it.id)),
                    duration,
                    it.amplifier
                )
            } ?: emptyList()
        }
    }

    @Serializable
    data class EffectObject(
        val id: String,
        val amplifier: Int = 0
    ) {
        constructor(effectType: StatusEffect, amplifier: Int = 0) : this(effectType.identifier().toString(), amplifier)

        companion object {
            fun fromInstance(instance: StatusEffectInstance): EffectObject {
                return EffectObject(instance.effectType.identifier().toString(), instance.amplifier)
            }
        }
    }

    const val UNIT_DRINK_VOLUME = 250L //mB
    var format = Format()

    fun applyWaterLevelRestoreMultiplier(value: Int, mul: Int): Int {
        return value * (1 + (mul - 1) / 2)
    }

    fun getWaterThirstyProbabilityEffect(): HydrationData.ProbabilityStatusEffectInstance =
        HydrationData.ProbabilityStatusEffectInstance(
            0.8f,
            StatusEffectInstance(ModStatusEffects.THIRSTY, 1200)
        )

    object Exhaustion {
        val config
            get() = format.exhaustion
        val multiplier
            get() = format.exhaustion.multiplier
        val sprint
            get() = format.exhaustion.sprint
        val jump
            get() = format.exhaustion.jump
        val healthReward
            get() = format.exhaustion.healthReward

        fun thirstyPerSecond(amplifier: Int): Float {
            return config.thirtyPerSecondOffset + (amplifier + 1) * config.thirtyPerSecondRate
        }

        fun thirstyMultiplier(amplifier: Int): Float {
            return config.thirtyMultiplierOffset + (1f + amplifier.toFloat()) * config.thirtyMultiplierRate
        }
    }

    object Punishment {
        private val config
            get() = format.punishment

        fun getPunishmentStatusEffectsLight(difficulty: WaterSource.ModDifficulty): Iterable<StatusEffectInstance> {
            return config.lightInLowWater.getEffectList(config.effectDuration, difficulty);
        }

        fun getPunishmentStatusEffectsHeavy(difficulty: WaterSource.ModDifficulty): Iterable<StatusEffectInstance> {
            return config.heavyInLowWater.getEffectList(config.effectDuration, difficulty);
        }
    }
}

