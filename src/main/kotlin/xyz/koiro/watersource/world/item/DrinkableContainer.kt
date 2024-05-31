@file:Suppress("UnstableApiUsage")

package xyz.koiro.watersource.world.item

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsage
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.UseAction
import net.minecraft.world.World
import xyz.koiro.watersource.WSConfig
import xyz.koiro.watersource.api.extractFluid
import xyz.koiro.watersource.api.getOrCreateFluidStorageData
import xyz.koiro.watersource.data.HydrationData
import xyz.koiro.watersource.data.HydrationDataManager
import xyz.koiro.watersource.world.attachment.WaterLevelData

open class DrinkableContainer(
    settings: Settings, capacity: Long,
    val useDuration: Int = 32,
    val drinkVolumeMultiplier: Int = 1,
    emptyContainerStack: (() -> ItemStack)? = null
) : FluidContainer(settings, capacity, emptyContainerStack), IHydrationUsable {
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
                    return ItemUsage.consumeHeldItem(world, user, hand)
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

    override fun onHydrationUsingFinished(stack: ItemStack, player: ServerPlayerEntity, hand: Hand) {
        onFluidDataChanged(stack, player, hand)
    }


    override fun hydrationUse(
        stack: ItemStack,
        hydrationData: HydrationData,
        waterLevelData: WaterLevelData,
        player: ServerPlayerEntity
    ) {
        val multiplier = drinkVolumeMultiplier
        IHydrationUsable.restoreWaterFromHydrationData(hydrationData, waterLevelData, player, multiplier)
        stack.extractFluid(WSConfig.UNIT_DRINK_VOLUME)
    }

    override fun findHydrationData(stack: ItemStack, manager: HydrationDataManager): HydrationData? {
        return stack.getOrCreateFluidStorageData()?.let {
            manager.findByFluid(it.fluid)
        }
    }
}