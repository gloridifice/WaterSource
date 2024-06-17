@file:Suppress("UnstableApiUsage")

package xyz.koiro.watersource.data

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.ListCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.minecraft.component.ComponentChanges
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.registry.tag.FluidTags
import net.minecraft.registry.tag.TagKey
import net.minecraft.server.network.ServerPlayerEntity
import java.util.Optional
import kotlin.jvm.optionals.getOrNull
import kotlin.random.Random

class HydrationData(
    val level: Int?,
    val saturation: Int?,
    val dryLevel: Int?,
    val matchMode: MatchMode = MatchMode.All,
    val matchList: ArrayList<IMatch>,
    val effects: ArrayList<StatusEffectObject>?,
) {
    companion object {
        val CODEC: Codec<HydrationData> = RecordCodecBuilder.mapCodec { instance ->
            val group = instance.group(
                Codec.INT.optionalFieldOf("level").forGetter<HydrationData> { Optional.ofNullable(it.level) },
                Codec.INT.optionalFieldOf("saturation").forGetter<HydrationData> { Optional.ofNullable(it.saturation) },
                Codec.INT.optionalFieldOf("dryLevel").forGetter<HydrationData> { Optional.ofNullable(it.dryLevel) },
                Codec.STRING.lenientOptionalFieldOf("match_mode", MatchMode.All.name)
                    .forGetter<HydrationData> { it.matchMode.name },
                Registries.ITEM.codec.optionalFieldOf("item").forGetter<HydrationData> {
                    Optional.ofNullable(it.matchList.find { it is ItemMatch }?.let { (it as ItemMatch).item })
                },
                Registries.FLUID.codec.optionalFieldOf("fluid").forGetter<HydrationData> {
                    Optional.ofNullable(it.matchList.find { it is FluidMatch }?.let { (it as FluidMatch).fluid })
                },
                TagKey.codec(RegistryKeys.ITEM).optionalFieldOf("item_tag").forGetter<HydrationData> {
                    Optional.ofNullable(it.matchList.find { it is ItemTagMatch }?.let { (it as ItemTagMatch).key })
                },
                TagKey.codec(RegistryKeys.FLUID).optionalFieldOf("fluid_tag").forGetter<HydrationData> {
                    Optional.ofNullable(it.matchList.find { it is FluidTagMatch }?.let { (it as FluidTagMatch).key })
                },
                ComponentChanges.CODEC.optionalFieldOf("components").forGetter<HydrationData> {
                    Optional.ofNullable(it.matchList.find { it is ComponentMatch }
                        ?.let { (it as ComponentMatch).components })
                },
                ListCodec(StatusEffectObject.CODEC, 0, Int.MAX_VALUE).optionalFieldOf("effects")
                    .forGetter<HydrationData> { Optional.ofNullable(it.effects) }
            )
            group.apply(instance) { level, saturation, dryLevel, matchMode, item, fluid, itemTag, fluidTag, components, effects ->
                val matchList = arrayListOf<IMatch>()
                item.ifPresent { matchList.add(ItemMatch(it)) }
                fluid.ifPresent { matchList.add(FluidMatch(it)) }
                itemTag.ifPresent { matchList.add(ItemTagMatch(it)) }
                fluidTag.ifPresent { matchList.add(FluidTagMatch(it)) }
                components.ifPresent { matchList.add(ComponentMatch(it)) }

                HydrationData(
                    level.getOrNull(),
                    saturation.getOrNull(),
                    dryLevel.getOrNull(),
                    MatchMode.valueOf(matchMode),
                    matchList,
                    effects.getOrNull()?.let { ArrayList(it) }
                )
            }
        }.codec()
    }

    fun isDry(): Boolean {
        return dryLevel != null && dryLevel > 0
    }

    fun applyEffectsToPlayer(player: ServerPlayerEntity, multiplier: Int = 1) {
        effects?.forEach { pEffect ->
            val effectInstance = StatusEffectInstance(
                RegistryEntry.of(pEffect.effect),
                (pEffect.durationInSec * 50).toInt(),
                pEffect.amplifier
            )
            if (Random.Default.nextFloat() < pEffect.probability)
                player.addStatusEffect(
                    StatusEffectInstance(
                        effectInstance.effectType,
                        effectInstance.duration * multiplier,
                        effectInstance.amplifier
                    )
                )
        }
    }

    fun match(itemStack: ItemStack?): Boolean {
        return when (matchMode) {
            MatchMode.All -> matchList.all { it.match(itemStack) }
            MatchMode.Any -> matchList.any { it.match(itemStack) }
        }
    }

    fun match(fluid: Fluid?): Boolean {
        return when (matchMode) {
            MatchMode.All -> matchList.all { it.match(fluid) }
            MatchMode.Any -> matchList.any { it.match(fluid) }
        }
    }

    class ComponentMatch(val components: ComponentChanges) : IMatch {
        override fun match(itemStack: ItemStack?): Boolean {
            return itemStack?.componentChanges == components
        }

        override fun match(fluid: Fluid?): Boolean = false
    }

    class ItemMatch(val item: Item) : IMatch {
        override fun match(itemStack: ItemStack?): Boolean {
            return itemStack?.isOf(item) ?: false
        }

        override fun match(fluid: Fluid?): Boolean = false
    }

    class FluidMatch(val fluid: Fluid) : IMatch {
        override fun match(itemStack: ItemStack?): Boolean {
            val context = ContainerItemContext.withConstant(itemStack)
            val storage = context.find(FluidStorage.ITEM)
            val fluid = storage?.first()?.resource?.fluid
            return this.fluid == fluid
        }

        override fun match(fluid: Fluid?): Boolean {
            return this.fluid == fluid
        }
    }

    class ItemTagMatch(val key: TagKey<Item>) : IMatch {
        override fun match(itemStack: ItemStack?): Boolean {
            return itemStack?.isIn(key) ?: false
        }

        override fun match(fluid: Fluid?): Boolean {
            return false
        }
    }

    class FluidTagMatch(val key: TagKey<Fluid>) : IMatch {
        override fun match(itemStack: ItemStack?): Boolean {
            val context = ContainerItemContext.withConstant(itemStack)
            val storage = context.find(FluidStorage.ITEM)
            val fluid = storage?.first()?.resource?.fluid
            return fluid?.isIn(key) ?: false
        }

        override fun match(fluid: Fluid?): Boolean {
            return fluid?.isIn(key) ?: false
        }
    }

    interface IMatch {
        fun match(itemStack: ItemStack?): Boolean
        fun match(fluid: Fluid?): Boolean
    }

    class StatusEffectObject(
        val effect: StatusEffect,

        // 0 = I, 1 = II...
        val amplifier: Int,
        val durationInSec: Float,
        // 0.0 ~ 1.0
        val probability: Float
    ) {
        companion object {
            val CODEC = RecordCodecBuilder.mapCodec {
                val group = it.group(
                    Registries.STATUS_EFFECT.codec.fieldOf("id").forGetter<StatusEffectObject> { it.effect },
                    Codec.INT.lenientOptionalFieldOf("amplifier", 0).forGetter<StatusEffectObject> { it.amplifier },
                    Codec.FLOAT.lenientOptionalFieldOf("duration", 1f)
                        .forGetter<StatusEffectObject> { it.durationInSec },
                    Codec.FLOAT.lenientOptionalFieldOf("probability", 1f)
                        .forGetter<StatusEffectObject> { it.probability }
                );
                group.apply(it) { id, amp, dur, pro ->
                    StatusEffectObject(id, amp, dur, pro)
                }
            }.codec()
        }
    }

    enum class MatchMode {
        All,
        Any
    }
}