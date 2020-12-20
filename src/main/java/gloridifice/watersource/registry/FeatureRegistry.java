package gloridifice.watersource.registry;

import gloridifice.watersource.common.world.gen.feature.CoconutTreeFeature;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;;

public class FeatureRegistry extends RegistryModule{

    public static final Feature<BaseTreeFeatureConfig> COCONUT_TREE = new CoconutTreeFeature("coconut_tree", BaseTreeFeatureConfig::func_236685_a_);
}
