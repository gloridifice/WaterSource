package gloridifice.watersource.common.network;

import gloridifice.watersource.common.capability.WaterLevelCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerWaterLevelMessage implements INormalMessage {
    int waterLevel, waterSaturationLevel;
    float waterExhaustionLevel;

    public PlayerWaterLevelMessage(int waterLevel, int waterSaturationLevel, float waterExhaustionLevel) {
        this.waterLevel = waterLevel;
        this.waterSaturationLevel = waterSaturationLevel;
        this.waterExhaustionLevel = waterExhaustionLevel;
    }

    public PlayerWaterLevelMessage(PacketBuffer buf) {
        waterLevel = buf.readInt();
        waterSaturationLevel = buf.readInt();
        waterExhaustionLevel = buf.readFloat();
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeInt(waterLevel);
        buf.writeInt(waterSaturationLevel);
        buf.writeFloat(waterExhaustionLevel);
    }

    @Override
    public void process(Supplier<NetworkEvent.Context> context) {
        if (context.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            context.get().enqueueWork(() -> Minecraft.getInstance().player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(date -> {
                date.setWaterSaturationLevel(waterSaturationLevel);
                date.setWaterLevel(waterLevel);
                date.setWaterExhaustionLevel(waterExhaustionLevel);
            }));
        }
    }
}
