package gloridifice.watersource.common;

import gloridifice.watersource.common.block.tree.CoconutTree;
import gloridifice.watersource.registry.FeatureRegistry;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class CommonProxy {
    public static void genFeature(){
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (biome == Biomes.BEACH){
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureRegistry.COCONUT_TREE.withConfiguration(CoconutTree.COCONUT_TREE_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(0, 0.5F, 1))));
            }
        }
    }
}
