package xyz.koiro.watersource.network

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import xyz.koiro.watersource.world.attachment.ModAttachmentTypes
import xyz.koiro.watersource.world.attachment.WaterLevelData
import xyz.koiro.watersource.world.block.entity.FilterBlockEntity

object ModClientNetworking {
    fun initialize() {
        ClientPlayNetworking.registerGlobalReceiver(ModNetworking.UPDATE_WATER_LEVEL){ payload, handler ->
            val client = handler.client()
            client.execute {
                val player = client.player!!
                player.getAttachedOrCreate(ModAttachmentTypes.WATER_LEVEL).setData(payload.level, payload.saturation, payload.exhaustion)
            }
        }
    }
}