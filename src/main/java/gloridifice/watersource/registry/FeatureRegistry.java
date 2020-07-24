package gloridifice.watersource.registry;

import gloridifice.watersource.common.world.gen.feature.CoconutTreeFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class FeatureRegistry extends RegistryModule{

    public static final Feature<TreeFeatureConfig> COCONUT_TREE = new CoconutTreeFeature("coconut_tree", TreeFeatureConfig::func_227338_a_);
}
