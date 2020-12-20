package gloridifice.watersource.common.block.tree;

import gloridifice.watersource.registry.BlockRegistry;
import gloridifice.watersource.registry.FeatureRegistry;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

import javax.annotation.Nullable;
import java.util.Random;

public class CoconutTree extends Tree {
    public static final BaseTreeFeatureConfig COCONUT_TREE_CONFIG = 
            new BaseTreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(BlockRegistry.BLOCK_COCONUT_TREE_LOG.getDefaultState()),
                    new SimpleBlockStateProvider(BlockRegistry.BLOCK_COCONUT_TREE_LEAF.getDefaultState()),
                    new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(1), 3),
                    new StraightTrunkPlacer(7, 4, 2), // Base height, random height A, random height B
                    new TwoLayerFeature(2, 0, 2)).setIgnoreVines().build();

    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
        return FeatureRegistry.COCONUT_TREE.withConfiguration(COCONUT_TREE_CONFIG);
    }
}
