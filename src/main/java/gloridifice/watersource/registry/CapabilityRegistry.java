package gloridifice.watersource.registry;

import gloridifice.watersource.common.capability.PlayerLastPosCapability;
import gloridifice.watersource.common.capability.WaterLevelCapability;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityRegistry {
    public static void init() {
        CapabilityManager.INSTANCE.register(WaterLevelCapability.Data.class, new WaterLevelCapability.Storage(), WaterLevelCapability.Data::new);
        CapabilityManager.INSTANCE.register(PlayerLastPosCapability.Data.class, new PlayerLastPosCapability.Storage(), PlayerLastPosCapability.Data::new);
    }
}
