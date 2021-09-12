package gloridifice.watersource.common.block.entity;

import gloridifice.watersource.registry.BlockEntityTypesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RainCollectorBlockEntity extends ModNormalBlockEntity  {
    LazyOptional<FluidTank> tank = LazyOptional.of(this::createFluidHandler);
    public int capacity;
    private int processTicks = 0;

    public RainCollectorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypesRegistry.RAIN_COLLECTOR, pos, state);
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
    public CompoundTag save(CompoundTag compoundTag) {
        tank.ifPresent(fluidTank -> {
            compoundTag.put("tank", fluidTank.writeToNBT(new CompoundTag()));
        });
        compoundTag.put("processTicks", IntTag.valueOf(processTicks));
        return super.save(compoundTag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.save(new CompoundTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag nbtTag = new CompoundTag();
        this.save(nbtTag);
        return new ClientboundBlockEntityDataPacket(getBlockPos(), 1, nbtTag);
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
                RainCollectorBlockEntity.this.refresh();
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
            if (blockEntity.processTicks % 5 == 0 && level.isRainingAt(blockPos)) {
                blockEntity.getTank().ifPresent(tank -> {
                    tank.fill(new FluidStack(Fluids.WATER, 1), IFluidHandler.FluidAction.EXECUTE);
                });
            }
        }
    }
}
