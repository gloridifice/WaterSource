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

open class FluidContainer(
    settings: Settings,
    val capacity: Long
) : Item(settings.maxDamage((capacity / WSConfig.UNIT_DRINK_VOLUME).toInt())) {
    init {
        FluidStorage.ITEM.registerForItems(::getFluidStorage, this)
    }

    fun canInsert(itemStack: ItemStack, fluid: Fluid, amount: Long, currentData: FluidStorageData): Boolean {
        return fluid == currentData.fluid || currentData.isBlank()
    }

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        if (!world.isClient()) {
            //todo get delta
            val hit = user.raycast(3.0, 0.05f, true)
            val handItem = user.getStackInHand(hand)
            if (hit is BlockHitResult) {
                val blockState = world.getBlockState(hit.blockPos)
                if (blockState.fluidState.fluid == Fluids.WATER) {
                    handItem.insertFluid(Fluids.WATER) { it.capacity }
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
                updateDamageFromAmount(stack, this.getAmount())
            }
        }
        if (nbt != null) {
            ret.readNbt(nbt)
        }
        return ret
    }

    open fun updateDamageFromAmount(stack: ItemStack, amount: Long) {
        stack.damage = stack.maxDamage - (amount / WSConfig.UNIT_DRINK_VOLUME).toInt()
    }

    override fun appendTooltip(
        stack: ItemStack,
        world: World?,
        tooltip: MutableList<Text>,
        context: TooltipContext
    ) {
        stack.getOrCreateFluidStorageData()?.let { storageData ->
            val name = Text.translatable(storageData.fluid.identifier().toTranslationKey("fluid"))
            val rawText =
                if (storageData.isBlank()) Text.of("Empty") else
                    name.append(Text.of(": ${storageData.amount}/${storageData.capacity}"))

            tooltip.add(rawText.copy().styled { it.withColor(Formatting.GRAY) })
        }
        super.appendTooltip(stack, world, tooltip, context)
    }
}