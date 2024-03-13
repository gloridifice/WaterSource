@file:Suppress("UnstableApiUsage")

package xyz.koiro.watersource.data

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier
import org.apache.logging.log4j.core.jmx.Server
import xyz.koiro.watersource.identifier

class HydrationData(
    val level: Int,
    val saturation: Int,
    val matchMode: MatchMode = MatchMode.All,
    val matchList: ArrayList<IMatch>,
    val effects: ArrayList<StatusEffectInstance>
) {
    val format: Format
    init {
        val item = (matchList.find { it is ItemMatch } as ItemMatch?)?.item?.identifier()?.toString()
        val fluid = (matchList.find { it is FluidMatch } as FluidMatch?)?.fluid?.identifier()?.toString()
        val itemTag = (matchList.find { it is ItemTagMatch } as ItemTagMatch?)?.key?.id?.toString()
        val fluidTag = (matchList.find { it is FluidTagMatch } as FluidTagMatch?)?.key?.id?.toString()
        val effectsObjs = effects.mapNotNull { effectInstance ->
            val id = effectInstance.effectType.identifier()
            id?.let {
                StatusEffectObject(it.toString(), effectInstance.amplifier, effectInstance.duration.toFloat() * 0.05f)
            }
        }
        format = Format(level, saturation, matchMode, item, fluid, itemTag, fluidTag, effectsObjs)
    }
    constructor(format: Format) : this(
        format.level ?: 0,
        format.saturation ?: 0,
        format.matchMode ?: MatchMode.All,
        arrayListOf<IMatch>(),
        arrayListOf<StatusEffectInstance>()
    ){
        fun valid(it: String?): String? = if(!it.isNullOrBlank() && it != "null") it else null
        valid(format.item)?.let {
            matchList.add(ItemMatch(Registries.ITEM.get(Identifier.tryParse(it))))
        }
        valid(format.fluid)?.let {
            matchList.add(FluidMatch(Registries.FLUID.get(Identifier.tryParse(it))))
        }
        valid(format.itemTag)?.let {
            matchList.add(ItemTagMatch(TagKey.of(RegistryKeys.ITEM, Identifier.tryParse(it))))
        }
        valid(format.fluidTag)?.let {
            matchList.add(FluidTagMatch(TagKey.of(RegistryKeys.FLUID, Identifier.tryParse(it))))
        }
        format.effects?.forEach{ obj ->
            val type = Registries.STATUS_EFFECT.get(Identifier.tryParse(obj.id))
            type?.let { effectType ->
                effects.add(StatusEffectInstance(effectType,  ((obj.duration ?: 0f) * 20f).toInt(), obj.amplifier ?: 0))
            }
        }
    }

    fun applyEffectsToPlayer(player: ServerPlayerEntity){
        effects.forEach { effectInstance ->
            player.addStatusEffect(StatusEffectInstance(effectInstance))
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

    @Serializable
    data class Format(
        val level: Int? = null,
        val saturation: Int? = null,

        @SerializedName("match_mode")
        @SerialName("match_mode")
        val matchMode: MatchMode? = null,

        val item: String? = null,
        val fluid: String? = null,

        @SerializedName("item_tag")
        @SerialName("item_tag")
        val itemTag: String? = null,

        @SerializedName("fluid_tag")
        @SerialName("fluid_tag")
        val fluidTag: String? = null,

        val effects: List<StatusEffectObject>?
    )

    @Serializable
    data class StatusEffectObject(
        val id: String?,

        // 0 = I, 1 = II...
        val amplifier: Int?,

        // s
        val duration: Float?
    )

    @Serializable
    enum class MatchMode {
        @SerializedName("all")
        @SerialName("all")
        All,

        @SerializedName("any")
        @SerialName("any")
        Any
    }
}