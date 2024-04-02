@file:Suppress("UnstableApiUsage")

package xyz.koiro.watersource.world.item

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.UseAction
import net.minecraft.world.World
import xyz.koiro.watersource.WSConfig
import xyz.koiro.watersource.WaterSource
import xyz.koiro.watersource.api.getOrCreateFluidStorageData
import xyz.koiro.watersource.data.HydrationDataManager

open class DrinkableContainer(
    settings: Settings, capacity: Long,
    val useDuration: Int = 32,
    val drinkVolumeMultiplier: Int = 1
) : FluidContainer(settings, capacity) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val result = super.use(world, user, hand)
        if (result.result != ActionResult.PASS) return result

        return drinkUse(world, user, hand)
    }

    private fun drinkUse(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val handStack = user.getStackInHand(hand)
        val storage = handStack.getOrCreateFluidStorageData()
        storage?.let { storageData ->
            val fluid = storageData.fluid
            HydrationDataManager.SERVER.findByFluid(fluid)?.let { hydration ->
                if (storageData.amount >= WSConfig.UNIT_DRINK_VOLUME) {
                    WaterSource.LOGGER.info("Debug use drinkable")
                    //restore player water level
                    return TypedActionResult.consume(user.getStackInHand(hand))
                }
            }
        }
        return TypedActionResult.pass(user.getStackInHand(hand))
    }

    override fun getMaxUseTime(stack: ItemStack?): Int {
        return useDuration
    }

    override fun getUseAction(stack: ItemStack?): UseAction {
        return UseAction.DRINK
    }
}