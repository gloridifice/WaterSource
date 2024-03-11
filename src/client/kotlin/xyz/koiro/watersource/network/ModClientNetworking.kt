package xyz.koiro.watersource.network

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import xyz.koiro.watersource.attechment.ModAttachmentTypes
import xyz.koiro.watersource.attechment.WaterLevelData

object ModClientNetworking {
    fun initialize() {
        ClientPlayNetworking.registerGlobalReceiver(ModNetworking.UPDATE_WATER_DATA_ID){ client, _, buf, sent ->
            val data = WaterLevelData.readBuf(buf)

            client.execute {
                val player = client.player!!
                player.setAttached(ModAttachmentTypes.WATER_LEVEL, data)
            }
        }
    }
}