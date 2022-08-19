package gloridifice.watersource.common.trigger;

import com.google.gson.JsonObject;
import gloridifice.watersource.WaterSource;
import gloridifice.watersource.registry.ConfigRegistry;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class GuideBookTrigger extends SimpleCriterionTrigger<GuideBookTrigger.TriggerInstance> {
    public static final ResourceLocation ID = new ResourceLocation(WaterSource.MODID,"guide_book");

    public ResourceLocation getId() {
        return ID;
    }

    public GuideBookTrigger.TriggerInstance createInstance(JsonObject p_70644_, EntityPredicate.Composite p_70645_, DeserializationContext p_70646_) {
        return new GuideBookTrigger.TriggerInstance(p_70645_);
    }

    public void trigger(ServerPlayer player) {
        if (ConfigRegistry.GIVE_GUIDE_BOOK_ON_JOINING_GAME.get()){
            this.trigger(player, (p_70648_) -> {
                return true;
            });
        }
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(EntityPredicate.Composite p_70654_) {
            super(GuideBookTrigger.ID, p_70654_);
        }
    }
}
