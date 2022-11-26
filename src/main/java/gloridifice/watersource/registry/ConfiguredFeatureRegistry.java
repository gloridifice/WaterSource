package gloridifice.watersource.registry;


import gloridifice.watersource.common.world.gen.placement.PalmTreePlacement;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;
import java.util.OptionalInt;

public class ConfiguredFeatureRegistry {


    public static final Holder<? extends ConfiguredFeature<TreeConfiguration, ?>> COCONUT_TREE =
            register("palm_tree", FeatureRegistry.COCONUT_TREE,
                    ((new TreeConfiguration.TreeConfigurationBuilder(
                            BlockStateProvider.simple(BlockRegistry.PALM_TREE_LOG.get().defaultBlockState())
                            , new FancyTrunkPlacer(3, 11, 0)
                            , BlockStateProvider.simple(BlockRegistry.PALM_TREE_LEAF.get().defaultBlockState())
                            , new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4)
                            , new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines()
                            .build()));

    public static final Holder<PlacedFeature> COCONUT_TREE_PLACEMENT =
            register("palm_tree", COCONUT_TREE, List.of(PalmTreePlacement.of(1), BiomeFilter.biome()));

    public static Holder<PlacedFeature> register(String pName, Holder<? extends ConfiguredFeature<?, ?>> pFeature, List<PlacementModifier> pPlacements) {
        return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, pName, new PlacedFeature(Holder.hackyErase(pFeature), List.copyOf(pPlacements)));
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String pName, F pFeature, FC pConfig) {
        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, pName, new ConfiguredFeature<>(pFeature, pConfig));
    }
}
