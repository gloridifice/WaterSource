package gloridifice.watersource.common.block;

import gloridifice.watersource.common.block.entity.StrainerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class StrainerBlock extends BaseEntityBlock {
    public static final VoxelShape STRAINER_SHAPE;
    static {
        STRAINER_SHAPE = Block.box(4, 0, 4, 12, 4, 12);
    }

    public StrainerBlock(String name, Properties properties) {
        super(properties);
        this.setRegistryName(name);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        //if (!player.isCreative()) {
/*          player.addStat(Stats.BLOCK_MINED.get(this));
            player.addExhaustion(0.005F);*/
            if (level.getBlockEntity(pos) instanceof StrainerBlockEntity && !level.isClientSide()) {
                StrainerBlockEntity tile = (StrainerBlockEntity) level.getBlockEntity(pos);
                popResource(level, pos, tile.getStrainer());
            }
        //}
        super.playerWillDestroy(level, pos, state, player);
    }


    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return STRAINER_SHAPE;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity livingEntity, ItemStack stack) {
        BlockEntity tile = level.getBlockEntity(pos);
        if (tile instanceof StrainerBlockEntity && !level.isClientSide()) {
            ((StrainerBlockEntity) tile).setStrainer(stack);
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public ItemStack getPickBlock(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        ItemStack itemStack = getCloneItemStack(world, pos, state);
        if (world.getBlockEntity(pos) instanceof StrainerBlockEntity) {
            BlockEntity tile = world.getBlockEntity(pos);
            itemStack = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null).getStackInSlot(0);
        }
        return itemStack;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StrainerBlockEntity(pos, state);
    }
}
