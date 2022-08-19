package gloridifice.watersource.common.block;

import gloridifice.watersource.common.block.entity.RainCollectorBlockEntity;
import gloridifice.watersource.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidUtil;

import javax.annotation.Nullable;

public class RainCollectorBlock extends BaseEntityBlock {
    public RainCollectorBlock(Properties properties) {
        super(properties);
    }
    boolean flag = false;
    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        RainCollectorBlockEntity tile = (RainCollectorBlockEntity) level.getBlockEntity(blockPos);
        tile.getTank().ifPresent(fluidTankUp -> {
            ItemStack heldItem = player.getItemInHand(hand);
            if (!heldItem.isEmpty()) {
                //液体交互
                flag = FluidUtil.interactWithFluidHandler(player, hand, fluidTankUp);
            }
        });
        return flag ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityRegistry.RAIN_COLLECTOR.get().create(pos, state);
    }
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level, BlockEntityType<T> blockEntityType, BlockEntityType<? extends RainCollectorBlockEntity> blockEntityType1) {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, blockEntityType1, RainCollectorBlockEntity::serveTick);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTicker(level, blockEntityType, BlockEntityRegistry.RAIN_COLLECTOR.get());
    }
}
