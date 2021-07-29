package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.world.gen.config.CoconutTreeFeatureConfig;
import gloridifice.watersource.common.world.gen.feature.CoconutTreeFeature;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class FeatureRegistry {
    private static Set<Feature<?>> FEATURES = new HashSet<>();
    public static final Feature<CoconutTreeFeatureConfig> COCONUT_TREE = register("coconut_tree", new CoconutTreeFeature(CoconutTreeFeatureConfig.CODEC));

    public static <V extends IFeatureConfig> Feature<V> register(String name, Feature<V> feature) {
        ResourceLocation id = new ResourceLocation(WaterSource.MODID, name);
        feature.setRegistryName(id);
        FEATURES.add(feature);
        return feature;
    }

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Feature<?>> event) {
        event.getRegistry().registerAll(FEATURES.toArray(new Feature[0]));
    }
}
