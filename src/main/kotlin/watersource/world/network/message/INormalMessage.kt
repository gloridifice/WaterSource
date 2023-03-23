package watersource.world.network.message

import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

interface INormalMessage {
    fun toBytes(buf: FriendlyByteBuf?)
    fun process(context: Supplier<NetworkEvent.Context?>?)

}