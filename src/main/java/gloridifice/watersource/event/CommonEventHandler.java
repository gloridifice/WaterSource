package gloridifice.watersource.event;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.capability.WaterLevelCapability;
import gloridifice.watersource.common.network.PlayerWaterLevelMessage;
import gloridifice.watersource.common.network.SimpleNetworkHandler;
import gloridifice.watersource.common.recipes.WaterLevelRecipe;
import gloridifice.watersource.registry.RecipesRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = WaterSource.MODID)
public class CommonEventHandler {
    @SubscribeEvent
    public static void addCap(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity && !(event.getObject() instanceof FakePlayer)) {
            event.addCapability(new ResourceLocation(WaterSource.MODID, "player_thirst_level"), new WaterLevelCapability.Provider());
        }
    }

    @SubscribeEvent
    public static void onLivingEntityUseItemEventFinish(LivingEntityUseItemEvent.Finish event) {
        LivingEntity entity = event.getEntityLiving();
        System.out.println(0);
        if (entity instanceof PlayerEntity && !(entity instanceof FakePlayer)) {
            WaterLevelRecipe recipe = RecipesRegistry.THIRST_LEVEL.getRecipeFromItemStack(event.getItem());
            System.out.println(event.getItem());
            if (recipe != null) {
                entity.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                    System.out.println(2);
                    data.addThirstLevel(recipe.getWaterLevel());
                    data.addThirstSaturationLevel(recipe.getWaterSaturationLevel());
                });
            }
        }
    }

    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            event.getEntityLiving().getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                data.addWaterExhaustionLevel(0.5f);
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerEventClone(PlayerEvent.Clone event) {
        if (!(event.getPlayer() instanceof FakePlayer) && event.getPlayer() instanceof ServerPlayerEntity && !event.isWasDeath()) {//TODO 配置文件：玩家死亡后是否保存恢复水分值，默认否
            System.out.println(0);
            if (event.getPlayer().getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL) != null) {
                System.out.println(1);
                event.getPlayer().getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(date -> {
                    event.getOriginal().getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(t -> {
                        date.setWaterLevel(t.getWaterLevel());
                        date.setWaterExhaustionLevel(t.getWaterExhaustionLevel());
                        date.setWaterSaturationLevel(t.getWaterSaturationLevel());
                        System.out.println(2);
                    });
                });
                event.getPlayer().getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(t -> SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new PlayerWaterLevelMessage(t.getWaterLevel(), t.getWaterSaturationLevel(),t.getWaterExhaustionLevel())));
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event){
        if (event.getPlayer() instanceof ServerPlayerEntity && !(event.getPlayer() instanceof FakePlayer))
        {
            event.getPlayer().getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(t -> SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new PlayerWaterLevelMessage(t.getWaterLevel(), t.getWaterSaturationLevel(),t.getWaterExhaustionLevel())));
        }
    }
    @SubscribeEvent
    public static void EntityJoinWorldEvent(EntityJoinWorldEvent event){
        if (event.getEntity() instanceof ServerPlayerEntity && !(event.getEntity() instanceof FakePlayer))
        {
            event.getEntity().getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(t -> SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getEntity()), new PlayerWaterLevelMessage(t.getWaterLevel(), t.getWaterSaturationLevel(),t.getWaterExhaustionLevel())));
        }
    }
}
