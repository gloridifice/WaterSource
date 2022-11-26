package gloridifice.watersource.common.event;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.world.gen.placement.PalmTreePlacement;
import gloridifice.watersource.registry.ConfiguredFeatureRegistry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement;
import net.minecraft.world.level.levelgen.placement.NoiseBasedCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WaterSource.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BiomeLoadEventHandler {
    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {
        if (event.getClimate().temperature >= 0.15f && event.getCategory() == Biome.BiomeCategory.BEACH) {
            //event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ConfiguredFeatureRegistry.COCONUT_TREE.placed(PalmTreePlacement.of(1), BiomeFilter.biome()));
        }
    }
}
