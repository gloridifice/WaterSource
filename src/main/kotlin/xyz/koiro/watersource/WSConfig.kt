package xyz.koiro.watersource

import kotlinx.serialization.Serializable
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import xyz.koiro.watersource.data.HydrationData
import xyz.koiro.watersource.datagen.provider.HydrationDataProvider
import xyz.koiro.watersource.world.effect.ModStatusEffects
import kotlin.math.floor

object WSConfig {
    @Serializable
    class Format(
        val exhaustion: ExhaustionFormat = ExhaustionFormat(),
        val punishment: PunishmentFormat = PunishmentFormat(),
        val filtering: FilteringFormat = FilteringFormat(),
        val highWaterPlayerHealingIntervalSecond: Float = 2.5f,
        val enableDryFeature: Boolean = true,
        val enableDefaultDryData: Boolean = true,
    )

    @Serializable
    class FilteringFormat(
        val enableStrainerRecipe: Boolean = true
    )

    @Serializable
    class ExhaustionFormat(
        val multiplier: Float = 1f,

        val sprintCost: Float = 0.05f,
        val jumpCost: Float = 0.4f,
        val healthRewardCost: Float = 2f,

        val thirtyCostPerSecondRate: Float = 0.3f,
        val thirtyCostPerSecondOffset: Float = 0.5f,

        val thirtyCostMultiplierRate: Float = 0.4f,
        val thirtyCostMultiplierOffset: Float = 1f,
    )

    @Serializable
    class PunishmentFormat(
        val effectDuration: Int = 50,
        val lightPunishmentInLowWater: LowWaterPunishmentObject = LowWaterPunishmentObject().apply {
            effectsInNormal = arrayOf(
                EffectObject(StatusEffects.SLOWNESS),
                EffectObject(StatusEffects.WEAKNESS)
            ).map { it.toString() }
            effectsInHard = arrayOf(
                EffectObject(StatusEffects.SLOWNESS, 1),
                EffectObject(StatusEffects.WEAKNESS, 1)
            ).map { it.toString() }
        },
        val heavyPunishmentInLowWater: LowWaterPunishmentObject = LowWaterPunishmentObject().apply {
            effectsInEasy = arrayOf(
                EffectObject(StatusEffects.SLOWNESS),
                EffectObject(StatusEffects.WEAKNESS, 2)
            ).map { it.toString() }
            effectsInNormal = arrayOf(
                EffectObject(StatusEffects.SLOWNESS, 1),
                EffectObject(StatusEffects.WEAKNESS, 2)
            ).map { it.toString() }
            effectsInHard = arrayOf(
                EffectObject(StatusEffects.SLOWNESS, 2),
                EffectObject(StatusEffects.WEAKNESS, 2)
            ).map { it.toString() }
        }
    )

    @Serializable
    class LowWaterPunishmentObject(
        val enable: Boolean = true,

        var effectsInEasy: List<String> = emptyList(),
        var effectsInNormal: List<String> = emptyList(),
        var effectsInHard: List<String> = emptyList(),
    ) {
        fun getEffectList(duration: Int, difficulty: WaterSource.ModDifficulty): List<StatusEffectInstance> {
            if (!enable) return emptyList()
            val raw =
                when (difficulty) {
                    WaterSource.ModDifficulty.PEACEFUL -> emptyList()
                    WaterSource.ModDifficulty.EASY -> effectsInEasy.map { EffectObject.fromString(it) }.toList()
                    WaterSource.ModDifficulty.NORMAL -> effectsInNormal.map { EffectObject.fromString(it) }.toList()
                    WaterSource.ModDifficulty.HARD -> effectsInHard.map { EffectObject.fromString(it) }.toList()
                }
            return raw.map {
                StatusEffectInstance(
                    Registries.STATUS_EFFECT.get(Identifier.tryParse(it.id)),
                    duration,
                    it.amplifier
                )
            }
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

            fun fromString(str: String): EffectObject {
                val list = str.split("::")
                val id = list[0]
                val amplifier = list[1].toInt()
                return EffectObject(id, amplifier)
            }
        }

        override fun toString(): String {
            return "$id::$amplifier"
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

    fun getMoisturizingRatio(levelSum: Int): Float {
        return 1f / (1f + levelSum.toFloat() * 0.15f)
    }

    object Exhaustion {
        val config
            get() = format.exhaustion
        val multiplier
            get() = format.exhaustion.multiplier
        val sprint
            get() = format.exhaustion.sprintCost
        val jump
            get() = format.exhaustion.jumpCost
        val healthReward
            get() = format.exhaustion.healthRewardCost

        fun thirstyPerSecond(amplifier: Int): Float {
            return config.thirtyCostPerSecondOffset + (amplifier + 1) * config.thirtyCostPerSecondRate
        }

        fun thirstyMultiplier(amplifier: Int): Float {
            return config.thirtyCostMultiplierOffset + (1f + amplifier.toFloat()) * config.thirtyCostMultiplierRate
        }
    }

    object Punishment {
        private val config
            get() = format.punishment

        fun getPunishmentStatusEffectsLight(difficulty: WaterSource.ModDifficulty): Iterable<StatusEffectInstance> {
            return config.lightPunishmentInLowWater.getEffectList(config.effectDuration, difficulty);
        }

        fun getPunishmentStatusEffectsHeavy(difficulty: WaterSource.ModDifficulty): Iterable<StatusEffectInstance> {
            return config.heavyPunishmentInLowWater.getEffectList(config.effectDuration, difficulty);
        }
    }

    object Filtering {
        val config
            get() = format.filtering
    }

    fun getDefaultFoodDryData(itemStack: ItemStack): HydrationData? {
        if (format.enableDefaultDryData && itemStack.isFood) {
            itemStack.item.foodComponent?.hunger?.let {
                if (it > 1) {
                    return HydrationDataProvider.dryItem(itemStack.item, floor(it / 2f).toInt())
                }
            }
        }
        return null
    }
}

