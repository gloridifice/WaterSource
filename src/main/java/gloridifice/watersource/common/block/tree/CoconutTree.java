package gloridifice.watersource.common.block.tree;

import gloridifice.watersource.common.world.gen.config.CoconutTreeFeatureConfig;
import gloridifice.watersource.registry.ConfiguredFeatureRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;


import javax.annotation.Nullable;
import java.util.Random;

public class CoconutTree extends Tree {
    @Nullable
    protected ConfiguredFeature<CoconutTreeFeatureConfig, ?> getCoconutTreeFeatureConfig (){
        return ConfiguredFeatureRegistry.COCONUT_TREE;
    }

    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
        return null;
    }

    @Override
    public boolean attemptGrowTree(ServerWorld worldIn, ChunkGenerator chunkGeneratorIn, BlockPos blockPosIn, BlockState blockStateIn, Random randomIn) {
        ConfiguredFeature<CoconutTreeFeatureConfig, ?> configuredfeature = this.getCoconutTreeFeatureConfig();
        if (configuredfeature == null) {
            return false;
        } else {
            //判定是否可以生长
            for (int i = 1; i <= 7; i++){
                for (int b = -1; b <= 1; b++){
                    if (!worldIn.isAirBlock(blockPosIn.up(i).east(b).north(b))) return false;
                }
            }
            for (int a = -3; a <= 3; a++){
                for (int b = 5; b<= 8; b++){
                    if (!worldIn.isAirBlock(blockPosIn.up(b).east(a).north(a))) return false;
                }
            }
            //
            worldIn.setBlockState(blockPosIn, Blocks.AIR.getDefaultState(), 4);
            if (configuredfeature.generate(worldIn, chunkGeneratorIn, randomIn, blockPosIn)) {
                return true;
            } else {
                worldIn.setBlockState(blockPosIn, blockStateIn, 4);
                return false;
            }
        }
    }
}
