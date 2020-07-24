package gloridifice.watersource.common.tile;

import gloridifice.watersource.registry.TileEntityTypesRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WaterFilterDownTile extends TileEntity {
    LazyOptional<FluidTank> downTank = LazyOptional.of(this::createFluidHandler);

    public WaterFilterDownTile() {
        super(TileEntityTypesRegistry.WATER_FILTER_DOWN_TILE);
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
        this.read(pkt.getNbtCompound());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbtTag = new CompoundNBT();
        this.write(nbtTag);
        return new SUpdateTileEntityPacket(getPos(), 1, nbtTag);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        downTank.ifPresent(fluidTank -> {
            fluidTank.readFromNBT(compound.getCompound("downTank"));
        });
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        downTank.ifPresent(fluidTank -> {
            compound.put("downTank", fluidTank.writeToNBT(new CompoundNBT()));
        });
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
        return new FluidTank(1000) {
            @Override
            protected void onContentsChanged() {
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
