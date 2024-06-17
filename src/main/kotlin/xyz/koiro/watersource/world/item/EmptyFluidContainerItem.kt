package xyz.koiro.watersource.world.item

import net.minecraft.client.item.TooltipType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.Fluids
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.world.World
import xyz.koiro.watersource.api.storage.FluidStorageData
import xyz.koiro.watersource.api.storage.getOrCreateFluidStorageData
import xyz.koiro.watersource.api.storage.insertFluid

class EmptyFluidContainerItem(settings: Settings?, val containerStack: () -> ItemStack) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val hit = user.raycast(3.0, 0.05f, true)
        if (hit is BlockHitResult) {
            val blockState = world.getBlockState(hit.blockPos)
            if (blockState.fluidState.fluid == Fluids.WATER) {
                val stack = user.getStackInHand(hand)
                val containerStack = run {
                    val ret = containerStack()
                    ret.insertFluid(Fluids.WATER) { it.capacity }
                    (ret.item as? FluidContainerItem)?.onFluidDataChanged(ret, user, hand)
                    ret
                }
                stack.decrement(1)

                val retStack =
                    if (stack.isEmpty) containerStack
                    else {
                        user.inventory.insertStack(containerStack)
                        stack
                    }
                return TypedActionResult.success(retStack)
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
        containerStack().getOrCreateFluidStorageData()?.let { storageData ->
            tooltip?.add(FluidStorageData.getEmptyText(storageData.capacity).styled { it.withColor(Formatting.GRAY) })
        }
        super.appendTooltip(stack, context, tooltip, type)
    }
}