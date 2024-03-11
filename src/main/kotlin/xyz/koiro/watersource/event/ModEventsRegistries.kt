@file:Suppress("UnstableApiUsage")

package xyz.koiro.watersource.event

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.event.player.UseItemCallback
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.TypedActionResult
import xyz.koiro.watersource.WaterExhaustionInfo
import xyz.koiro.watersource.WaterSource
import xyz.koiro.watersource.attechment.ModAttachmentTypes
import xyz.koiro.watersource.data.HydrationDataManager
import java.util.*

object ModEventsRegistries {
    private val serverEntityLoadHandler = ServerEntityEvents.Load { entity, _ ->
        if (entity is ServerPlayerEntity) {
            attachWaterLevelData(entity)
            attachPlayerLastPositionData(entity)
        }
    }
    private val worldTickHandler = ServerTickEvents.EndWorldTick {
        it.players.forEach { player ->
            updatePlayerLastPositionData(player)
            waterExhaustionTick(player)
        }
    }

    private fun attachWaterLevelData(player: ServerPlayerEntity) {
        if (!player.hasAttached(ModAttachmentTypes.WATER_LEVEL)) {
            val waterLevelData = player.getAttachedOrCreate(ModAttachmentTypes.WATER_LEVEL)
            waterLevelData.updateToClient(player)

            WaterSource.LOGGER.info("Player <${player.name}>'s water level data attached.")
        }
    }

    private fun attachPlayerLastPositionData(player: ServerPlayerEntity) {
        if (!player.hasAttached(ModAttachmentTypes.POSITION_OFFSET)) {
            player.getAttachedOrCreate(ModAttachmentTypes.POSITION_OFFSET)

            WaterSource.LOGGER.info("Player <${player.name}>'s pos offset data attached.")
        }
    }

    private fun updatePlayerLastPositionData(player: ServerPlayerEntity) {
        if (player.isOnGround) {
            val attach = player.getAttachedOrCreate(ModAttachmentTypes.POSITION_OFFSET)
            val new = attach.map {
                ModAttachmentTypes.PosOffset(player.pos, it.current)
            }.orElse(ModAttachmentTypes.PosOffset(player.pos, player.pos))
            player.setAttached(ModAttachmentTypes.POSITION_OFFSET, Optional.of(new))
        } else {
            player.setAttached(ModAttachmentTypes.POSITION_OFFSET, Optional.empty())
        }
    }

    private fun waterExhaustionTick(player: ServerPlayerEntity) {
        val posOffset = player.getAttachedOrCreate(ModAttachmentTypes.POSITION_OFFSET)
        val waterLevelAttach = player.getAttachedOrCreate(ModAttachmentTypes.WATER_LEVEL)
        //Movement
        posOffset.ifPresent {
            if (player.isSprinting) {
                waterLevelAttach.addExhaustion(WaterExhaustionInfo.SPRINT * it.offset.length().toFloat())
                waterLevelAttach.updateToClient(player)
            }
        }
    }

    private val playerJumpWaterExhaustion = ModServerEvents.PlayerJump {
        if (it is ServerPlayerEntity) {
            val waterLevelData = it.getAttachedOrCreate(ModAttachmentTypes.WATER_LEVEL)
            waterLevelData.addExhaustion(WaterExhaustionInfo.JUMP)
            waterLevelData.updateToClient(it)
        }
        ActionResult.PASS
    }

    private val playerUseItemFinished = ModServerEvents.FinishUsingItem { player, world, stack ->
        if (player is ServerPlayerEntity){
            WaterSource.LOGGER.info("Player ${player.name} finishes using ${stack.name}")
            val data = HydrationDataManager.SERVER.findByItemStack(stack)
            data?.let { hydrationData ->
                val level = hydrationData.level
                val saturation = hydrationData.saturation
                player.modifyAttached(ModAttachmentTypes.WATER_LEVEL) {
                    it.recoveryWater(level, saturation)
                    it
                }
                player.getAttached(ModAttachmentTypes.WATER_LEVEL)?.updateToClient(player)
            }
        }
        ActionResult.PASS
    }

    fun initialize() {
        ServerEntityEvents.ENTITY_LOAD.register(serverEntityLoadHandler)
        ServerTickEvents.END_WORLD_TICK.register(worldTickHandler)
        ModServerEvents.PLAYER_JUMP.register(playerJumpWaterExhaustion)
        ModServerEvents.FINISH_USING_ITEM.register(playerUseItemFinished)
    }
}