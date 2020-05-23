package gloridifice.watersource.common.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WaterLevelCapability {
    @CapabilityInject(Data.class)
    public static Capability<Data> PLAYER_WATER_LEVEL;

    public static class Storage implements Capability.IStorage<Data>
    {
        @Override
        public INBT writeNBT(Capability<Data> capability, Data instance, Direction side)
        {
            CompoundNBT compound = new CompoundNBT();
            compound.putInt("PlayerWaterLevel", instance.getWaterLevel());
            compound.putInt("PlayerWaterSaturationLevel", instance.getWaterSaturationLevel());
            compound.putFloat("PlayerWaterExhaustionLevel", instance.getWaterExhaustionLevel());
            return compound;
        }

        @Override
        public void readNBT(Capability<Data> capability, Data instance, Direction side, INBT nbt)
        {
            instance.setWaterLevel(((CompoundNBT) nbt).getInt("PlayerWaterLevel"));
            instance.setWaterSaturationLevel(((CompoundNBT) nbt).getInt("PlayerWaterSaturationLevel"));
            instance.setWaterExhaustionLevel(((CompoundNBT) nbt).getFloat("PlayerWaterExhaustionLevel"));
        }
    }
    public static class Data
    {
        private int waterLevel = 20;
        private int waterSaturationLevel = 0;
        private float waterExhaustionLevel = 0;

        public void addThirstLevel(int add)
        {
            this.waterLevel = this.waterLevel + add <= 20 ? waterLevel + add : 20;
        }
        public void addThirstSaturationLevel(int add)
        {
            this.waterSaturationLevel = this.waterSaturationLevel + add <= 20 ? waterSaturationLevel + add : 20;
        }
        public void addWaterExhaustionLevel(float add){
            if (this.waterExhaustionLevel + add < 4.0f){
                this.waterExhaustionLevel += add;
            }else {
                this.waterExhaustionLevel = 0;
                reduceLevel(1);
            }
        }
        public void setWaterLevel(int temp)
        {
            this.waterLevel = temp;
        }

        public void setWaterExhaustionLevel(float waterExhaustionLevel) {
            this.waterExhaustionLevel = waterExhaustionLevel;
        }

        public int getWaterLevel()
        {
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
        public void reduceLevel(int reduce){
            if (this.waterSaturationLevel - reduce >= 0){
                waterSaturationLevel -= reduce;
            }else {
                if (waterLevel - (reduce - waterSaturationLevel) >= 0){
                    waterLevel -= reduce;
                    waterSaturationLevel = 0;
                }else {
                    waterLevel = 0;
                    waterSaturationLevel = 0;
                }
            }
        }
    }
    public static class Provider implements ICapabilitySerializable<INBT>
    {
        private Data playerThirstLevel = new Data();
        private Capability.IStorage<Data> storage = PLAYER_WATER_LEVEL.getStorage();

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
        {
            if (cap.equals(PLAYER_WATER_LEVEL))
                return LazyOptional.of(() -> playerThirstLevel).cast();
            else
                return LazyOptional.empty();
        }

        @Override
        public INBT serializeNBT()
        {
            return storage.writeNBT(PLAYER_WATER_LEVEL, playerThirstLevel, null);
        }

        @Override
        public void deserializeNBT(INBT nbt)
        {
            storage.readNBT(PLAYER_WATER_LEVEL, playerThirstLevel, null, nbt);
        }
    }
}
