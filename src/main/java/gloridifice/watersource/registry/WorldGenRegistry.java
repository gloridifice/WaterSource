package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.world.gen.feature.CoconutTreeFeature;
import gloridifice.watersource.common.world.gen.placement.PalmTreePlacement;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.OptionalInt;

public class WorldGenRegistry {
    public static final DeferredRegister<Feature<?>> MOD_FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, WaterSource.MODID);
    //Features
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> COCONUT_TREE = MOD_FEATURES.register("palm_tree", () -> new CoconutTreeFeature(NoneFeatureConfiguration.CODEC));
    public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> CON_COCONUT_TREE;

    //Placement Features
    public static Holder<PlacedFeature> PALM_TREE_PLACEMENT;

    public static void RegistryConfiguredFeatures(){
        CON_COCONUT_TREE = FeatureUtils.register("palm_tree", COCONUT_TREE.get(), NoneFeatureConfiguration.INSTANCE);
        PALM_TREE_PLACEMENT = PlacementUtils.register("palm_tree", CON_COCONUT_TREE, CountOnEveryLayerPlacement.of(1), BiomeFilter.biome());
    }
}
