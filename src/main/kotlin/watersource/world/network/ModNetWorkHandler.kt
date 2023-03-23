package watersource.world.network

import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.network.NetworkRegistry
import watersource.WaterSource
import watersource.world.network.message.INormalMessage
import watersource.world.network.message.PlayerWaterMessage
import java.util.function.Function

object ModNetWorkHandler {
    private val PROTOCOL_VERSION = "1"

    val INSTANCE = NetworkRegistry.newSimpleChannel(
        ResourceLocation(WaterSource.ID, "main"),
        { PROTOCOL_VERSION },
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    )

    fun doRegistry(){
        var id = 0
        registryMessage(id++, PlayerWaterMessage::class.java){ buf -> PlayerWaterMessage(buf) }
    }

    @Suppress("INACCESSIBLE_TYPE")
    fun <MSG : INormalMessage> registryMessage(index: Int, clazz: Class<MSG>, decoder: Function<FriendlyByteBuf, MSG>){
        ModNetWorkHandler.INSTANCE.registerMessage(index, clazz, INormalMessage::toBytes, decoder) { msg, context ->
            run {
                msg.process(context)
                context.get().packetHandled = true
            }
        }

    }
}