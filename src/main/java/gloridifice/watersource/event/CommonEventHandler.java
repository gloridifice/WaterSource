package gloridifice.watersource.event;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.capability.WaterLevelCapability;
import gloridifice.watersource.common.recipes.WaterLevelRecipe;
import gloridifice.watersource.registry.RecipesRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WaterSource.MODID)
public class CommonEventHandler {
    @SubscribeEvent
    public static void addCap(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity && !(event.getObject() instanceof FakePlayer))
        {
            event.addCapability(new ResourceLocation(WaterSource.MODID, "player_thirst_level"), new WaterLevelCapability.Provider());
        }
    }
    @SubscribeEvent
    public static void onLivingEntityUseItemEventFinish(LivingEntityUseItemEvent.Finish event){
        LivingEntity entity = event.getEntityLiving();
        if (entity instanceof PlayerEntity){
            WaterLevelRecipe recipe = RecipesRegistry.THIRST_LEVEL.getRecipeFromItemStack(event.getResultStack());
            if (recipe != null){
                entity.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                    data.addThirstLevel(recipe.getWaterLevel());
                    data.addThirstSaturationLevel(recipe.getWaterSaturationLevel());
                });
            }
        }
    }
    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event){
        if (event.getEntityLiving() instanceof PlayerEntity){
            event.getEntityLiving().getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                data.addWaterExhaustionLevel(0.5f);
            });
        }
    }
}
