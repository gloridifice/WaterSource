package gloridifice.watersource.common.block;

import gloridifice.watersource.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class CoconutSaplingBlock extends SaplingBlock {
    public CoconutSaplingBlock(Tree treeIn, Properties properties) {
        super(treeIn, properties);
    }
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block block = state.getBlock();
        return block == Blocks.SAND || block == Blocks.RED_SAND;
    }
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return (double)worldIn.rand.nextFloat() < 0.45D;
    }

    @Override
    public void placeTree(ServerWorld world, BlockPos blockPos, BlockState block, Random rand) {
        super.placeTree(world, blockPos, block, rand);
        if (block.get(STAGE) == 0) {
            world.setBlockState(blockPos, block.cycle(STAGE), 4);
        }else world.setBlockState(blockPos, BlockRegistry.blockCoconutTreeLog.getDefaultState());
    }
}
