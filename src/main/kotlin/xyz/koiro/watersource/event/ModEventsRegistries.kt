@file:Suppress("UnstableApiUsage")

package xyz.koiro.watersource.event

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.event.player.UseItemCallback
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World
import xyz.koiro.watersource.WaterExhaustionInfo
import xyz.koiro.watersource.WaterPunishmentInfo
import xyz.koiro.watersource.WaterSource
import xyz.koiro.watersource.WaterSource.getWaterSourceDifficulty
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
    private val worldTickHandler = ServerTickEvents.EndWorldTick { world ->
        world.players.forEach { player ->
            updatePlayerLastPositionData(player)
            waterExhaustionTick(player)
            lowWaterLevelPunishment(player, world)
        }
    }

    private val worldTickStart = ServerTickEvents.StartWorldTick { world ->
        world.players.forEach { player ->
            lowWaterLevelPunishment(player, world)
        }
    }
    private fun lowWaterLevelPunishment(player: ServerPlayerEntity, world: World) {
        val waterLevelData = player.getAttachedOrCreate(ModAttachmentTypes.WATER_LEVEL)
        val diff = world.getWaterSourceDifficulty()
        when {
            waterLevelData.level <= 0 -> {
                WaterPunishmentInfo.getPunishmentStatusEffectsZero(diff).forEach {
                    player.addStatusEffect(StatusEffectInstance(it))
                }
            }

            waterLevelData.level <= 6 -> {
                WaterPunishmentInfo.getPunishmentStatusEffectsSix(diff).forEach {
                    player.addStatusEffect(StatusEffectInstance(it))
                }
            }
        }
    }

    private fun attachWaterLevelData(player: ServerPlayerEntity) {
        val waterLevelData = player.getAttachedOrCreate(ModAttachmentTypes.WATER_LEVEL)
        waterLevelData.updateToClient(player)

        WaterSource.LOGGER.info("Player <${player.name}>'s water level data attached.")
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
        ActionResult.SUCCESS
    }

    private val playerUseItemFinished = ModServerEvents.FinishUsingItem { player, world, stack ->
        if (player is ServerPlayerEntity) {
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

    const val LEVEL_KEY = "WaterSourceWaterLevel"
    const val SATURATION_KEY = "WaterSourceWaterSaturation"
    const val EXHAUSTION_KEY = "WaterSourceExhaustion"
    private val writeModPlayerData = ModServerEvents.PlayerWriteCustomNbt { player, nbt ->
        val data = player.getAttachedOrCreate(ModAttachmentTypes.WATER_LEVEL)
        nbt.putInt(LEVEL_KEY, data.level)
        nbt.putInt(SATURATION_KEY, data.saturation)
        nbt.putFloat(EXHAUSTION_KEY, data.exhaustion)
        ActionResult.PASS
    }

    private val readModPlayerNbt = ModServerEvents.PlayerReadCustomNbt { player, nbt ->
        val data = player.getAttachedOrCreate(ModAttachmentTypes.WATER_LEVEL)
        val level = nbt.getInt(LEVEL_KEY)
        val saturation = nbt.getInt(SATURATION_KEY)
        val exhaustion = nbt.getFloat(EXHAUSTION_KEY)
        data.setData(level, saturation, exhaustion)
        ActionResult.PASS
    }

    fun initialize() {
        ServerEntityEvents.ENTITY_LOAD.register(serverEntityLoadHandler)
        ServerTickEvents.END_WORLD_TICK.register(worldTickHandler)
//        ServerTickEvents.START_WORLD_TICK.register(worldTickStart)
        ModServerEvents.PLAYER_JUMP.register(playerJumpWaterExhaustion)
        ModServerEvents.FINISH_USING_ITEM.register(playerUseItemFinished)
        ModServerEvents.PLAYER_WRITE_CUSTOM_NBT.register(writeModPlayerData)
        ModServerEvents.PLAYER_READ_CUSTOM_NBT.register(readModPlayerNbt)

    }
}