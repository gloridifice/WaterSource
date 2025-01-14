@file:Suppress("UnstableApiUsage")

package xyz.koiro.watersource.world.item

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage
import net.fabricmc.fabric.api.transfer.v1.storage.Storage
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.world.World
import xyz.koiro.watersource.*
import xyz.koiro.watersource.api.storage.FluidStorageData
import xyz.koiro.watersource.api.storage.getOrCreateFluidStorageData
import xyz.koiro.watersource.api.storage.insertFluid
import xyz.koiro.watersource.api.storage.setFluidStorage
import kotlin.math.*

open class FluidContainerItem(
    settings: Settings,
    val capacity: Long,
    val emptyContainer: (() -> ItemStack)? = null
) : Item(settings.maxDamage((capacity / WSConfig.UNIT_DRINK_VOLUME).toInt())) {
    init {
        FluidStorage.ITEM.registerForItems(::getFluidStorage, this)
    }

    fun canInsert(itemStack: ItemStack, fluid: Fluid, amount: Long, currentData: FluidStorageData): Boolean {
        return fluid == currentData.fluid || currentData.isBlank()
    }

    fun onFluidDataChanged(stack: ItemStack, playerEntity: PlayerEntity): ItemStack {
        stack.getOrCreateFluidStorageData()?.let {
            val amount = it.amount
            val damage = ceil((1 - amount.toDouble() / capacity.toDouble()) * maxDamage.toDouble()).toInt()
            if (it.isBlank()){
                if (emptyContainer != null){
                    return emptyContainer.invoke()
                }
            } else {
                stack.damage = min(damage, maxDamage)
            }
        }
        return stack
    }

    open fun setStorageData(stack: ItemStack, data: FluidStorageData): ItemStack {
        stack.setFluidStorage(data)
        return if (data.isBlank()) emptyContainer?.invoke() ?: stack else stack
    }

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        if (!world.isClient()) {
            val hit = user.raycast(3.0, 0.05f, true)
            val handItem = user.getStackInHand(hand)
            if (hit is BlockHitResult) {
                val blockState = world.getBlockState(hit.blockPos)
                if (blockState.fluidState.fluid == Fluids.WATER) {
                    handItem.insertFluid(Fluids.WATER) { it.capacity }
                    user.setStackInHand(hand, onFluidDataChanged(handItem, user))
                    return TypedActionResult.success(handItem)
                }
            }
        }
        return super.use(world, user, hand)
    }

    open fun getFluidStorage(stack: ItemStack, context: ContainerItemContext): Storage<FluidVariant> {
        val nbt = stack.getSubNbt("FluidStorage")
        val ret = object : SingleFluidStorage() {
            override fun getCapacity(variant: FluidVariant?): Long {
                return (stack.item as FluidContainerItem).capacity
            }

            override fun onFinalCommit() {
                super.onFinalCommit()
                val nbtCompound = NbtCompound()
                this.writeNbt(nbtCompound)
                stack.setSubNbt("FluidStorage", nbtCompound)
            }
        }
        if (nbt != null) {
            ret.readNbt(nbt)
        }
        return ret
    }

    override fun appendTooltip(
        stack: ItemStack,
        world: World?,
        tooltip: MutableList<Text>,
        context: TooltipContext
    ) {
        stack.getOrCreateFluidStorageData()?.let { storageData ->
            tooltip.add(storageData.getDisplayText().styled { it.withColor(Formatting.GRAY) })
        }
        super.appendTooltip(stack, world, tooltip, context)
    }
}