package gloridifice.watersource.event;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.capability.PlayerLastPosCapability;
import gloridifice.watersource.common.capability.WaterLevelCapability;
import gloridifice.watersource.common.network.PlayerWaterLevelMessage;
import gloridifice.watersource.common.network.SimpleNetworkHandler;
import gloridifice.watersource.common.recipe.ThirstItemRecipe;
import gloridifice.watersource.common.recipe.ThirstItemRecipeManager;
import gloridifice.watersource.common.recipe.WaterLevelRecipe;
import gloridifice.watersource.common.recipe.WaterLevelRecipeManager;
import gloridifice.watersource.registry.CapabilityRegistry;
import gloridifice.watersource.registry.EffectRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import roito.afterthedrizzle.common.capability.CapabilitiesRegistry;
import roito.afterthedrizzle.common.capability.CapabilityPlayerTemperature;
import roito.afterthedrizzle.common.environment.temperature.ApparentTemperature;

import java.util.Random;

@Mod.EventBusSubscriber(modid = WaterSource.MODID)
public class CommonEventHandler {
    static int tick = 0;

    @SubscribeEvent
    public static void addCap(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity && !(event.getObject() instanceof FakePlayer)) {
            event.addCapability(new ResourceLocation(WaterSource.MODID, "player_thirst_level"), new WaterLevelCapability.Provider());
            event.addCapability(new ResourceLocation(WaterSource.MODID,"player_last_position"), new PlayerLastPosCapability.Provider());
        }
    }

    @SubscribeEvent
    public static void onLivingEntityUseItemEventFinish(LivingEntityUseItemEvent.Finish event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity instanceof PlayerEntity && !(entity instanceof FakePlayer)) {
            Random rand = new Random();
            WaterLevelRecipe wRecipe = WaterLevelRecipeManager.getRecipeFromItemStack(event.getItem());
            if (wRecipe != null) {
                entity.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                    data.addWaterLevel(wRecipe.getWaterLevel());
                    data.addWaterSaturationLevel(wRecipe.getWaterSaturationLevel());
                });
            }
            ThirstItemRecipe tRecipe = ThirstItemRecipeManager.getRecipeFromItemStick(event.getItem());
            if (tRecipe != null){
                if (rand.nextInt(100) < tRecipe.getProbability()){
                    entity.addPotionEffect(new EffectInstance(EffectRegistry.THIRST,tRecipe.getDuration(),tRecipe.getAmplifier()));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event) {
        Entity entity = event.getEntityLiving();
        if (entity instanceof PlayerEntity) {
            if (WaterLevelCapability.canPlayerAddWaterExhaustionLevel((PlayerEntity)entity)){
                entity.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                    if (entity.isSprinting()){
                        data.addExhaustion((PlayerEntity) entity,0.24f);
                    }else data.addExhaustion((PlayerEntity) entity,0.14f);
                });
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerEventClone(PlayerEvent.Clone event) {
        if (!(event.getPlayer() instanceof FakePlayer) && event.getPlayer() instanceof ServerPlayerEntity && !event.isWasDeath()) {//TODO 配置文件：玩家死亡后是否保存恢复水分值，默认否
            if (event.getPlayer().getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL) != null) {
                event.getPlayer().getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(date -> {
                    event.getOriginal().getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(t -> {
                        date.setWaterLevel(t.getWaterLevel());
                        date.setWaterExhaustionLevel(t.getWaterExhaustionLevel());
                        date.setWaterSaturationLevel(t.getWaterSaturationLevel());
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

    @SubscribeEvent
    public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event){
        tick ++;
        tick %= 8000;
        PlayerEntity player = event.player;
        World world = player.getEntityWorld();
        if (player != null && WaterLevelCapability.canPlayerAddWaterExhaustionLevel(player)){
            if (tick % 2 == 0){
                player.getCapability(PlayerLastPosCapability.PLAYER_LAST_POSITION).ifPresent(data -> {
                    boolean lastOnGround = data.isLastOnGround();
                    double lastX = data.getLastX();
                    double lastY = data.getLastY();
                    double lastZ = data.getLastZ();
                    if (lastOnGround && player.onGround || player.isInWater()) {
                        double x = Math.sqrt(Math.pow(lastX - player.getPosX(), 2) + Math.pow(lastY - player.getPosY(), 2) + Math.pow(lastZ - player.getPosZ(), 2));
                        if (x < 5) {
                            player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(dataW -> {
                                if (player.isSprinting()){
                                    dataW.addExhaustion(player, (float) (x / 20));
                                }else dataW.addExhaustion(player, (float) (x / 40));

                            });
                        }
                    }

                    if (player.onGround || player.isInWater()) {
                        data.setLastX(player.getPosX());
                        data.setLastY(player.getPosY());
                        data.setLastZ(player.getPosZ());
                        data.setLastOnGround(true);
                    }else data.setLastOnGround(false);
                });
            }

            if(tick % 10 == 0){
                //自然状态
                if (ModList.get().isLoaded("afterthedrizzle") && player.getCapability(CapabilityPlayerTemperature.PLAYER_TEMP) != null){
                    player.getCapability(CapabilityPlayerTemperature.PLAYER_TEMP).ifPresent(d -> {
                        if (d.getApparentTemperature() == ApparentTemperature.HOT){
                            player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                                data.addExhaustion(player, 0.0075f);
                            });
                        }
                        if (d.getApparentTemperature() == ApparentTemperature.HOT){
                            player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                                data.addExhaustion(player, 0.0135f);
                            });
                        }
                    });
                }else {
                    Biome biome = world.getBiome(player.getPosition());
                    if (world.getLight(player.getPosition()) == 15 && world.getDayTime() < 11000 && world.getDayTime() > 450 && !world.isRainingAt(player.getPosition())) {
                        if (biome.getDefaultTemperature() > 0.3) {
                            player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                                data.addExhaustion(player, 0.0075f);
                            });
                        }
                        if (biome.getDefaultTemperature() > 0.9) {
                            player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                                data.addExhaustion(player, 0.0055f);
                            });
                        }
                    }
                }
                //口渴状态
                EffectInstance effectInstance = player.getActivePotionEffect(EffectRegistry.THIRST);
                if (effectInstance != null){
                    player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                        data.addExhaustion(player,0.05f + 0.02f * effectInstance.getAmplifier());
                    });
                }
            }
        }
        if (tick % 60 == 0 && player != null && !(player instanceof FakePlayer)){
            player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                data.punishment(player);
            });
            if (world.getDifficulty() == Difficulty.PEACEFUL){
                player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                    data.restoreWaterLevel(2);
                });
            }
        }
    }
    @SubscribeEvent
    public static void onBlockBreakEvent(BlockEvent.BreakEvent event){
        PlayerEntity player = event.getPlayer();
        if (WaterLevelCapability.canPlayerAddWaterExhaustionLevel(player)){
            player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> data.addExhaustion(player,0.005f));
        }
    }

}
