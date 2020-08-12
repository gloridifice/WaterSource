package gloridifice.watersource.common.block.tree;

import com.google.common.collect.Sets;
import gloridifice.watersource.common.world.gen.feature.CoconutTreeFeature;
import gloridifice.watersource.registry.BlockRegistry;
import gloridifice.watersource.registry.FeatureRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;


import javax.annotation.Nullable;
import java.util.Random;
import java.util.Set;

public class CoconutTree extends Tree {
    public static final TreeFeatureConfig COCONUT_TREE_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.BLOCK_COCONUT_TREE_LOG.getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.BLOCK_COCONUT_TREE_LEAF.getDefaultState()), new BlobFoliagePlacer(2, 0))).baseHeight(4).heightRandA(2).foliageHeight(3).ignoreVines().setSapling((net.minecraftforge.common.IPlantable) BlockRegistry.BLOCK_COCONUT_SAPLING).build();

    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
        return FeatureRegistry.COCONUT_TREE.withConfiguration(COCONUT_TREE_CONFIG);
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<?> chunkGeneratorIn, BlockPos blockPosIn, BlockState blockStateIn, Random randomIn) {
        ConfiguredFeature<TreeFeatureConfig, ?> configuredfeature = this.getTreeFeature(randomIn, true);
        if (configuredfeature == null) {
            return false;
        } else {
            Set<BlockPos> set = Sets.newHashSet();
            Set<BlockPos> set1 = Sets.newHashSet();
            MutableBoundingBox mutableboundingbox = MutableBoundingBox.getNewBoundingBox();
            worldIn.setBlockState(blockPosIn, Blocks.AIR.getDefaultState(), 4);
            (configuredfeature.config).forcePlacement();
            if (((CoconutTreeFeature)configuredfeature.feature).place(worldIn,randomIn,blockPosIn,set,set1,mutableboundingbox,COCONUT_TREE_CONFIG)) {
                return true;
            } else {
                worldIn.setBlockState(blockPosIn, blockStateIn, 4);
                return false;
            }
        }
    }
}
