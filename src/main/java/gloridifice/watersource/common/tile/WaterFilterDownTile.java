package gloridifice.watersource.common.tile;

import gloridifice.watersource.registry.TileEntityTypesRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WaterFilterDownTile extends ModNormalTile {
    LazyOptional<FluidTank> downTank = LazyOptional.of(this::createFluidHandler);

    double cacheTimeEnter, cacheTimeExit = 0;
    boolean previousIsIn = false;
    boolean isLeftAnimeEnd = true;

    final int capacity;

    public WaterFilterDownTile(int capacity) {
        super(TileEntityTypesRegistry.WATER_FILTER_DOWN_TILE);
        this.capacity = capacity;
    }

    public double getCacheTimeEnter() {
        return cacheTimeEnter;
    }

    public void setCacheTimeEnter(double cacheTimeEnter) {
        this.cacheTimeEnter = cacheTimeEnter;
    }

    public double getCacheTimeExit() {
        return cacheTimeExit;
    }

    public void setCacheTimeExit(double cacheTimeExit) {
        this.cacheTimeExit = cacheTimeExit;
    }

    public boolean isPreviousIsIn() {
        return previousIsIn;
    }

    public void setPreviousIsIn(boolean previousIsIn) {
        this.previousIsIn = previousIsIn;
    }

    public boolean isLeftAnimeEnd() {
        return isLeftAnimeEnd;
    }

    public void setLeftAnimeEnd(boolean leftAnimeEnd) {
        isLeftAnimeEnd = leftAnimeEnd;
    }

    public LazyOptional<FluidTank> getDownTank() {
        return downTank;
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(world.getBlockState(pkt.getPos()), pkt.getNbtCompound());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbtTag = new CompoundNBT();
        this.write(nbtTag);
        return new SUpdateTileEntityPacket(getPos(), 1, nbtTag);
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compound) {
        super.read(blockState, compound);
        downTank.ifPresent(fluidTank -> fluidTank.readFromNBT(compound.getCompound("downTank")));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        downTank.ifPresent(fluidTank -> compound.put("downTank", fluidTank.writeToNBT(new CompoundNBT())));
        return super.write(compound);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (!this.removed) {
            if (CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.equals(cap)) {
                return downTank.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    private FluidTank createFluidHandler() {
        return new FluidTank(capacity) {
            @Override
            protected void onContentsChanged() {
                WaterFilterDownTile.this.refresh();
                WaterFilterDownTile.this.markDirty();
                super.onContentsChanged();
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return !stack.getFluid().getAttributes().isLighterThanAir() && stack.getFluid().getAttributes().getTemperature() < 500;
            }
        };
    }
}
