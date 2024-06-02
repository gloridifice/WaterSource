package xyz.koiro.watersource.world.attachment

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.network.ServerPlayerEntity
import xyz.koiro.watersource.WSConfig
import xyz.koiro.watersource.network.ModNetworking
import xyz.koiro.watersource.world.effect.ModStatusEffects

class WaterLevelData(
    level: Int = 20,
    saturation: Int = 8,
    exhaustion: Float = 0f,
    maxLevel: Int = 20,
    maxSaturation: Int = 20,
    maxExhaustion: Float = 4f,
) {
    var level = level
        private set(value) {
            field = value.coerceIn(0, maxLevel)
        }
    var saturation = saturation
        private set(value) {
            field = value.coerceIn(0, maxSaturation)
        }
    var maxLevel = maxLevel
        private set
    var maxSaturation = maxSaturation
        private set
    var exhaustion = exhaustion
        private set
    var maxExhaustion = maxExhaustion
        private set

    /** Consume water by adding exhaustion like hungry system in vanilla.
     * */
    fun addExhaustion(amount: Float, player: PlayerEntity) {
        val effect = player.getStatusEffect(ModStatusEffects.THIRSTY)
        var finalAmount = amount * WSConfig.Exhaustion.multiplier
        effect?.let {
            finalAmount *= WSConfig.Exhaustion.thirstyMultiplier(it.amplifier)
        }
        val added = finalAmount + exhaustion
        exhaustion = if (added >= maxExhaustion) {
            val count = (added / maxExhaustion).toInt()
            consumeWater(count)

            added % maxExhaustion
        } else {
            added
        }
    }

    /** Do recovery water level and saturation level with drinks.
     * */
    fun restoreWater(level: Int, saturation: Int, multiplier: Int) {
        this.level += WSConfig.applyWaterLevelRestoreMultiplier(level, multiplier)
        this.saturation += WSConfig.applyWaterLevelRestoreMultiplier(saturation, multiplier)
    }

    /** Consume water saturation level and water level. */
    private fun consumeWater(amount: Int = 1) {
        val diff = saturation - amount
        if (diff < 0) level += diff
        else saturation = diff
    }

    /** Consume only water saturation */
    fun dryConsumeWater(amount: Int){
        saturation -= amount
    }

    fun writeBuf(buf: PacketByteBuf): PacketByteBuf{
        buf.writeInt(level)
        buf.writeInt(saturation)
        buf.writeFloat(exhaustion)
        buf.writeInt(maxLevel)
        buf.writeInt(maxSaturation)
        buf.writeFloat(maxExhaustion)
        return buf
    }

    companion object {
        fun readBuf(buf: PacketByteBuf): WaterLevelData {
            val level = buf.readInt()
            val saturation = buf.readInt()
            val exhaustion = buf.readFloat()
            val maxLevel = buf.readInt()
            val maxSaturation = buf.readInt()
            val maxExhaustion = buf.readFloat()
            return WaterLevelData(level, saturation, exhaustion, maxLevel, maxSaturation, maxExhaustion)
        }
    }

    fun updateToClient(user: ServerPlayerEntity){
        ServerPlayNetworking.send(user, ModNetworking.UPDATE_WATER_DATA_ID, writeBuf(PacketByteBufs.create()))
    }

    fun setData(level: Int = 20, saturation: Int = 8, exhaustion: Float = 0f){
        this.level = level
        this.saturation = saturation
        this.exhaustion = exhaustion
    }
}