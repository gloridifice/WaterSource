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

public class PlayerLastPosCapability {
    @CapabilityInject(PlayerLastPosCapability.Data.class)
    public static Capability<PlayerLastPosCapability.Data> PLAYER_LAST_POSITION;

    public static class Storage implements Capability.IStorage<PlayerLastPosCapability.Data> {
        @Override
        public INBT writeNBT(Capability<PlayerLastPosCapability.Data> capability, PlayerLastPosCapability.Data instance, Direction side) {
            CompoundNBT compound = new CompoundNBT();
            compound.putDouble("LastX", instance.getLastX());
            compound.putDouble("LastY", instance.getLastY());
            compound.putDouble("LastZ", instance.getLastZ());
            compound.putBoolean("LastOnGround", instance.isLastOnGround());
            return compound;
        }

        @Override
        public void readNBT(Capability<PlayerLastPosCapability.Data> capability, PlayerLastPosCapability.Data instance, Direction side, INBT nbt) {
            instance.setLastX(((CompoundNBT) nbt).getInt("LastX"));
            instance.setLastY(((CompoundNBT) nbt).getInt("LastY"));
            instance.setLastZ(((CompoundNBT) nbt).getFloat("LastZ"));
            instance.setLastOnGround(((CompoundNBT) nbt).getBoolean("LastOnGround"));
        }
    }

    public static class Data {
        private double lastX, lastY, lastZ;
        private boolean lastOnGround;

        public double getLastX() {
            return lastX;
        }

        public double getLastY() {
            return lastY;
        }

        public double getLastZ() {
            return lastZ;
        }

        public boolean isLastOnGround() {
            return lastOnGround;
        }

        public void setLastX(double lastX) {
            this.lastX = lastX;
        }

        public void setLastY(double lastY) {
            this.lastY = lastY;
        }

        public void setLastZ(double lastZ) {
            this.lastZ = lastZ;
        }

        public void setLastOnGround(boolean lastOnGround) {
            this.lastOnGround = lastOnGround;
        }
    }

    public static class Provider implements ICapabilitySerializable<INBT> {
        private PlayerLastPosCapability.Data playerLastPosition = new PlayerLastPosCapability.Data();
        private Capability.IStorage<PlayerLastPosCapability.Data> storage = PLAYER_LAST_POSITION.getStorage();

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap.equals(PLAYER_LAST_POSITION)) return LazyOptional.of(() -> playerLastPosition).cast();
            else return LazyOptional.empty();
        }

        @Override
        public INBT serializeNBT() {
            return storage.writeNBT(PLAYER_LAST_POSITION, playerLastPosition, null);
        }

        @Override
        public void deserializeNBT(INBT nbt) {
            storage.readNBT(PLAYER_LAST_POSITION, playerLastPosition, null, nbt);
        }
    }
}
