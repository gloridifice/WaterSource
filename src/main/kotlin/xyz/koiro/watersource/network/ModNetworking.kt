package xyz.koiro.watersource.network

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.minecraft.network.NetworkSide
import net.minecraft.network.packet.CustomPayload
import net.minecraft.network.packet.PacketType
import net.minecraft.registry.Registries
import xyz.koiro.watersource.WaterSource

object ModNetworking {
    val UPDATE_WATER_LEVEL = CustomPayload.Id<UpdateWaterLevelPayload>(WaterSource.identifier("update_water_level"))
    fun initialize(){
        PayloadTypeRegistry.playS2C().register(UPDATE_WATER_LEVEL, UpdateWaterLevelPayload.PACK_CODEC)
    }
}