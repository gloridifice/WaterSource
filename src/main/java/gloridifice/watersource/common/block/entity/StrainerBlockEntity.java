package gloridifice.watersource.common.block.entity;

import gloridifice.watersource.registry.BlockEntityTypesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StrainerBlockEntity extends ModNormalBlockEntity {
    LazyOptional<ItemStackHandler> strainer = LazyOptional.of(this::createStrainerItemStackHandler);

    public StrainerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypesRegistry.STRAINER_TILE, pos, state);
    }
    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        strainer.ifPresent(data -> data.deserializeNBT(compound.getCompound("strainer")));
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        strainer.ifPresent(data -> compound.put("strainer", ((INBTSerializable<CompoundTag>) data).serializeNBT()));
        return super.save(compound);
    }

    public void setStrainer(ItemStack stack) {
        this.strainer.ifPresent(date -> {
            date.setStackInSlot(0, stack);
        });
    }

    public ItemStack getStrainer() {
        return strainer.orElse(null).getStackInSlot(0);
    }

    public CompoundTag getUpdateTag() {
        return this.save(new CompoundTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (!this.isRemoved() && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return strainer.cast();
        }
        return super.getCapability(cap, side);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag nbtTag = new CompoundTag();
        this.save(nbtTag);
        return new ClientboundBlockEntityDataPacket(getBlockPos(), 1, nbtTag);
    }
    private ItemStackHandler createStrainerItemStackHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                StrainerBlockEntity.this.refresh();
                super.onContentsChanged(slot);
            }

            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return true;
            }
            @Override
            protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
                return 1;
            }
        };
    }
}
