@file:Suppress("UnstableApiUsage")

package xyz.koiro.watersource.data

import com.google.gson.annotations.SerializedName
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier

class HydrationData(
    val level: Int,
    val saturation: Int,
    val matchMode: MatchMode,
    val matchList: ArrayList<IMatch>,
    val format: Format
) {

    constructor(format: Format) : this(
        format.level ?: 0,
        format.saturation ?: 0,
        format.matchMode ?: MatchMode.All,
        arrayListOf<IMatch>(),
        format
    ){
        format.item?.let {
            matchList.add(ItemMatch(Registries.ITEM.get(Identifier.tryParse(it))))
        }
        format.fluid?.let {
            matchList.add(FluidMatch(Registries.FLUID.get(Identifier.tryParse(it))))
        }
        format.itemTag?.let {
            matchList.add(ItemTagMatch(TagKey.of(RegistryKeys.ITEM, Identifier.tryParse(it))))
        }
        format.fluidTag?.let {
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

    class ItemMatch(private val item: Item) : IMatch {
        override fun match(itemStack: ItemStack?): Boolean {
            return itemStack?.isOf(item) ?: false
        }

        override fun match(fluid: Fluid?): Boolean = false
    }

    class FluidMatch(private val fluid: Fluid) : IMatch {
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

    class ItemTagMatch(private val key: TagKey<Item>) : IMatch {
        override fun match(itemStack: ItemStack?): Boolean {
            return itemStack?.isIn(key) ?: false
        }

        override fun match(fluid: Fluid?): Boolean {
            return false
        }
    }

    class FluidTagMatch(private val key: TagKey<Fluid>) : IMatch {
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

    data class Format(
        val level: Int?,
        val saturation: Int?,

        @SerializedName("match_mode")
        val matchMode: MatchMode?,

        val item: String?,
        val fluid: String?,
        @SerializedName("item_tag")
        val itemTag: String?,
        @SerializedName("fluid_tag")
        val fluidTag: String?,
    )

    enum class MatchMode {
        @SerializedName("all")
        All,

        @SerializedName("any")
        Any
    }
}