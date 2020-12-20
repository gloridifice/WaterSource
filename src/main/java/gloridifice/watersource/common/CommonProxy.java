package gloridifice.watersource.common;

import gloridifice.watersource.common.block.tree.CoconutTree;
import gloridifice.watersource.registry.BlockRegistry;
import gloridifice.watersource.registry.FeatureRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class CommonProxy {
    public static void init(){
        genFeature();
        registerFireInfo();
    }
    public static void genFeature(){
        ForgeRegistries.BIOMES.getValue(Biomes.BEACH.getLocation()).addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureRegistry.COCONUT_TREE.withConfiguration(CoconutTree.COCONUT_TREE_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(0, 0.5F, 1))));
    }

    public static void registerFireInfo(){
        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        fireblock.setFireInfo(BlockRegistry.BLOCK_COCONUT_TREE_STAIRS, 5, 20);
        fireblock.setFireInfo(BlockRegistry.BLOCK_COCONUT_TREE_PLANKS, 5, 20);
        fireblock.setFireInfo(BlockRegistry.BLOCK_COCONUT_TREE_FENCE, 5, 20);
        fireblock.setFireInfo(BlockRegistry.BLOCK_COCONUT_TREE_FENCE_GATE, 5, 20);
        fireblock.setFireInfo(BlockRegistry.BLOCK_COCONUT_TREE_SLAB, 5, 20);
        fireblock.setFireInfo(BlockRegistry.BLOCK_COCONUT_TREE_LOG, 5, 5);
        fireblock.setFireInfo(BlockRegistry.BLOCK_STRIPPED_COCONUT_TREE_LOG, 5, 5);
        fireblock.setFireInfo(BlockRegistry.BLOCK_COCONUT_TREE_LEAF, 30, 60);
    }
}
