package gloridifice.watersource.common.item;

import gloridifice.watersource.registry.GroupRegistry;
import net.minecraft.item.Item;

public class ModNormalItem extends Item {
    public ModNormalItem(String name,Properties properties) {
        super(properties.group(GroupRegistry.waterSourceGroup));
        this.setRegistryName(name);
    }
    public ModNormalItem(String name) {
        super(new Properties().group(GroupRegistry.waterSourceGroup));
        this.setRegistryName(name);
    }
}
