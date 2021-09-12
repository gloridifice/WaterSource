package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.world.gen.feature.CoconutTreeFeature;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class FeatureRegistry {
    private static Set<Feature<?>> FEATURES = new HashSet<>();
    public static final Feature<TreeConfiguration> COCONUT_TREE = register("coconut_tree", new CoconutTreeFeature(TreeConfiguration.CODEC));

    public static <V extends FeatureConfiguration> Feature<V> register(String name, Feature<V> feature) {
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
