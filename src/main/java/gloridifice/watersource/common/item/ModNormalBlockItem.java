package gloridifice.watersource.common.item;

import gloridifice.watersource.registry.CreativeModeTabRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;


public class ModNormalBlockItem extends BlockItem {
    public ModNormalBlockItem(Block blockIn) {
        super(blockIn, new Item.Properties().tab(CreativeModeTabRegistry.WATER_SOURCE_TAB));
    }

    public ModNormalBlockItem(Block blockIn, String name) {
        super(blockIn, new Item.Properties().tab(CreativeModeTabRegistry.WATER_SOURCE_TAB));
    }

    public ModNormalBlockItem(Block blockIn, Properties properties) {
        super(blockIn, properties);

    }
    public ModNormalBlockItem(Block blockIn, Properties properties, String name) {
        super(blockIn, properties);

    }
}
