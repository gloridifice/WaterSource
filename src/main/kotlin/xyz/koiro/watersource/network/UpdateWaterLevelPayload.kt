package xyz.koiro.watersource.network

import net.minecraft.network.PacketByteBuf
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.packet.CustomPayload

class UpdateWaterLevelPayload(val level: Int, val saturation: Int, val exhaustion: Float): CustomPayload {
    companion object{
        val PACK_CODEC = PacketCodec.ofStatic(::write, ::read)

        fun write(buf: RegistryByteBuf, payload: UpdateWaterLevelPayload){
            buf.writeInt(payload.level)
            buf.writeInt(payload.saturation)
            buf.writeFloat(payload.exhaustion)
        }
        fun read(buf: RegistryByteBuf): UpdateWaterLevelPayload{
            val level = buf.readInt()
            val saturation = buf.readInt()
            val exhaustion = buf.readFloat()
            return UpdateWaterLevelPayload(level, saturation, exhaustion)
        }
    }

    override fun getId(): CustomPayload.Id<out CustomPayload> {
        return ModNetworking.UPDATE_WATER_LEVEL
    }
}