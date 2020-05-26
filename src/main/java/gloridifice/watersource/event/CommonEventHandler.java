package gloridifice.watersource.event;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.capability.WaterLevelCapability;
import gloridifice.watersource.common.network.PlayerWaterLevelMessage;
import gloridifice.watersource.common.network.SimpleNetworkHandler;
import gloridifice.watersource.common.recipes.ThirstItemRecipe;
import gloridifice.watersource.common.recipes.ThirstItemRecipeManager;
import gloridifice.watersource.common.recipes.WaterLevelRecipe;
import gloridifice.watersource.common.recipes.WaterLevelRecipeManager;
import gloridifice.watersource.registry.EffectRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
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
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.Random;

@Mod.EventBusSubscriber(modid = WaterSource.MODID)
public class CommonEventHandler {
    static int tick = 0;

    @SubscribeEvent
    public static void addCap(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity && !(event.getObject() instanceof FakePlayer)) {
            event.addCapability(new ResourceLocation(WaterSource.MODID, "player_thirst_level"), new WaterLevelCapability.Provider());
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
                    data.addWaterExhaustionLevel(0.1f);
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
    static double lastX,lastY,lastZ;
    static World lastWorld = null;
    static boolean lastOnGround = false;
    @SubscribeEvent
    public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event){
        tick ++;
        tick %= 8000;
        PlayerEntity player = event.player;
        World world = player.getEntityWorld();
        if (player != null && WaterLevelCapability.canPlayerAddWaterExhaustionLevel(player)){
            if (lastWorld != null){
                if (world == lastWorld && lastOnGround && player.onGround){
                    double x = Math.sqrt(Math.pow(lastX-player.getPosX(),2)+Math.pow(lastY-player.getPosY(),2)+Math.pow(lastZ-player.getPosZ(),2));
                    //System.out.println(x);
                    if (x < 5){
                      player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                          data.addWaterExhaustionLevel((float)(x/15));
                      });
                    }
                }
            }
            if (player.onGround){
                lastX = player.getPosX();
                lastY = player.getPosY();
                lastZ = player.getPosZ();
                lastWorld = world;
            }
            lastOnGround = player.onGround;

            if(tick % 10 == 0){
                //自然状态
                Biome biome = world.getBiome(player.getPosition());
                if (world.getLight(player.getPosition()) == 15 && world.getDayTime() < 11000 && world.getDayTime() > 450){
                    if (biome.getDefaultTemperature() > 0.3){
                        player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                            data.addWaterExhaustionLevel(0.0065f);
                        });
                    }
                    if (biome.getDefaultTemperature() > 0.9){
                        player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                            data.addWaterExhaustionLevel(0.0025f);
                        });
                    }
                }

                //口渴状态
                EffectInstance effectInstance = player.getActivePotionEffect(EffectRegistry.THIRST);
                if (effectInstance != null){
                    player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                        data.addWaterExhaustionLevel(0.05f + 0.02f * effectInstance.getAmplifier());
                    });
                }

            }
        }
    }
    @SubscribeEvent
    public static void onBlockBreakEvent(BlockEvent.BreakEvent event){
        PlayerEntity player = event.getPlayer();
        if (WaterLevelCapability.canPlayerAddWaterExhaustionLevel(player)){
            player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> data.addWaterExhaustionLevel(0.005f));
        }
    }

}
