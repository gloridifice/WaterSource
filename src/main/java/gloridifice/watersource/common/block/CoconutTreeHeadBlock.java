package gloridifice.watersource.common.block;



import gloridifice.watersource.WaterSource;
import gloridifice.watersource.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;


public class CoconutTreeHeadBlock extends Block implements IGrowable {
    public CoconutTreeHeadBlock(String name, Properties properties) {
        super(properties);
        this.setRegistryName(name);
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        if (BlockTags.getCollection().get(new ResourceLocation(WaterSource.MODID,"coconut_tree_logs")).contains(worldIn.getBlockState(pos.down()).getBlock())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        int a = rand.nextInt(4);
        if (worldIn.isAirBlock(pos.west()) && a == 0) {
            worldIn.setBlockState(pos.west(), BlockRegistry.BLOCK_NATURAL_COCONUT.getDefaultState().with(NaturalCoconutBlock.AGE, 0).with(NaturalCoconutBlock.HORIZONTAL_FACING, Direction.EAST));
        }
        if (worldIn.isAirBlock(pos.east()) && a == 1) {
            worldIn.setBlockState(pos.east(), BlockRegistry.BLOCK_NATURAL_COCONUT.getDefaultState().with(NaturalCoconutBlock.AGE, 0).with(NaturalCoconutBlock.HORIZONTAL_FACING, Direction.WEST));
        }
        if (worldIn.isAirBlock(pos.south()) && a == 2) {
            worldIn.setBlockState(pos.south(), BlockRegistry.BLOCK_NATURAL_COCONUT.getDefaultState().with(NaturalCoconutBlock.AGE, 0).with(NaturalCoconutBlock.HORIZONTAL_FACING, Direction.NORTH));
        }
        if (worldIn.isAirBlock(pos.north()) && a == 3) {
            worldIn.setBlockState(pos.north(), BlockRegistry.BLOCK_NATURAL_COCONUT.getDefaultState().with(NaturalCoconutBlock.AGE, 0).with(NaturalCoconutBlock.HORIZONTAL_FACING, Direction.SOUTH));
        }
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (canGrow(worldIn, pos, state, false)) {
            if (!worldIn.isAreaLoaded(pos, 1))
                return; // Forge: prevent loading unloaded chunks when checking neighbor's light
            if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(26) == 0)) {
                grow(worldIn,rand,pos,state);
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
            }
        }
    }

}
