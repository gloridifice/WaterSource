package watersource.world.network.message

import net.minecraft.client.Minecraft
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Player
import net.minecraftforge.network.NetworkDirection
import net.minecraftforge.network.NetworkEvent
import watersource.world.capability.ModCapabilities
import java.util.function.Supplier

class PlayerWaterMessage(val nbt: Tag?) :
    INormalMessage {
    constructor(buf: FriendlyByteBuf) : this(buf.readNbt())
    override fun toBytes(buf: FriendlyByteBuf?) {
        if (nbt is CompoundTag) buf?.writeNbt(nbt)
    }

    override fun process(context: Supplier<NetworkEvent.Context?>?) {
        if (context?.get()?.direction == NetworkDirection.PLAY_TO_CLIENT) {
            context.get()?.enqueueWork {
                val player = Minecraft.getInstance().player
                player?.getCapability(ModCapabilities.PLAYER_WATER)?.ifPresent {
                    it.readFromNBT(nbt)
                }
            }
        }
    }
}