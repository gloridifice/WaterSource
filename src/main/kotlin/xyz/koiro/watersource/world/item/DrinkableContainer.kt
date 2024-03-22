@file:Suppress("UnstableApiUsage")

package xyz.koiro.watersource.world.item

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.UseAction
import net.minecraft.world.World
import xyz.koiro.watersource.WaterSourceConfig
import xyz.koiro.watersource.data.HydrationDataManager

class DrinkableContainer(
    settings: Settings, capacity: Long,
    val useDuration: Int = 32
) : FluidContainer(settings, capacity) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val result = super.use(world, user, hand)
        if (result.result != ActionResult.PASS) return result

        val context = ContainerItemContext.ofPlayerHand(user, hand)
        val storage = context.find(FluidStorage.ITEM)
        storage?.first()?.let { storageView ->
            val fluid = storageView.resource.fluid
            HydrationDataManager.SERVER.findByFluid(fluid)?.let { data ->
                if (storageView.amount >= WaterSourceConfig.UNIT_DRINK_VOLUME) {
                    Transaction.openOuter().use { transaction ->
                        storage.extract(storageView.resource, WaterSourceConfig.UNIT_DRINK_VOLUME, transaction)
                    }
                    return TypedActionResult.success(user.getStackInHand(hand))
                }
            }
        }
        return TypedActionResult.pass(user.getStackInHand(hand))
    }

    override fun getMaxUseTime(stack: ItemStack?): Int {
        return useDuration
    }

    override fun getUseAction(stack: ItemStack?): UseAction {
        return UseAction.EAT
    }
}