package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;;

public class FeatureRegistry extends RegistryModule{

    public static final Feature<BaseTreeFeatureConfig> COCONUT_TREE;
    static {
        COCONUT_TREE = new TreeFeature(BaseTreeFeatureConfig.CODEC);
        COCONUT_TREE.setRegistryName(WaterSource.MODID, "coconut_tree");
    }
}
