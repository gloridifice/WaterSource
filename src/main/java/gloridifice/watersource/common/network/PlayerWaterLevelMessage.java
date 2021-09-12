package gloridifice.watersource.common.network;

import gloridifice.watersource.registry.CapabilityRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerWaterLevelMessage implements INormalMessage {
    int waterLevel, waterSaturationLevel;
    float waterExhaustionLevel;

    public PlayerWaterLevelMessage(int waterLevel, int waterSaturationLevel, float waterExhaustionLevel) {
        this.waterLevel = waterLevel;
        this.waterSaturationLevel = waterSaturationLevel;
        this.waterExhaustionLevel = waterExhaustionLevel;
    }

    public PlayerWaterLevelMessage(FriendlyByteBuf buf) {
        waterLevel = buf.readInt();
        waterSaturationLevel = buf.readInt();
        waterExhaustionLevel = buf.readFloat();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(waterLevel);
        buf.writeInt(waterSaturationLevel);
        buf.writeFloat(waterExhaustionLevel);
    }

    @Override
    public void process(Supplier<NetworkEvent.Context> context) {
        if (context.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            context.get().enqueueWork(() -> Minecraft.getInstance().player.getCapability(CapabilityRegistry.PLAYER_WATER_LEVEL).ifPresent(date -> {
                date.setWaterSaturationLevel(waterSaturationLevel);
                date.setWaterLevel(waterLevel);
                date.setWaterExhaustionLevel(waterExhaustionLevel);
            }));
        }
    }
}
