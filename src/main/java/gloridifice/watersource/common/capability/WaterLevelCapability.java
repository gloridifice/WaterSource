package gloridifice.watersource.common.capability;

import gloridifice.watersource.registry.ConfigRegistry;
import gloridifice.watersource.registry.EffectRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraft.world.Difficulty.PEACEFUL;

public class WaterLevelCapability {
    @CapabilityInject(Data.class)
    public static Capability<Data> PLAYER_WATER_LEVEL;
    public static DamageSource BY_THIRST=(new DamageSource("byThirst")).setDamageBypassesArmor().setDifficultyScaled();
    public static boolean canPlayerAddWaterExhaustionLevel(PlayerEntity player) {
        return !(player instanceof FakePlayer) && !player.isCreative() && !player.isSpectator() && player.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL) != null && player.getEntityWorld().getDifficulty() != PEACEFUL;
    }

    public static class Storage implements Capability.IStorage<Data> {
        @Override
        public INBT writeNBT(Capability<Data> capability, Data instance, Direction side) {
            CompoundNBT compound = new CompoundNBT();
            compound.putInt("PlayerWaterLevel", instance.getWaterLevel());
            compound.putInt("PlayerWaterSaturationLevel", instance.getWaterSaturationLevel());
            compound.putFloat("PlayerWaterExhaustionLevel", instance.getWaterExhaustionLevel());
            return compound;
        }

        @Override
        public void readNBT(Capability<Data> capability, Data instance, Direction side, INBT nbt) {
            instance.setWaterLevel(((CompoundNBT) nbt).getInt("PlayerWaterLevel"));
            instance.setWaterSaturationLevel(((CompoundNBT) nbt).getInt("PlayerWaterSaturationLevel"));
            instance.setWaterExhaustionLevel(((CompoundNBT) nbt).getFloat("PlayerWaterExhaustionLevel"));
        }
    }

    public static class Data {
        private int waterLevel = 20;
        private int waterSaturationLevel = 6;
        private float waterExhaustionLevel = 0;

        public void addWaterLevel(int add) {
            this.waterLevel = Math.min(this.waterLevel + add, 20);
        }

        public void addWaterSaturationLevel(int add) {
            this.waterSaturationLevel = Math.min(this.waterSaturationLevel + add, 20);
        }

        protected void addExhaustion(float add) {
            reduceLevel((int) ((this.waterExhaustionLevel + add) / 4.0f));
            this.waterExhaustionLevel = (this.waterExhaustionLevel + add) % 4.0f;
        }

        public void addExhaustion(PlayerEntity player, float add) {
            float finalValue = (float) ((double) add * ConfigRegistry.WATER_REDUCING_RATE.get());
            EffectInstance effect = player.getActivePotionEffect(EffectRegistry.THIRST);
            if (effect != null) {
                addExhaustion(finalValue * (3 + effect.getAmplifier()) / 2F);
            }
            else addExhaustion(finalValue);
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
            }
            else {
                if (waterLevel - (reduce - waterSaturationLevel) >= 0) {
                    waterLevel -= reduce;
                    waterSaturationLevel = 0;
                }
                else {
                    waterLevel = 0;
                    waterSaturationLevel = 0;
                }
            }
        }

        public void restoreWater(int restore) {
            this.waterLevel = Math.min(waterLevel + restore, 20);
            if (this.waterLevel == 20) this.waterSaturationLevel = Math.min(waterLevel + restore, 20);
        }
        public void award(PlayerEntity player) {
            if (player.world.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION) && getWaterLevel() > 17 && player.getFoodStats().getFoodLevel() > 10 && player.getHealth() < player.getMaxHealth()) {
                player.heal(1);
                switch (player.getEntityWorld().getDifficulty()) {
                    case PEACEFUL:
                        break;
                    case EASY:
                        addExhaustion(5.0f);
                        player.addExhaustion(0.6F);
                        break;
                    case NORMAL:
                        addExhaustion(6.0f);
                        player.addExhaustion(0.8F);
                        break;
                    case HARD:
                        addExhaustion(7.0f);
                        player.addExhaustion(1.0F);
                        break;
                }
            }
        }

        public void punishment(PlayerEntity player) {
            int weAmp = ConfigRegistry.WEAKNESS_EFFECT_AMPLIFIER.get();
            int slAmp = ConfigRegistry.WEAKNESS_EFFECT_AMPLIFIER.get();
            if (getWaterLevel() <= 6) {
                switch (player.getEntityWorld().getDifficulty()) {
                    case PEACEFUL:
                        break;
                    case EASY:
                        if (weAmp > -1 && (player.getActivePotionEffect(Effects.WEAKNESS) == null || player.getActivePotionEffect(Effects.WEAKNESS).getDuration() <= 100))
                            player.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 400, weAmp, false, false));
                        if (slAmp > -1 && (player.getActivePotionEffect(Effects.SLOWNESS) == null || player.getActivePotionEffect(Effects.SLOWNESS).getDuration() <= 100))
                            player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 400, slAmp, false, false));
                        break;
                    default:
                        if (weAmp > -1 && (player.getActivePotionEffect(Effects.WEAKNESS) == null || player.getActivePotionEffect(Effects.WEAKNESS).getDuration() <= 100))
                            player.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 400, weAmp + 1, false, false));
                        if (slAmp > -1 && (player.getActivePotionEffect(Effects.SLOWNESS) == null || player.getActivePotionEffect(Effects.SLOWNESS).getDuration() <= 100))
                            player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 400, slAmp, false, false));
                        break;
                }
            }
            int i = 0;
            if (player.getEntityWorld().getDifficulty() != Difficulty.HARD) i = 1;
            if (getWaterLevel() == 0 && player.getHealth() > i) {
                if (!player.getEntityWorld().isRemote()) {
                    player.attackEntityFrom(BY_THIRST, 1.0f);
                }
                else
                    player.getEntityWorld().playSound(player, player.getPosition(), SoundEvents.ENTITY_GUARDIAN_ATTACK, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
        }
    }

    public static class Provider implements ICapabilitySerializable<INBT> {
        private Data playerWaterLevel = new Data();
        private Capability.IStorage<Data> storage = PLAYER_WATER_LEVEL.getStorage();

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap.equals(PLAYER_WATER_LEVEL)) return LazyOptional.of(() -> playerWaterLevel).cast();
            else return LazyOptional.empty();
        }

        @Override
        public INBT serializeNBT() {
            return storage.writeNBT(PLAYER_WATER_LEVEL, playerWaterLevel, null);
        }

        @Override
        public void deserializeNBT(INBT nbt) {
            storage.readNBT(PLAYER_WATER_LEVEL, playerWaterLevel, null, nbt);
        }
    }
}
