package gloridifice.watersource.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModNormalBlockEntity extends BlockEntity {
    public ModNormalBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(blockEntityType, pos, state);
    }

    public void refresh() {
        if (this.hasLevel() && !this.getLevel().isClientSide() && !this.remove) {
            BlockState state = this.level.getBlockState(getBlockPos());
            this.getLevel().markAndNotifyBlock(getBlockPos(), this.getLevel().getChunkAt(getBlockPos()), state, state, 11, 2);

            ClientboundBlockEntityDataPacket packet = this.getUpdatePacket();
            Stream<ServerPlayer> playerEntity = ((ServerLevel) this.level).getChunkSource().chunkMap.getPlayers(new ChunkPos(this.getBlockPos().getX() >> 4, this.getBlockPos().getZ() >> 4), false);
            for (ServerPlayer player : playerEntity.collect(Collectors.toList())) {
                player.connection.send(packet);
            }
        }
    }
}
