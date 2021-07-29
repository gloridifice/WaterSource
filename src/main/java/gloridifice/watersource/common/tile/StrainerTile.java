package gloridifice.watersource.common.tile;

import gloridifice.watersource.registry.TileEntityTypesRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;

import javax.annotation.Nullable;

public class StrainerTile extends ModNormalTile {
    CompoundNBT tag = new CompoundNBT();

    public StrainerTile() {
        super(TileEntityTypesRegistry.STRAINER_TILE);
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compound) {
        super.read(blockState, compound);
        tag = compound.getCompound("tag");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("tag", tag);
        return super.write(compound);
    }

    public void setTag(CompoundNBT tag) {
        this.tag = tag;
    }

    public CompoundNBT getTag() {
        return tag;
    }

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
}
