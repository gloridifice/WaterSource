package gloridifice.watersource.common.item;

import gloridifice.watersource.registry.CreativeModeTabRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;


public class ModNormalBlockItem extends BlockItem {
    public ModNormalBlockItem(Block blockIn) {
        super(blockIn, new Item.Properties().tab(CreativeModeTabRegistry.WATER_SOURCE_TAB));
        try {
            this.setRegistryName(blockIn.getRegistryName());
        }catch (java.lang.NullPointerException e){
            System.out.println("Block" + blockIn.toString());
        }
    }

    public ModNormalBlockItem(Block blockIn, String name) {
        super(blockIn, new Item.Properties().tab(CreativeModeTabRegistry.WATER_SOURCE_TAB));
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
