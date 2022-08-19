package gloridifice.watersource.common.capability;

import gloridifice.watersource.common.network.PlayerWaterLevelMessage;
import gloridifice.watersource.common.network.SimpleNetworkHandler;
import gloridifice.watersource.helper.WaterLevelUtil;
import gloridifice.watersource.registry.*;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WaterLevelCapability {

    public static Tag writeNBT(Capability<WaterLevelCapability> capability, WaterLevelCapability instance, Direction side) {
        CompoundTag compound = new CompoundTag();
        compound.putInt("PlayerWaterLevel", instance.getWaterLevel());
        compound.putInt("PlayerWaterSaturationLevel", instance.getWaterSaturationLevel());
        compound.putFloat("PlayerWaterExhaustionLevel", instance.getWaterExhaustionLevel());
        return compound;
    }

    public static void readNBT(Capability<WaterLevelCapability> capability, WaterLevelCapability instance, Direction side, Tag nbt) {
        instance.setWaterLevel(((CompoundTag) nbt).getInt("PlayerWaterLevel"));
        instance.setWaterSaturationLevel(((CompoundTag) nbt).getInt("PlayerWaterSaturationLevel"));
        instance.setWaterExhaustionLevel(((CompoundTag) nbt).getFloat("PlayerWaterExhaustionLevel"));
    }


    private int waterLevel = 20;
    private int waterSaturationLevel = 6;
    private float waterExhaustionLevel = 0;

    public void addWaterLevel(Player player, int add) {
        this.waterLevel = Math.min(this.waterLevel + add, 20);
        restoreUpdate(player);
    }

    public void addWaterSaturationLevel(Player player, int add) {
        this.waterSaturationLevel = Math.min(this.waterSaturationLevel + add, 20);
        restoreUpdate(player);
    }

    protected void addExhaustion(float add) {
        reduceLevel((int) ((this.waterExhaustionLevel + add) / 4.0f));
        this.waterExhaustionLevel = (this.waterExhaustionLevel + add) % 4.0f;
    }

    public void addExhaustion(Player player, float add) {
        float moisturizingRate = WaterLevelUtil.getMoisturizingRate(player);
        float finalValue = (float) ((double) add * ConfigRegistry.WATER_REDUCING_RATE.get()) * moisturizingRate;
        MobEffectInstance effect = player.getEffect(MobEffectRegistry.THIRST.get());
        if (effect != null) {
            addExhaustion(finalValue * (4 + effect.getAmplifier()) / 2);
        } else addExhaustion(finalValue);
    }

    public void setWaterLevel(int temp) {
        this.waterLevel = temp;
    }

    public void setWaterExhaustionLevel(float waterExhaustionLevel) {
        this.waterExhaustionLevel = waterExhaustionLevel;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public void setWaterSaturationLevel(int waterSaturationLevel) {
        this.waterSaturationLevel = waterSaturationLevel;
    }

    public int getWaterSaturationLevel() {
        return waterSaturationLevel;
    }

    public float getWaterExhaustionLevel() {
        return waterExhaustionLevel;
    }

    public void reduceLevel(int reduce) {
        if (this.waterSaturationLevel - reduce >= 0) {
            waterSaturationLevel -= reduce;
        } else {
            if (waterLevel - (reduce - waterSaturationLevel) >= 0) {
                waterLevel -= reduce;
                waterSaturationLevel = 0;
            } else {
                waterLevel = 0;
                waterSaturationLevel = 0;
            }
        }
    }

    public void restoreWater(Player player, int restore) {
        this.waterLevel = Math.min(waterLevel + restore, 20);
        if (this.waterLevel == 20) this.waterSaturationLevel = Math.min(waterLevel + restore, 20);
        restoreUpdate(player);
    }

    public static void restoreUpdate(Player player) {
        if (!player.level.isClientSide()) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            CriteriaTriggerRegistry.WATER_LEVEL_RESTORED_TRIGGER.trigger(serverPlayer);
            player.getCapability(CapabilityRegistry.PLAYER_WATER_LEVEL).ifPresent(t -> SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new PlayerWaterLevelMessage(t.getWaterLevel(), t.getWaterSaturationLevel(), t.getWaterExhaustionLevel())));
        }

    }

    public void award(Player player) {
        FoodData foodData = player.getFoodData();
        if (player.level.getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION) && getWaterLevel() > 17 && player.getFoodData().getFoodLevel() > 10 && player.getHealth() < player.getMaxHealth()) {
            player.heal(1);
            switch (player.level.getDifficulty()) {
                case PEACEFUL:
                    break;
                case EASY:
                    addExhaustion(5.0f);
                    foodData.addExhaustion(0.6F);
                    break;
                case NORMAL:
                    addExhaustion(6.0f);
                    foodData.addExhaustion(0.8F);
                    break;
                case HARD:
                    addExhaustion(7.0f);
                    foodData.addExhaustion(1.0F);
                    break;
            }
        }
    }

    public void punishment(Player player) {

        if (getWaterLevel() <= 6) {
            switch (player.level.getDifficulty()) {
                case PEACEFUL:
                    break;
                case EASY:
                    mobEffectPunishment(player, 0);
                    break;
                default:
                    mobEffectPunishment(player, 1);
                    break;
            }
        }
        int i = 0;
        if (player.level.getDifficulty() != Difficulty.HARD) i = 1;
        if (getWaterLevel() == 0 && player.getHealth() > i) {
            if (!player.level.isClientSide()) {
                player.hurt(new DamageSource("byThirst"), 1.0f);
            } else {
                player.level.playSound(player, player, SoundEvents.GUARDIAN_ATTACK, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        }
    }


    protected static void mobEffectPunishment(Player player, int level) {
        int weAmp = ConfigRegistry.WEAKNESS_EFFECT_AMPLIFIER.get();
        int slAmp = ConfigRegistry.WEAKNESS_EFFECT_AMPLIFIER.get();
        MobEffectInstance weaknessEffect = player.getEffect(MobEffects.WEAKNESS);
        MobEffectInstance movementSlowDownEffect = player.getEffect(MobEffects.MOVEMENT_SLOWDOWN);
        if (weAmp > -1 && (weaknessEffect == null || weaknessEffect.getDuration() <= 100))
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 400, weAmp + level, false, false));
        if (slAmp > -1 && movementSlowDownEffect == null || movementSlowDownEffect.getDuration() <= 100)
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 400, slAmp, false, false));

    }

    public static class Provider implements ICapabilitySerializable<Tag> {
        @Nonnull
        private final WaterLevelCapability instance;

        private final LazyOptional<WaterLevelCapability> handler;

        public Provider() {
            instance = new WaterLevelCapability();
            handler = LazyOptional.of(this::getInstance);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap == null) return LazyOptional.empty();
            return CapabilityRegistry.PLAYER_WATER_LEVEL.orEmpty(cap, handler);
        }

        public WaterLevelCapability getInstance() {
            return instance;
        }

        @Override
        public Tag serializeNBT() {
            return WaterLevelCapability.writeNBT(CapabilityRegistry.PLAYER_WATER_LEVEL, instance, null);
        }

        @Override
        public void deserializeNBT(Tag nbt) {
            WaterLevelCapability.readNBT(CapabilityRegistry.PLAYER_WATER_LEVEL, instance, null, nbt);
        }
    }

}
