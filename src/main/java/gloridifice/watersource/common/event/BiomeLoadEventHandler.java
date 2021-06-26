package gloridifice.watersource.common.event;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.registry.ConfiguredFeatureRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid= WaterSource.MODID, bus= Mod.EventBusSubscriber.Bus.FORGE)
public class BiomeLoadEventHandler {
    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.BEACH) {
            BiomeGenerationSettingsBuilder settings = event.getGeneration();
            settings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatureRegistry.COCONUT_TREE);
        }
    }
}
