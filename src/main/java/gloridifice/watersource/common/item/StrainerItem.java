package gloridifice.watersource.common.item;

import gloridifice.watersource.registry.GroupRegistry;
import net.minecraft.item.Item;

public class StrainerItem extends ModNormalItem {
    public StrainerItem(String name, int maxDamage) {
        super(name,new Properties().maxDamage(maxDamage).setNoRepair().group(GroupRegistry.waterSourceGroup));
    }
}
