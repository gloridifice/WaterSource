package gloridifice.watersource.common;

import gloridifice.watersource.registry.CriteriaTriggerRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;

public class CommonProxy {
    public static void init() {
        registerFireInfo();
        CriteriaTriggerRegistry.WATER_LEVEL_RESTORED_TRIGGER.getId();
        CriteriaTriggerRegistry.GUIDE_BOOK_TRIGGER.getId();
    }

    public static void registerFireInfo() {
        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        /*
        fireblock.setFireInfo(BlockRegistry.BLOCK_COCONUT_TREE_STAIRS, 5, 20);
        fireblock.setFireInfo(BlockRegistry.BLOCK_COCONUT_TREE_PLANKS, 5, 20);
        fireblock.setFireInfo(BlockRegistry.BLOCK_COCONUT_TREE_FENCE, 5, 20);
        fireblock.setFireInfo(BlockRegistry.BLOCK_COCONUT_TREE_FENCE_GATE, 5, 20);
        fireblock.setFireInfo(BlockRegistry.BLOCK_COCONUT_TREE_SLAB, 5, 20);
        fireblock.setFireInfo(BlockRegistry.BLOCK_COCONUT_TREE_LOG, 5, 5);
        fireblock.setFireInfo(BlockRegistry.BLOCK_STRIPPED_COCONUT_TREE_LOG, 5, 5);
        fireblock.setFireInfo(BlockRegistry.BLOCK_COCONUT_TREE_LEAF, 30, 60);*/
    }
}
