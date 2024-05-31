package xyz.koiro.watersource.world.item

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsage
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.UseAction
import net.minecraft.world.World

class DrinkOnceItem(settings: Settings, val useDuration: Int = 32, val emptyStack: () -> ItemStack) : Item(settings) {
    override fun use(world: World?, user: PlayerEntity?, hand: Hand?): TypedActionResult<ItemStack> {
        return ItemUsage.consumeHeldItem(world, user, hand)
    }

    override fun getMaxUseTime(stack: ItemStack?): Int {
        return useDuration
    }

    override fun getUseAction(stack: ItemStack?): UseAction {
        return UseAction.DRINK
    }

    override fun finishUsing(stack: ItemStack, world: World, user: LivingEntity): ItemStack {
        if (user !is PlayerEntity) return stack

        val emptyStack = emptyStack()

        stack.decrement(1)

        return if (stack.isEmpty) {
            emptyStack
        } else {
            user.inventory.insertStack(emptyStack)
            stack
        }
    }
}