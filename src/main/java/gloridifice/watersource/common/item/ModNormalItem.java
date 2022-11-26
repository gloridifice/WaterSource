package gloridifice.watersource.common.item;

import gloridifice.watersource.registry.CreativeModeTabRegistry;
import net.minecraft.world.item.Item;

public class ModNormalItem extends Item {
    public ModNormalItem(Properties properties) {
        super(properties.tab(CreativeModeTabRegistry.WATER_SOURCE_TAB));
    }

    public ModNormalItem() {
        super(new Properties().tab(CreativeModeTabRegistry.WATER_SOURCE_TAB));
    }
}
