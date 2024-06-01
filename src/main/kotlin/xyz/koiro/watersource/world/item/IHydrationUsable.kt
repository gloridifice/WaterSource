package xyz.koiro.watersource.world.item

import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Hand
import xyz.koiro.watersource.data.HydrationData
import xyz.koiro.watersource.data.HydrationDataManager
import xyz.koiro.watersource.world.attachment.WaterLevelData

interface IHydrationUsable {
    fun onHydrationUsingFinished(stack: ItemStack, player: ServerPlayerEntity, hand: Hand)
    fun hydrationUse(
        stack: ItemStack,
        hydrationData: HydrationData,
        waterLevelData: WaterLevelData,
        player: ServerPlayerEntity
    )

    fun findHydrationData(stack: ItemStack, manager: HydrationDataManager): HydrationData?

    companion object {
        fun restoreWaterFromHydrationData(
            hydrationData: HydrationData,
            waterLevelData: WaterLevelData,
            player: ServerPlayerEntity,
            multiplier: Int = 1,
        ) {
            val level = hydrationData.level
            val saturation = hydrationData.saturation
            if (hydrationData.isDry()) {
                val dryLevel = hydrationData.dryLevel!!
                waterLevelData.dryConsumeWater(dryLevel)
            } else {
                waterLevelData.restoreWater(level, saturation, multiplier)
            }
            waterLevelData.updateToClient(player)
            hydrationData.applyEffectsToPlayer(player, multiplier)
        }
    }
}