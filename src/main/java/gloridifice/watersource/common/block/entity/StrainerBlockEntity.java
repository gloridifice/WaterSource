package gloridifice.watersource.common.block.entity;

import gloridifice.watersource.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.stream.Stream;

public class StrainerBlockEntity extends ModNormalBlockEntity {
    LazyOptional<ItemStackHandler> strainerHandler = LazyOptional.of(this::createStrainerItemStackHandler);

    public StrainerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.STRAINER_TILE.get(), pos, state);

    }

    public void setStrainer(ItemStack strainer) {
        strainerHandler.ifPresent(data -> {
            data.setStackInSlot(0, strainer);
        });
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        this.strainerHandler.ifPresent(stackHandler -> {
            CompoundTag compound = ((INBTSerializable<CompoundTag>) stackHandler).serializeNBT();
            tag.put("strainer", compound);
        });
        super.saveAdditional(tag);
    }


    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        CompoundTag strainerTag = tag.getCompound("strainer");
        this.strainerHandler.ifPresent(stackHandler -> ((INBTSerializable<CompoundTag>) stackHandler).deserializeNBT(strainerTag));
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return strainerHandler.cast();
        }
        return super.getCapability(cap, side);
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
                return true;
            }

            @Override
            protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
                return 1;
            }
        };
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        strainerHandler.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        strainerHandler = net.minecraftforge.common.util.LazyOptional.of(this::createStrainerItemStackHandler);
    }

}
