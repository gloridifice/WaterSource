package xyz.koiro.watersource.world.item

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.world.World
import xyz.koiro.watersource.api.insertFluid

class EmptyDrinkableContainer(settings: Settings?, val containerStack: () -> ItemStack) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        if (!world.isClient()) {
            val hit = user.raycast(3.0, 0.05f, true)
            if (hit is BlockHitResult) {
                val stack = containerStack();
                val blockState = world.getBlockState(hit.blockPos)
                if (blockState.fluidState.fluid == Fluids.WATER) {
                    stack.insertFluid(Fluids.WATER) { it.capacity }
                    (stack.item as? FluidContainer)?.onFluidDataChanged(stack, user, hand)
                    val heldStack = user.getStackInHand(hand)
                    val handSlot = user.inventory.getSlotWithStack(heldStack)

                    heldStack.decrement(1)

                    if (heldStack.isEmpty) user.inventory.insertStack(handSlot, stack)
                    else user.inventory.insertStack(stack)

                    return TypedActionResult.success(stack)
                }
            }
        }
        return super.use(world, user, hand)
    }
}