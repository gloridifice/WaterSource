package xyz.koiro.watersource.network

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import xyz.koiro.watersource.world.attachment.ModAttachmentTypes
import xyz.koiro.watersource.world.attachment.WaterLevelData
import xyz.koiro.watersource.world.block.entity.FilterBlockEntity

object ModClientNetworking {
    fun initialize() {
        ClientPlayNetworking.registerGlobalReceiver(ModNetworking.UPDATE_WATER_DATA_ID){ client, _, buf, sent ->
            val data = WaterLevelData.readBuf(buf)

            client.execute {
                val player = client.player!!
                player.setAttached(ModAttachmentTypes.WATER_LEVEL, data)
            }
        }
        ClientPlayNetworking.registerGlobalReceiver(ModNetworking.UPDATE_FILTER_ENTITY_ID) { mc, _, buf, sent ->
            val pos = buf.readBlockPos()
            val entity = mc.world?.getBlockEntity(pos)
            if (entity is FilterBlockEntity) {
                entity.readPacket(buf)
            }
        }
    }
}