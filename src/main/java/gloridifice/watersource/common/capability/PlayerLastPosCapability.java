package gloridifice.watersource.common.capability;

import gloridifice.watersource.registry.CapabilityRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerLastPosCapability {
    public static Tag writeNBT(Capability<PlayerLastPosCapability> capability, PlayerLastPosCapability instance, Direction side) {
        CompoundTag compound = new CompoundTag();
        compound.putDouble("LastX", instance.getLastX());
        compound.putDouble("LastY", instance.getLastY());
        compound.putDouble("LastZ", instance.getLastZ());
        compound.putBoolean("LastOnGround", instance.isLastOnGround());
        return compound;
    }
    public static void readNBT(Capability<PlayerLastPosCapability> capability, PlayerLastPosCapability instance, Direction side, Tag nbt) {
        instance.setLastX(((CompoundTag) nbt).getInt("LastX"));
        instance.setLastY(((CompoundTag) nbt).getInt("LastY"));
        instance.setLastZ(((CompoundTag) nbt).getFloat("LastZ"));
        instance.setLastOnGround(((CompoundTag) nbt).getBoolean("LastOnGround"));
    }
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


    public static class Provider implements ICapabilitySerializable<Tag>, ICapabilityProvider {
        private final PlayerLastPosCapability playerLastPosition = new PlayerLastPosCapability();
        private final LazyOptional<PlayerLastPosCapability> handler = LazyOptional.of(() -> playerLastPosition);
        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap == null) return LazyOptional.empty();
            return CapabilityRegistry.PLAYER_LAST_POSITION.orEmpty(cap, handler);
        }

        @Override
        public Tag serializeNBT() {
            return PlayerLastPosCapability.writeNBT(CapabilityRegistry.PLAYER_LAST_POSITION, playerLastPosition, null);
        }

        @Override
        public void deserializeNBT(Tag nbt) {
            PlayerLastPosCapability.readNBT(CapabilityRegistry.PLAYER_LAST_POSITION, playerLastPosition, null, nbt);
        }
    }
}
