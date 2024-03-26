@file:Suppress("UnstableApiUsage")

package xyz.koiro.watersource.event

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.world.World
import xyz.koiro.watersource.WSConfig
import xyz.koiro.watersource.WSConfig.Exhaustion
import xyz.koiro.watersource.WSConfig.Punishment
import xyz.koiro.watersource.WaterSource
import xyz.koiro.watersource.WaterSource.getWaterSourceDifficulty
import xyz.koiro.watersource.api.extractFluid
import xyz.koiro.watersource.api.getOrCreateFluidStorageData
import xyz.koiro.watersource.world.attachment.ModAttachmentTypes
import xyz.koiro.watersource.data.HydrationDataManager
import xyz.koiro.watersource.api.ifInSurvivalAndGetWaterData
import xyz.koiro.watersource.data.HydrationData
import xyz.koiro.watersource.world.effect.ModStatusEffects
import xyz.koiro.watersource.world.item.DrinkableContainer
import java.util.*

object ModEventsRegistries {
    fun initialize() {
        ServerEntityEvents.ENTITY_LOAD.register(serverEntityLoadHandler)
        ServerTickEvents.END_WORLD_TICK.register(worldTickHandler)
        ModServerEvents.PLAYER_JUMP.register(playerJumpWaterExhaustion)
        ModServerEvents.FINISH_USING_ITEM.register(playerUseHydrationItemFinished)
        ModServerEvents.PLAYER_WRITE_CUSTOM_NBT.register(writeModPlayerData)
        ModServerEvents.PLAYER_READ_CUSTOM_NBT.register(readModPlayerNbt)
    }

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
            highWaterLevelReward(player, world)
            thirstyTick(player)
        }
    }

    private fun thirstyTick(player: ServerPlayerEntity) {
        player.ifInSurvivalAndGetWaterData { waterData ->
            player.getStatusEffect(ModStatusEffects.THIRSTY)?.let { effectInstance ->
                val ticker = player.getAttachedOrCreate(ModAttachmentTypes.THIRSTY_ADD_EXHAUSTION_TICKER)
                ticker.add(1)
                if (ticker.value >= 20) {
                    waterData.addExhaustion(Exhaustion.thirstyPerSecond(effectInstance.amplifier), player)
                    waterData.updateToClient(player)
                }
            }
        }
    }

    private fun highWaterLevelReward(player: ServerPlayerEntity, world: World) {
        player.ifInSurvivalAndGetWaterData { waterLevelData ->
            if (waterLevelData.level > 16 && !player.hasStatusEffect(ModStatusEffects.THIRSTY)) {
                val tick = player.getAttachedOrCreate(ModAttachmentTypes.WATER_REWARD_HEAL_TICKER)
                tick.add(1)
                if (tick.value > 125) {
                    player.heal(1f)
                    waterLevelData.addExhaustion(Exhaustion.REWARD_HEALTH, player)
                    waterLevelData.updateToClient(player)
                    tick.setValue(0)
                }
            }
        }
    }

    private fun lowWaterLevelPunishment(player: ServerPlayerEntity, world: World) {
        player.ifInSurvivalAndGetWaterData { waterData ->
            val diff = world.getWaterSourceDifficulty()
            when {
                waterData.level <= 0 -> {
                    Punishment.getPunishmentStatusEffectsZero(diff).forEach { effect ->
                        player.addStatusEffect(StatusEffectInstance(effect))
                    }
                }

                waterData.level <= 6 -> {
                    Punishment.getPunishmentStatusEffectsSix(diff).forEach { effect ->
                        player.addStatusEffect(StatusEffectInstance(effect))
                    }
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
        player.getAttachedOrCreate(ModAttachmentTypes.POSITION_OFFSET)

        WaterSource.LOGGER.info("Player <${player.name}>'s pos offset data attached.")
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
        player.ifInSurvivalAndGetWaterData { waterData ->
            val posOffset = player.getAttachedOrCreate(ModAttachmentTypes.POSITION_OFFSET)
            //Movement
            posOffset.ifPresent {
                if (player.isSprinting) {
                    waterData.addExhaustion(Exhaustion.SPRINT * it.offset.length().toFloat(), player)
                    waterData.updateToClient(player)
                }
            }
        }
    }

    private val playerJumpWaterExhaustion = ModServerEvents.PlayerJump { player ->
        if (player is ServerPlayerEntity) {
            player.ifInSurvivalAndGetWaterData {
                it.addExhaustion(Exhaustion.JUMP, player)
                it.updateToClient(player)
            }
        }
        ActionResult.SUCCESS
    }

    private val playerUseHydrationItemFinished = ModServerEvents.FinishUsingItem { player, world, stack ->
        if (player is ServerPlayerEntity) {
            player.ifInSurvivalAndGetWaterData { waterLevelData ->
                WaterSource.LOGGER.info("Player ${player.name} finishes using ${stack.name}")

                val isItemDrinkable = stack.item is DrinkableContainer
                val data: HydrationData? = if (isItemDrinkable) {
                    stack.getOrCreateFluidStorageData()?.let { storage ->
                        HydrationDataManager.SERVER.findByFluid(storage.fluid)
                    }
                } else {
                    HydrationDataManager.SERVER.findByItemStack(stack)
                }

                data?.let { hydrationData ->
                    val multiplier = if (isItemDrinkable) (stack.item as DrinkableContainer).drinkVolumeMultiplier else 1
                    val level = hydrationData.level
                    val saturation = hydrationData.saturation
                    waterLevelData.restoreWater(level, saturation, multiplier)
                    waterLevelData.updateToClient(player)
                    hydrationData.applyEffectsToPlayer(player, multiplier)
                    if (isItemDrinkable){
                        stack.extractFluid(WSConfig.UNIT_DRINK_VOLUME)
                    }
                }
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

}