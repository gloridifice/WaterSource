package gloridifice.watersource.registry;

import gloridifice.watersource.common.capability.PlayerLastPosCapability;
import gloridifice.watersource.common.capability.WaterLevelCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CapabilityRegistry {
    public static Capability<WaterLevelCapability> PLAYER_WATER_LEVEL = CapabilityManager.get(new CapabilityToken<>(){});
    public static Capability<PlayerLastPosCapability> PLAYER_LAST_POSITION = CapabilityManager.get(new CapabilityToken<>(){});

    @SubscribeEvent
    public static void registryCapability(RegisterCapabilitiesEvent event){
        event.register(WaterLevelCapability.class);
        event.register(PlayerLastPosCapability.class);
    }
}
