package gloridifice.watersource.common.tile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.server.ServerWorld;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModNormalTile extends TileEntity {
    public ModNormalTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public void refresh() {
        if (this.hasWorld() && !this.getWorld().isRemote() && !this.removed) {
            BlockState state = this.world.getBlockState(pos);
            this.getWorld().markAndNotifyBlock(pos, this.getWorld().getChunkAt(pos), state, state, 11, 2);

            SUpdateTileEntityPacket packet = this.getUpdatePacket();
            Stream<ServerPlayerEntity> playerEntity = ((ServerWorld) this.world).getChunkProvider().chunkManager.getTrackingPlayers(new ChunkPos(this.pos.getX() >> 4, this.pos.getZ() >> 4), false);
            for (ServerPlayerEntity player : playerEntity.collect(Collectors.toList())) {
                player.connection.sendPacket(packet);
            }
        }
    }
}
