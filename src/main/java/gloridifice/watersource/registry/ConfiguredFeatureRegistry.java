package gloridifice.watersource.registry;


import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;

import java.util.OptionalInt;

public class ConfiguredFeatureRegistry {

    /**
    public static final Holder<? extends ConfiguredFeature<TreeConfiguration, ?>> COCONUT_TREE =
            register("palm_tree", FeatureRegistry.COCONUT_TREE
                    .lace((new TreeConfiguration.TreeConfigurationBuilder(
                            BlockStateProvider.simple(BlockRegistry.PALM_TREE_LOG.defaultBlockState())
                            , new FancyTrunkPlacer(3, 11, 0)
                            , BlockStateProvider.simple(BlockRegistry.PALM_TREE_LEAF.defaultBlockState())
                            //, BlockStateProvider.simple(BlockRegistry.BLOCK_COCONUT_SAPLING.defaultBlockState())
                            , new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4)
                            , new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines()
                            .build()));

/*    public static ConfiguredFeature<TreeConfiguration, ?> COCONUT_TREE =
            register("palm_tree", FeatureRegistry.COCONUT_TREE
                    .configured(new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(BlockRegistry.BLOCK_COCONUT_TREE_LOG.defaultBlockState())
                            , new FancyTrunkPlacer(3, 11, 0)
                            , new SimpleStateProvider(BlockRegistry.BLOCK_COCONUT_SAPLING.defaultBlockState()), new SimpleStateProvider(BlockRegistry.BLOCK_COCONUT_SAPLING.defaultBlockState())
                            , new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4)
                            , new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines()
                            .build()));*/
    //.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0,0.6f,1))));
    //public static ConfiguredFeature<TreeConfiguration, ?> COCONUT_TREE_PLACEMENT = register("palm_tree", FeatureRegistry.COCONUT_TREE.configured(new TreeConfiguration.Builder(new SimpleStateProvider(BlockRegistry.BLOCK_COCONUT_TREE_LOG.defaultBlockState()), new SimpleStateProvider(BlockRegistry.BLOCK_COCONUT_TREE_LEAF.defaultBlockState()), new SimpleStateProvider(BlockRegistry.BLOCK_COCONUT_SAPLING.defaultBlockState())).build()));

    public static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, name, configuredFeature);
    }
}
