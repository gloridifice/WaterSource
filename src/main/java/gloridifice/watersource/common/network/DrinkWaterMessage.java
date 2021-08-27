package gloridifice.watersource.common.network;

import gloridifice.watersource.common.capability.WaterLevelCapability;
import gloridifice.watersource.common.event.CommonEventHandler;
import gloridifice.watersource.registry.CapabilityRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class DrinkWaterMessage implements INormalMessage{
    public DrinkWaterMessage(){

    }
    public DrinkWaterMessage(PacketBuffer buf) {

    }

    @Override
    public void toBytes(PacketBuffer buf) {

    }

    @Override
    public void process(Supplier<NetworkEvent.Context> context) {
        PlayerEntity player = context.get().getSender();
        CommonEventHandler.drinkWater(player);
        player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
            SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PlayerWaterLevelMessage(data.getWaterLevel(), data.getWaterSaturationLevel(), data.getWaterExhaustionLevel()));
        });
    }
}
