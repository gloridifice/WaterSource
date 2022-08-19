package gloridifice.watersource.common.block.entity;


import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.item.StrainerBlockItem;
import gloridifice.watersource.common.recipe.WaterFilterRecipe;
import gloridifice.watersource.data.ModItemTags;
import gloridifice.watersource.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

public class WaterFilterUpBlockEntity extends SimpleAnimationBlockEntity {
    LazyOptional<FluidTank> upTank = LazyOptional.of(this::createFluidHandler);
    LazyOptional<ItemStackHandler> strainer = LazyOptional.of(this::createStrainerItemStackHandler);
    LazyOptional<ItemStackHandler> props = LazyOptional.of(this::createPropsItemStackHandler);
    protected int processTicks = 0;

    final int capacity;

    public WaterFilterUpBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.WATER_FILTER_UP_TILE.get(), pos, state);
        this.capacity = 3000;
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


    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        upTank.ifPresent(fluidTank -> fluidTank.readFromNBT(compound.getCompound("upTank")));
        strainer.ifPresent(itemStackHandler -> itemStackHandler.deserializeNBT(compound.getCompound("strainer")));
        props.ifPresent(itemStackHandler -> itemStackHandler.deserializeNBT(compound.getCompound("props")));
        this.setProcessTicks(((IntTag) compound.get("processTicks")).getAsInt());
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        upTank.ifPresent(fluidTank -> compound.put("upTank", fluidTank.writeToNBT(new CompoundTag())));
        strainer.ifPresent(itemStackHandler -> compound.put("strainer", ((INBTSerializable<CompoundTag>) itemStackHandler).serializeNBT()));
        props.ifPresent(itemStackHandler -> compound.put("props", ((INBTSerializable<CompoundTag>) itemStackHandler).serializeNBT()));
        compound.put("processTicks", IntTag.valueOf(getProcessTicks()));
        super.saveAdditional(compound);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (!this.isRemoved()) {
            if (CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY == cap) {
                return upTank.cast();
            }
            if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == cap) {
                return strainer.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    public int getProcessTicks() {
        return this.processTicks;
    }

    public void setProcessTicks(int processTicks) {
        this.processTicks = processTicks;
    }

    public static void serveTick(Level level, BlockPos blockPos, BlockState blockState, WaterFilterUpBlockEntity blockEntity) {
        LazyOptional<ItemStackHandler> strainer = blockEntity.getStrainer();
        LazyOptional<ItemStackHandler> props = blockEntity.getProps();
        LazyOptional<FluidTank> upTank = blockEntity.getUpTank();
        blockEntity.setProcessTicks(blockEntity.getProcessTicks() % 10000);
        blockEntity.setProcessTicks(blockEntity.getProcessTicks() + 1);
        if (blockEntity.getProcessTicks() % 20 == 0) {
            BlockEntity downTile = level.getBlockEntity(blockPos.below());
            if (downTile instanceof WaterFilterDownBlockEntity) {
                strainer.ifPresent(strainerHandler -> props.ifPresent(propsHandler -> {
                    ItemStack strainerStack = strainerHandler.getStackInSlot(0);
                    int speed = propsHandler.getStackInSlot(0).getItem() == Items.HEART_OF_THE_SEA ? 25 : 10;
                    upTank.ifPresent(fluidTankUp -> {
                        WaterFilterRecipe recipe = WaterFilterRecipe.getRecipeFromInput(level, strainerStack, fluidTankUp.getFluid().getFluid());
                        ((WaterFilterDownBlockEntity) downTile).getDownTank().ifPresent(fluidTankDown -> {
                            if (recipe != null && fluidTankDown.getFluid().getAmount() < fluidTankDown.getCapacity()) {
                                if (fluidTankDown.isEmpty() || fluidTankDown.getFluid().getFluid().isSame(recipe.getOutputFluid())) {
                                    fluidTankDown.fill(new FluidStack(recipe.getOutputFluid(), speed), IFluidHandler.FluidAction.EXECUTE);
                                    fluidTankUp.drain(speed, IFluidHandler.FluidAction.EXECUTE);
                                }
                                if (blockEntity.getProcessTicks() % 500 == 0) {
                                    //减少滤网耐久
                                    if (strainerStack.isDamageableItem()) {
                                        strainerHandler.setStackInSlot(0, StrainerBlockItem.hurt(strainerStack, 1));
                                    }
                                }
                            }
                        });
                    });
                }));
            }
        }
    }

    private ItemStackHandler createStrainerItemStackHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                clientSync();
            }

            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return ForgeRegistries.ITEMS.tags().getTag(ModItemTags.STRAINERS).contains(stack.getItem());
            }

            @Override
            public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
                if (stack.isEmpty()) return ItemStack.EMPTY;

                if (!isItemValid(slot, stack)) return stack;

                validateSlotIndex(slot);

                ItemStack existing = this.stacks.get(slot);

                if (simulate) {
                    if (!existing.isEmpty()) {
                        Block.popResource(level, getBlockPos(), existing);
                        extractItem(slot, existing.getCount() + 1, false);
                    }
                    this.stacks.set(slot, stack);
                }
                onContentsChanged(slot);
                return ItemStack.EMPTY;
            }

            @Override
            protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
                return 1;
            }
        };
    }

    private ItemStackHandler createPropsItemStackHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 2);
                super.onContentsChanged(slot);
            }

            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return stack.getItem() == Items.HEART_OF_THE_SEA;
            }

            @Override
            protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
                return 1;
            }
        };
    }

    private FluidTank createFluidHandler() {
        return new FluidTank(capacity) {
            @Override
            protected void onContentsChanged() {
                setChanged();
                clientSync();
                super.onContentsChanged();
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return !stack.getFluid().getAttributes().isLighterThanAir() && stack.getFluid().getAttributes().getTemperature() < 500;
            }
        };
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        strainer.invalidate();
        props.invalidate();
        upTank.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        strainer = LazyOptional.of(this::createStrainerItemStackHandler);
        props = LazyOptional.of(this::createPropsItemStackHandler);
        upTank = LazyOptional.of(this::createFluidHandler);
    }
}
