package gloridifice.watersource.registry;

import gloridifice.watersource.common.capability.PlayerLastPosCapability;
import gloridifice.watersource.common.capability.WaterLevelCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class CapabilityRegistry {
    @CapabilityInject(WaterLevelCapability.class)
    public static Capability<WaterLevelCapability> PLAYER_WATER_LEVEL = null;

    @CapabilityInject(PlayerLastPosCapability.class)
    public static Capability<PlayerLastPosCapability> PLAYER_LAST_POSITION = null;

    public static void registryCapability(RegisterCapabilitiesEvent event){
        event.register(WaterLevelCapability.class);
        event.register(PlayerLastPosCapability.class);
    }
}
