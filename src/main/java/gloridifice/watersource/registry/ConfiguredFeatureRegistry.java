package gloridifice.watersource.registry;

import gloridifice.watersource.common.world.gen.config.CoconutTreeFeatureConfig;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.*;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

public class ConfiguredFeatureRegistry {
    public static ConfiguredFeature<CoconutTreeFeatureConfig, ?> COCONUT_TREE = (ConfiguredFeature<CoconutTreeFeatureConfig, ?>) register("coconut_tree",FeatureRegistry.COCONUT_TREE
            .withConfiguration(new CoconutTreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.BLOCK_COCONUT_TREE_LOG.getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.BLOCK_COCONUT_TREE_LEAF.getDefaultState()),new SimpleBlockStateProvider(BlockRegistry.BLOCK_COCONUT_SAPLING.getDefaultState())).build()));
            //.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0,0.6f,1))));
            public static ConfiguredFeature<CoconutTreeFeatureConfig, ?> COCONUT_TREE_PLACEMENT = (ConfiguredFeature<CoconutTreeFeatureConfig, ?>) register("coconut_tree",FeatureRegistry.COCONUT_TREE
                    .withConfiguration(new CoconutTreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.BLOCK_COCONUT_TREE_LOG.getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.BLOCK_COCONUT_TREE_LEAF.getDefaultState()),new SimpleBlockStateProvider(BlockRegistry.BLOCK_COCONUT_SAPLING.getDefaultState())).build())
    .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0,0.6f,1))));

    public static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, name, configuredFeature);
    }
}
