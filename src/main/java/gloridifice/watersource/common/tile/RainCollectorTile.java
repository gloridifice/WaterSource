package gloridifice.watersource.common.tile;

import gloridifice.watersource.registry.TileEntityTypesRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RainCollectorTile extends ModNormalTile implements ITickableTileEntity {
    LazyOptional<FluidTank> tank = LazyOptional.of(this::createFluidHandler);
    public int capacity;
    private int processTicks = 0;

    public RainCollectorTile(int capability) {
        super(TileEntityTypesRegistry.RAIN_COLLECTOR);
        this.capacity = capability;
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compound) {
        super.read(blockState, compound);
        tank.ifPresent(fluidTank -> {
            fluidTank.readFromNBT(compound.getCompound("tank"));
        });
        processTicks = ((IntNBT) compound.get("processTicks")).getInt();

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
    public CompoundNBT write(CompoundNBT compound) {
        tank.ifPresent(fluidTank -> {
            compound.put("tank", fluidTank.writeToNBT(new CompoundNBT()));
        });
        compound.put("processTicks", IntNBT.valueOf(processTicks));
        return super.write(compound);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (!this.removed) {
            if (CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.equals(cap)) {
                return tank.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    private FluidTank createFluidHandler() {
        return new FluidTank(capacity) {
            @Override
            protected void onContentsChanged() {
                RainCollectorTile.this.refresh();
                RainCollectorTile.this.markDirty();
                super.onContentsChanged();
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return !stack.getFluid().getAttributes().isLighterThanAir() && stack.getFluid().getAttributes().getTemperature() < 500;
            }
        };
    }

    public LazyOptional<FluidTank> getTank() {
        return tank;
    }

    @Override
    public void tick() {
        processTicks++;
        processTicks %= 2000;
        if (getWorld().isRaining()) {
            if (processTicks % 5 == 0 && world.isRainingAt(pos)) {
                tank.ifPresent(tank -> {
                    tank.fill(new FluidStack(Fluids.WATER, 1), IFluidHandler.FluidAction.EXECUTE);
                });
            }
        }
    }
}
