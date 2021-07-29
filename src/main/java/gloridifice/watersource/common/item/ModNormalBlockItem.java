package gloridifice.watersource.common.item;

import gloridifice.watersource.registry.GroupRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class ModNormalBlockItem extends BlockItem {
    public ModNormalBlockItem(Block blockIn) {
        super(blockIn, new Item.Properties().group(GroupRegistry.waterSourceGroup));
        this.setRegistryName(blockIn.getRegistryName());
    }

    public ModNormalBlockItem(Block blockIn, String name) {
        super(blockIn, new Item.Properties().group(GroupRegistry.waterSourceGroup));
        this.setRegistryName(name);
    }

    public ModNormalBlockItem(Block blockIn, Properties properties) {
        super(blockIn, properties);
        this.setRegistryName(blockIn.getRegistryName());
    }

    public ModNormalBlockItem(Block blockIn, Properties properties, String name) {
        super(blockIn, properties);
        this.setRegistryName(name);
    }
}
