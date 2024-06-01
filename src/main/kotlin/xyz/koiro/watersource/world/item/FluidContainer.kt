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
import xyz.koiro.watersource.api.FluidStorageData
import xyz.koiro.watersource.api.getOrCreateFluidStorageData
import xyz.koiro.watersource.api.insertFluid
import xyz.koiro.watersource.datagen.ModLanguages
import kotlin.math.round

open class FluidContainer(
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

    fun onFluidDataChanged(stack: ItemStack, playerEntity: PlayerEntity, hand: Hand) {
        stack.getOrCreateFluidStorageData()?.let {
            val amount = it.amount
            val damage = round((1 - amount.toDouble() / capacity.toDouble()) * maxDamage).toInt()
            if (damage >= maxDamage){
                if (emptyContainer != null){
                    playerEntity.setStackInHand(hand, emptyContainer.invoke())
                } else stack.damage = maxDamage
            } else {
                stack.damage = damage
            }
        }
    }

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        if (!world.isClient()) {
            val hit = user.raycast(3.0, 0.05f, true)
            val handItem = user.getStackInHand(hand)
            if (hit is BlockHitResult) {
                val blockState = world.getBlockState(hit.blockPos)
                if (blockState.fluidState.fluid == Fluids.WATER) {
                    handItem.insertFluid(Fluids.WATER) { it.capacity }
                    onFluidDataChanged(handItem, user, hand)
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
                return (stack.item as FluidContainer).capacity
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