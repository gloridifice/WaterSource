package gloridifice.watersource.common.block.entity;

import gloridifice.watersource.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;

public class WaterDispenserBlockEntity extends SimpleAnimationBlockEntity{
    public int capacity = 5000;
    LazyOptional<FluidTank> tank = LazyOptional.of(this::createFluidHandler);
    public WaterDispenserBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.WATER_DISPENSER.get(), pos, state);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        tank.ifPresent(fluidTank -> {
            fluidTank.readFromNBT(compoundTag.getCompound("tank"));
        });
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        tank.ifPresent(fluidTank -> {
            compoundTag.put("tank", fluidTank.writeToNBT(new CompoundTag()));
        });
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (!this.isRemoved()) {
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
                setChanged();
                clientSync();
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return !stack.getFluid().getAttributes().isLighterThanAir() && stack.getFluid().getAttributes().getTemperature() < 500;
            }
        };
    }
}
