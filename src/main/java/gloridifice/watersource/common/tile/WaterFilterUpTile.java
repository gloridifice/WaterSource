package gloridifice.watersource.common.tile;


import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.item.StrainerBlockItem;
import gloridifice.watersource.common.recipe.WaterFilterRecipe;
import gloridifice.watersource.registry.ParticleRegistry;
import gloridifice.watersource.registry.TileEntityTypesRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
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
import java.util.Random;

public class WaterFilterUpTile extends ModNormalTile implements ITickableTileEntity {
    LazyOptional<FluidTank> upTank = LazyOptional.of(this::createFluidHandler);
    LazyOptional<ItemStackHandler> strainer = LazyOptional.of(this::createStrainerItemStackHandler);
    LazyOptional<ItemStackHandler> props = LazyOptional.of(this::createPropsItemStackHandler);

    double cacheTimeEnter, cacheTimeExit = 0;
    boolean previousIsIn = false;
    boolean isLeftAnimeEnd = true;
    private int heightAmount = 0;

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

    final int capacity;

    private int processTicks = 0;

    public WaterFilterUpTile(int capacity) {
        super(TileEntityTypesRegistry.WATER_FILTER_UP_TILE);
        this.capacity = capacity;
    }

    public LazyOptional<FluidTank> getUpTank() {
        return upTank;
    }

    public LazyOptional<ItemStackHandler> getStrainer() {
        return strainer;
    }

    public LazyOptional<ItemStackHandler> getProps() {
        return props;
    }

    public int getProcessTicks() {
        return processTicks;
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compound) {
        super.read(blockState, compound);
        upTank.ifPresent(fluidTank -> fluidTank.readFromNBT(compound.getCompound("upTank")));
        strainer.ifPresent(itemStackHandler -> itemStackHandler.deserializeNBT(compound.getCompound("strainer")));
        props.ifPresent(itemStackHandler -> itemStackHandler.deserializeNBT(compound.getCompound("props")));
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
        upTank.ifPresent(fluidTank -> compound.put("upTank", fluidTank.writeToNBT(new CompoundNBT())));
        strainer.ifPresent(itemStackHandler -> compound.put("strainer", ((INBTSerializable<CompoundNBT>) itemStackHandler).serializeNBT()));
        props.ifPresent(itemStackHandler -> compound.put("props", ((INBTSerializable<CompoundNBT>) itemStackHandler).serializeNBT()));
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

    boolean flag;

    @Override
    public void tick() {
        processTicks %= 8000;
        processTicks++;
        if (processTicks % 20 == 0) {
            TileEntity downTile = world.getTileEntity(pos.down());
            if (downTile instanceof WaterFilterDownTile) {
                strainer.ifPresent(strainerHandler -> props.ifPresent(propsHandler -> {
                    ItemStack strainerStack = strainerHandler.getStackInSlot(0);
                    int speed = propsHandler.getStackInSlot(0).getItem() == Items.HEART_OF_THE_SEA ? 25 : 10;
                    upTank.ifPresent(fluidTankUp -> {
                        WaterFilterRecipe recipe = WaterFilterRecipe.getRecipeFromInput(this.world, strainerStack, fluidTankUp.getFluid().getFluid());
                        ((WaterFilterDownTile) downTile).getDownTank().ifPresent(fluidTankDown -> {
                            if (recipe != null && fluidTankDown.getFluid().getAmount() < fluidTankDown.getCapacity()) {
                                flag = true;
                                if (fluidTankDown.isEmpty() || fluidTankDown.getFluid().getFluid().isEquivalentTo(recipe.getOutputFluid())) {
                                    fluidTankDown.fill(new FluidStack(recipe.getOutputFluid(), speed), IFluidHandler.FluidAction.EXECUTE);
                                    fluidTankUp.drain(speed, IFluidHandler.FluidAction.EXECUTE);
                                }
                                if (processTicks % 10000 / speed == 0) {
                                    //减少滤网耐久
                                    if (strainerStack.isDamageable()) {
                                        strainerHandler.setStackInSlot(0, StrainerBlockItem.damageItem(strainerStack, 1));
                                    }
                                }
                            }
                            else flag = false;
                        });
                    });
                }));
            }
        }
/*        if (flag){
            this.addParticles();
        }*/
    }

    private void addParticles() {
        BlockPos pos = this.getPos();
        Random random = new Random();

        this.getWorld().addOptionalParticle(ParticleRegistry.FLUID_WATER, true, 0.125D + (double) pos.getX() + random.nextDouble() * 0.75D, (double) pos.getY(), 0.125D + (double) pos.getZ() + random.nextDouble() * 0.75D, 0D, -0.2D, 0D);
    }

    private ItemStackHandler createStrainerItemStackHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                WaterFilterUpTile.this.refresh();
                WaterFilterUpTile.this.markDirty();
                super.onContentsChanged(slot);
            }

            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return ItemTags.getCollection().get(new ResourceLocation(WaterSource.MODID, "strainers")).contains(stack.getItem());
            }

            @Override
            public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
                if (stack.isEmpty()) return ItemStack.EMPTY;

                if (!isItemValid(slot, stack)) return stack;

                validateSlotIndex(slot);

                ItemStack existing = this.stacks.get(slot);

                if (simulate) {
                    if (!existing.isEmpty()) {
                        Block.spawnAsEntity(world, pos, existing);
                        extractItem(slot, existing.getCount() + 1, false);
                    }
                    this.stacks.set(slot, stack);
                }
                onContentsChanged(slot);
                return ItemStack.EMPTY;
            }
        };
    }

    private ItemStackHandler createPropsItemStackHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                WaterFilterUpTile.this.refresh();
                WaterFilterUpTile.this.markDirty();
                super.onContentsChanged(slot);
            }

            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return stack.getItem() == Items.HEART_OF_THE_SEA;
            }
        };
    }

    private FluidTank createFluidHandler() {
        return new FluidTank(capacity) {
            @Override
            protected void onContentsChanged() {
                WaterFilterUpTile.this.refresh();
                WaterFilterUpTile.this.markDirty();
                super.onContentsChanged();
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return !stack.getFluid().getAttributes().isLighterThanAir() && stack.getFluid().getAttributes().getTemperature() < 500;
            }
        };
    }
    public int getFluidAmount()
    {
        return this.upTank.orElse(null).getFluidAmount();
    }

    private void updateHeight()
    {
        if (this.getWorld() != null && this.getWorld().isRemote)
        {
            if (heightAmount > this.getFluidAmount())
            {
                heightAmount -= Math.max(1, (heightAmount - this.getFluidAmount()));
            }
            else if (heightAmount < this.getFluidAmount())
            {
                heightAmount += Math.max(1, (this.getFluidAmount() - heightAmount));
            }
        }
    }

    public float getHeight()
    {
        updateHeight();
        return 0.75F * this.heightAmount / capacity;
    }
}
