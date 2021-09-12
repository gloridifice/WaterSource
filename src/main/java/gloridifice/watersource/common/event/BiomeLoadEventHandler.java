package gloridifice.watersource.common.event;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.registry.ConfiguredFeatureRegistry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WaterSource.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BiomeLoadEventHandler {
    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.BiomeCategory.BEACH) {
            event.getGeneration().getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION).add(() ->ConfiguredFeatureRegistry.COCONUT_TREE);
        }
    }
}
