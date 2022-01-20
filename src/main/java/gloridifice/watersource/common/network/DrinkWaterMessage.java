package gloridifice.watersource.common.network;

import gloridifice.watersource.common.event.CommonEventHandler;
import gloridifice.watersource.registry.CapabilityRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class DrinkWaterMessage implements INormalMessage{
    public DrinkWaterMessage(){

    }
    public DrinkWaterMessage(FriendlyByteBuf buf) {

    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {

    }

    @Override
    public void process(Supplier<NetworkEvent.Context> context) {
        Player player = context.get().getSender();
        CommonEventHandler.drinkWaterBlock(player);
        player.getCapability(CapabilityRegistry.PLAYER_WATER_LEVEL).ifPresent(data -> {
            SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new PlayerWaterLevelMessage(data.getWaterLevel(), data.getWaterSaturationLevel(), data.getWaterExhaustionLevel()));
        });
    }
}
