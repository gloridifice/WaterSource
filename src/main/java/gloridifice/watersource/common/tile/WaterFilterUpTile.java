package gloridifice.watersource.common.tile;

import gloridifice.watersource.client.render.tile.WaterFilterDownTER;
import gloridifice.watersource.common.recipe.WaterFilterRecipe;
import gloridifice.watersource.common.recipe.WaterFilterRecipeManager;
import gloridifice.watersource.registry.TileEntityTypesRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WaterFilterUpTile extends TileEntity implements ITickableTileEntity {
    LazyOptional<FluidTank> upTank = LazyOptional.of(this::createFluidHandler);
    LazyOptional<ItemStackHandler> strainer = LazyOptional.of(this::createItemStackHandler);

    private int processTicks = 0;

    public WaterFilterUpTile() {
        super(TileEntityTypesRegistry.WATER_FILTER_UP_TILE);
    }

    public LazyOptional<FluidTank> getUpTank() {
        return upTank;
    }

    public LazyOptional<ItemStackHandler> getStrainer() {
        return strainer;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        upTank.ifPresent(fluidTank -> {
            fluidTank.readFromNBT(compound.getCompound("upTank"));
        });
        strainer.ifPresent(itemStackHandler -> {
            itemStackHandler.deserializeNBT(compound.getCompound("strainer"));
        });
        processTicks = ((IntNBT) compound.get("processTicks")).getInt();

    }
    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.write(new CompoundNBT());
    }
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
    {
        this.read(pkt.getNbtCompound());
    }
    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        CompoundNBT nbtTag = new CompoundNBT();
        this.write(nbtTag);
        return new SUpdateTileEntityPacket(getPos(), 1, nbtTag);
    }


    @Override
    public CompoundNBT write(CompoundNBT compound) {
        upTank.ifPresent(fluidTank -> {
            compound.put("upTank", fluidTank.writeToNBT(new CompoundNBT()));
        });
        strainer.ifPresent(itemStackHandler -> {
            compound.put("strainer", ((INBTSerializable<CompoundNBT>) itemStackHandler).serializeNBT());
        });
        compound.put("processTicks", IntNBT.valueOf(processTicks));
        return super.write(compound);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (!this.removed) {
            if (CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.equals(cap)) {
                return upTank.cast();
            }
            if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(cap)) {
                return strainer.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void tick() {
        processTicks %= 8000;
        processTicks++;
        if (processTicks % 50 == 0) {
            WaterFilterDownTile downTile = (WaterFilterDownTile) world.getTileEntity(pos.down());
            strainer.ifPresent(itemStackHandler -> {
                        upTank.ifPresent(fluidTankUp -> {
                            WaterFilterRecipe recipe = WaterFilterRecipeManager.getRecipeFromInput(itemStackHandler.getStackInSlot(0), fluidTankUp.getFluid().getFluid());
                            if (recipe != null) {
                                downTile.getDownTank().ifPresent(fluidTankDown -> {
                                    if (fluidTankDown.isEmpty() || fluidTankDown.getFluid().getFluid().isEquivalentTo(recipe.getOutputFluid())) {
                                        fluidTankDown.fill(new FluidStack(recipe.getOutputFluid(), 5), IFluidHandler.FluidAction.EXECUTE);
                                        fluidTankUp.drain(5, IFluidHandler.FluidAction.EXECUTE);
                                    }
                                });
                            }

                        });
                        if (processTicks % 1000 == 0) {
                            if (itemStackHandler.getStackInSlot(0).isDamageable()) {
                                itemStackHandler.getStackInSlot(0).setDamage(itemStackHandler.getStackInSlot(0).getDamage() + 1);
                            }
                        }
                    }
            );
        }
    }

    private ItemStackHandler createItemStackHandler() {
        return new ItemStackHandler(1);
    }

    private FluidTank createFluidHandler() {
        return new FluidTank(1000) {
            @Override
            protected void onContentsChanged() {
                WaterFilterUpTile.this.markDirty();
                super.onContentsChanged();
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return !stack.getFluid().getAttributes().isLighterThanAir() && stack.getFluid().getAttributes().getTemperature() < 500;
            }
        };
    }
}
