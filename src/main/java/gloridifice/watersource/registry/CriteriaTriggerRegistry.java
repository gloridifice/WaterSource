package gloridifice.watersource.registry;

import gloridifice.watersource.common.trigger.WaterLevelRestoredTrigger;
import net.minecraft.advancements.CriteriaTriggers;


public class CriteriaTriggerRegistry {
    public static final WaterLevelRestoredTrigger WATER_LEVEL_RESTORED_TRIGGER = CriteriaTriggers.register(new WaterLevelRestoredTrigger());
}
