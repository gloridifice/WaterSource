@file:Suppress("UnstableApiUsage")

package xyz.koiro.watersource.world.item

import net.minecraft.client.item.TooltipType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
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
import xyz.koiro.watersource.world.datacomponent.ModDataComponentTypes
import kotlin.math.round

open class FluidContainerItem(
    settings: Settings,
    val capacity: Long,
    val emptyContainer: (() -> ItemStack)? = null
) : Item(
    settings.maxDamage((capacity / WSConfig.UNIT_DRINK_VOLUME).toInt())
) {

    fun canInsert(itemStack: ItemStack, fluid: Fluid, amount: Long, currentData: FluidStorageData): Boolean {
        return fluid == currentData.fluid || currentData.isBlank()
    }

    fun onFluidDataChanged(stack: ItemStack, playerEntity: PlayerEntity, hand: Hand) {
        stack.getOrCreateFluidStorageData()?.let {
            val amount = it.amount
            val maxDamage = stack.maxDamage
            val damage = round((1 - amount.toDouble() / capacity.toDouble()) * maxDamage).toInt()
            if (damage >= maxDamage) {
                if (emptyContainer != null) {
                    playerEntity.setStackInHand(hand, emptyContainer.invoke())
                } else stack.damage = maxDamage
            } else {
                stack.damage = damage
            }
        }
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
                    onFluidDataChanged(handItem, user, hand)
                    return TypedActionResult.success(handItem)
                }
            }
        }
        return super.use(world, user, hand)
    }

    override fun appendTooltip(
        stack: ItemStack?,
        context: TooltipContext?,
        tooltip: MutableList<Text>?,
        type: TooltipType?
    ) {
        stack?.getOrCreateFluidStorageData()?.let { storageData ->
            tooltip?.add(storageData.getDisplayText().styled { it.withColor(Formatting.GRAY) })
        }
        super.appendTooltip(stack, context, tooltip, type)
    }
}