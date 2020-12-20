package gloridifice.watersource.common.block;

import gloridifice.watersource.common.tile.StrainerTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class StrainerBlock extends Block {
    public static final VoxelShape STRAINER_SHAPE;
    static {
        STRAINER_SHAPE = Block.makeCuboidShape(4,0,4,12,4,12);
    }
    public StrainerBlock(String name,Properties properties) {
        super(properties);
        this.setRegistryName(name);
    }
    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);
        if (!player.isCreative()){
            player.addStat(Stats.BLOCK_MINED.get(this));
            player.addExhaustion(0.005F);
            ItemStack itemStack = this.getItem(worldIn,pos,state);
            if (worldIn.getTileEntity(pos) instanceof StrainerTile){
                StrainerTile tile = (StrainerTile) worldIn.getTileEntity(pos);
                itemStack.setTag(tile.getTag());
                spawnAsEntity(worldIn,pos,itemStack);
            }
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        //TODO
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return STRAINER_SHAPE;
    }

// TODO find and implement replacement
//    @Override
//    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
//        return false;
//    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new StrainerTile();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if (worldIn.getTileEntity(pos) instanceof StrainerTile){
            StrainerTile tile = (StrainerTile)worldIn.getTileEntity(pos);
            tile.setTag(stack.getTag());
        }
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        ItemStack itemStack = getItem(world,pos,state).copy();
        if (world.getTileEntity(pos) instanceof StrainerTile){
            StrainerTile tile = (StrainerTile)world.getTileEntity(pos);
            itemStack.setTag(tile.getTag());
        }
        return itemStack;
    }
}
