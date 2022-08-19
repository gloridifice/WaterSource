package gloridifice.watersource.common.trigger;

import com.google.gson.JsonObject;
import gloridifice.watersource.WaterSource;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class WaterLevelRestoredTrigger extends SimpleCriterionTrigger<WaterLevelRestoredTrigger.TriggerInstance> {
    public static final ResourceLocation ID = new ResourceLocation(WaterSource.MODID, "water_level_restored");
    @Override
    protected TriggerInstance createInstance(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext deserializationContext) {
        return new TriggerInstance(composite);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player){
        this.trigger(player, triggerInstance -> true);
    }
    public static class TriggerInstance extends AbstractCriterionTriggerInstance{
        public TriggerInstance(EntityPredicate.Composite composite) {
            super(ID, composite);
        }
    }
}

