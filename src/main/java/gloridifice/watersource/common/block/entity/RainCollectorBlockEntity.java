package gloridifice.watersource.common.block.entity;

import gloridifice.watersource.registry.BlockEntityRegistry;
import gloridifice.watersource.registry.FluidRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;

public class RainCollectorBlockEntity extends ModNormalBlockEntity  {
    LazyOptional<FluidTank> tank = LazyOptional.of(this::createFluidHandler);
    public int capacity;
    private int processTicks = 0;

    public RainCollectorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.RAIN_COLLECTOR.get(), pos, state);
        this.capacity = 5000;
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        tank.ifPresent(fluidTank -> {
            fluidTank.readFromNBT(compoundTag.getCompound("tank"));
        });
        processTicks = ((IntTag) compoundTag.get("processTicks")).getAsInt();
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        tank.ifPresent(fluidTank -> {
            compoundTag.put("tank", fluidTank.writeToNBT(new CompoundTag()));
        });
        compoundTag.put("processTicks", IntTag.valueOf(processTicks));
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

    public LazyOptional<FluidTank> getTank() {
        return tank;
    }


    public int getProcessTicks() {
        return processTicks;
    }

    public void setProcessTicks(int processTicks) {
        this.processTicks = processTicks;
    }

    public static void serveTick(Level level, BlockPos blockPos, BlockState blockState, RainCollectorBlockEntity blockEntity) {
        blockEntity.setProcessTicks(blockEntity.getProcessTicks());
        blockEntity.setProcessTicks(blockEntity.getProcessTicks() % 2000);
        if (level.isRaining()) {
            if (blockEntity.processTicks % 5 == 0 && hasBlockAboveSelf(level, blockPos)) {
                blockEntity.getTank().ifPresent(tank -> {
                    tank.fill(new FluidStack(FluidRegistry.PURIFIED_WATER.get(), 1), IFluidHandler.FluidAction.EXECUTE);
                });
            }
        }
    }
    public static boolean hasBlockAboveSelf(Level level, BlockPos pos){
        boolean bool = true;
        for (int i = 1; i <= level.getHeight(); i++){
            bool = bool && level.getBlockState(pos.above(i)).isAir();
        }
        return bool;
    }
}
