@file:Suppress("UnstableApiUsage")

package xyz.koiro.watersource.data

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import xyz.koiro.watersource.identifier

class HydrationData(
    val level: Int,
    val saturation: Int,
    val matchMode: MatchMode = MatchMode.All,
    val matchList: ArrayList<IMatch>,
) {

    val format: Format
    init {
        val item = (matchList.find { it is ItemMatch } as ItemMatch?)?.item?.identifier()?.toString()
        val fluid = (matchList.find { it is FluidMatch } as FluidMatch?)?.fluid?.identifier()?.toString()
        val itemTag = (matchList.find { it is ItemTagMatch } as ItemTagMatch?)?.key?.id?.toString()
        val fluidTag = (matchList.find { it is FluidTagMatch } as FluidTagMatch?)?.key?.id?.toString()
        format = Format(level, saturation, matchMode, item, fluid, itemTag, fluidTag)
    }
    constructor(format: Format) : this(
        format.level ?: 0,
        format.saturation ?: 0,
        format.matchMode ?: MatchMode.All,
        arrayListOf<IMatch>(),
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