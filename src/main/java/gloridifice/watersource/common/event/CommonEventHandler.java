package gloridifice.watersource.common.event;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.capability.PlayerLastPosCapability;
import gloridifice.watersource.common.capability.WaterLevelCapability;
import gloridifice.watersource.common.network.PlayerWaterLevelMessage;
import gloridifice.watersource.common.network.SimpleNetworkHandler;
import gloridifice.watersource.common.recipe.*;
import gloridifice.watersource.registry.BlockRegistry;
import gloridifice.watersource.registry.EffectRegistry;
import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

import static gloridifice.watersource.registry.ConfigRegistry.RESET_WATER_LEVEL_IN_DEATH;
import static gloridifice.watersource.registry.RecipeSerializersRegistry.THIRST_ITEM_RECIPE_SERIALIZER;

@Mod.EventBusSubscriber(modid = WaterSource.MODID)
public class CommonEventHandler {
    static int tick = 0;

    @SubscribeEvent
    public static void addCap(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity && !(event.getObject() instanceof FakePlayer)) {
            event.addCapability(new ResourceLocation(WaterSource.MODID, "player_thirst_level"), new WaterLevelCapability.Provider());
            event.addCapability(new ResourceLocation(WaterSource.MODID, "player_last_position"), new PlayerLastPosCapability.Provider());
        }
    }

    @SubscribeEvent
    public static void onLivingEntityUseItemEventFinish(LivingEntityUseItemEvent.Finish event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity instanceof PlayerEntity && !(entity instanceof FakePlayer)) {
            Random rand = new Random();
            WaterLevelItemRecipe wRecipe = WaterLevelRecipeManager.getRecipeFromItemStack(event.getItem());

            IThirstRecipe tRecipe = ThirstItemRecipe.getRecipeFromItem(entity.world, event.getItem());
            if (wRecipe != null) {
                entity.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                    data.addWaterLevel(wRecipe.getWaterLevel());
                    if(tRecipe == null){
                        data.addWaterSaturationLevel(wRecipe.getWaterSaturationLevel());
                    }
                });
            }
            if (tRecipe != null) {
                if (rand.nextInt(100) < tRecipe.getProbability()) {
                    entity.addPotionEffect(new EffectInstance(EffectRegistry.THIRST, tRecipe.getDuration(), tRecipe.getAmplifier()));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event) {
        Entity entity = event.getEntityLiving();
        if (entity instanceof PlayerEntity) {
            if (WaterLevelCapability.canPlayerAddWaterExhaustionLevel((PlayerEntity) entity)) {
                entity.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                    if (entity.isSprinting()) {
                        data.addExhaustion((PlayerEntity) entity, 0.24f);
                    } else data.addExhaustion((PlayerEntity) entity, 0.14f);
                });
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerEventClone(PlayerEvent.Clone event) {
        boolean flag = false;
        flag = !(event.getPlayer() instanceof FakePlayer) && event.getPlayer() instanceof ServerPlayerEntity;
        if (RESET_WATER_LEVEL_IN_DEATH.get()) {
            flag = flag && !event.isWasDeath();
        }
        if (flag && event.getPlayer().getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL) != null) {
            event.getPlayer().getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(date -> {
                event.getOriginal().getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(t -> {
                    date.setWaterLevel(t.getWaterLevel());
                    date.setWaterExhaustionLevel(t.getWaterExhaustionLevel());
                    date.setWaterSaturationLevel(t.getWaterSaturationLevel());
                });
            });
            event.getPlayer().getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(t -> SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new PlayerWaterLevelMessage(t.getWaterLevel(), t.getWaterSaturationLevel(), t.getWaterExhaustionLevel())));
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() instanceof ServerPlayerEntity && !(event.getPlayer() instanceof FakePlayer)) {
            event.getPlayer().getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(t -> SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new PlayerWaterLevelMessage(t.getWaterLevel(), t.getWaterSaturationLevel(), t.getWaterExhaustionLevel())));
        }
    }

    @SubscribeEvent
    public static void EntityJoinWorldEvent(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof ServerPlayerEntity && !(event.getEntity() instanceof FakePlayer)) {
            event.getEntity().getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(t -> SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getEntity()), new PlayerWaterLevelMessage(t.getWaterLevel(), t.getWaterSaturationLevel(), t.getWaterExhaustionLevel())));
        }
    }

    @SubscribeEvent
    public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        tick++;
        tick %= 8000;
        PlayerEntity player = event.player;
        World world = player.getEntityWorld();
        if (player != null && WaterLevelCapability.canPlayerAddWaterExhaustionLevel(player)) {
            if (tick % 2 == 0) {
                player.getCapability(PlayerLastPosCapability.PLAYER_LAST_POSITION).ifPresent(data -> {
                    boolean lastOnGround = data.isLastOnGround();
                    double lastX = data.getLastX();
                    double lastY = data.getLastY();
                    double lastZ = data.getLastZ();
                    if (lastOnGround && player.isOnGround() || player.isInWater()) {
                        double x = Math.sqrt(Math.pow(lastX - player.getPosX(), 2) + Math.pow(lastY - player.getPosY(), 2) + Math.pow(lastZ - player.getPosZ(), 2));
                        if (x < 5) {
                            player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(dataW -> {
                                if (player.isSprinting()) {
                                    dataW.addExhaustion(player, (float) (x / 15));
                                } else dataW.addExhaustion(player, (float) (x / 30));
                            });
                        }
                    }

                    if (player.isOnGround() || player.isInWater()) {
                        data.setLastX(player.getPosX());
                        data.setLastY(player.getPosY());
                        data.setLastZ(player.getPosZ());
                        data.setLastOnGround(true);
                    } else data.setLastOnGround(false);
                });
            }

            if (tick % 10 == 0) {
                //Natural State
/*                if (ModList.get().isLoaded("afterthedrizzle") && player.getCapability(CapabilityPlayerTemperature.PLAYER_TEMP) != null) {
                    player.getCapability(CapabilityPlayerTemperature.PLAYER_TEMP).ifPresent(d -> {
                        if (d.getApparentTemperature() == ApparentTemperature.HOT) {
                            player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                                data.addExhaustion(player, 0.0075f);
                            });
                        }
                        if (d.getApparentTemperature() == ApparentTemperature.HOT) {
                            player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                                data.addExhaustion(player, 0.0135f);
                            });
                        }
                    });
                } else {*/
                    Biome biome = world.getBiome(player.getPosition());
                    if (world.getLight(player.getPosition()) == 15 && world.getDayTime() < 11000 && world.getDayTime() > 450 && !world.isRainingAt(player.getPosition())) {
                        if (biome.getTemperature() > 0.3) {
                            player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                                data.addExhaustion(player, 0.0075f);
                            });
                        }
                        if (biome.getTemperature() > 0.9) {
                            player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                                data.addExhaustion(player, 0.0055f);
                            });
                        }
                    }
                //}
                //Thirty State
                EffectInstance effectInstance = player.getActivePotionEffect(EffectRegistry.THIRST);
                if (effectInstance != null) {
                    player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                        data.addExhaustion(player, 0.1f + 0.05f * effectInstance.getAmplifier());
                    });
                }
            }
        }
        //Punishment/Reward - 5s
        if (tick % 250 == 0 && player != null && !(player instanceof FakePlayer)) {
            player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                if (!player.isCreative()){
                    data.punishment(player);
                    data.award(player);
                }
            });
        }
        //Restore water level in Peaceful difficulty mode - 3s
        if (tick % 150 == 0 && player != null && !(player instanceof FakePlayer)) {
            if (world.getDifficulty() == Difficulty.PEACEFUL) {
                player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                    data.restoreWaterLevel(2);
                });
            }
        }
        //Update water between server and client - 30s
        if (tick % 1500 == 0 && player != null && !(player instanceof FakePlayer) && !world.isRemote) {
            player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
                SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PlayerWaterLevelMessage(data.getWaterLevel(), data.getWaterSaturationLevel(), data.getWaterExhaustionLevel()));
            });
        }
    }

    @SubscribeEvent
    public static void onBlockBreakEvent(BlockEvent.BreakEvent event) {
        PlayerEntity player = event.getPlayer();
        if (WaterLevelCapability.canPlayerAddWaterExhaustionLevel(player)) {
            player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> data.addExhaustion(player, 0.005f));
        }
    }
    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event){
        BlockState state = event.getWorld().getBlockState(event.getPos());
        ItemStack heldItem = event.getItemStack();
        PlayerEntity player = event.getPlayer();
        if (heldItem.getItem() instanceof AxeItem && state.getBlock() == BlockRegistry.BLOCK_COCONUT_TREE_LOG){
            event.getWorld().playSound(player,event.getPos(), SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!event.getWorld().isRemote()){
                event.getWorld().setBlockState(event.getPos(),BlockRegistry.BLOCK_STRIPPED_COCONUT_TREE_LOG.getDefaultState().with(RotatedPillarBlock.AXIS,state.get(RotatedPillarBlock.AXIS)));
                heldItem.damageItem(1, player, (data) -> {data.sendBreakAnimation(event.getHand());});
            }
        }
    }
    @SubscribeEvent
    public static void onLivingAttacked(LivingAttackEvent event){
        EffectInstance effectInstance = event.getEntityLiving().getActivePotionEffect(EffectRegistry.ACCOMPANYING_SOUL);
        if (effectInstance != null){
            if (event.getEntityLiving().getEntityWorld().getDimensionKey() == World.THE_NETHER){
                event.getEntityLiving().heal(event.getAmount() * (0.25f + (float) effectInstance.getAmplifier() * 0.06f));
            }else event.getEntityLiving().heal(event.getAmount() * (0.2f + (float) effectInstance.getAmplifier() * 0.05f));
        }
    }
    @SubscribeEvent
    public static void getVanillaFurnaceFuelValue(FurnaceFuelBurnTimeEvent event) {
        if (event.getItemStack().getItem() == BlockRegistry.ITEM_DIRTY_STRAINER) {
            event.setBurnTime(600);
            // 可以设定为 0。0 代表“这个物品不是燃料”，更准确地说是“这个物品燃烧时间是 0”。
            // 可以设定为 -1。-1 代表“由原版逻辑来决定”。
            // 可通过 event.getBurnTime() 获得当前决定的燃烧时间。
            // 这个事件可以取消。取消意味着后续的 Event listener 将不会收到这个事件，进而
            // 无法修改燃烧时间。
        }
    }
}
