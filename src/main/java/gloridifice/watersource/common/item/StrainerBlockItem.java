package gloridifice.watersource.common.item;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.registry.GroupRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class StrainerBlockItem extends ModNormalItem {
    public StrainerBlockItem(String name, int maxDamage) {
        super(name,new Properties().maxDamage(maxDamage).setNoRepair().group(GroupRegistry.waterSourceGroup));
    }
    public StrainerBlockItem(String name, int maxDamage, ResourceLocation strainerTexture) {
        super(name,new Properties().maxDamage(maxDamage).setNoRepair().group(GroupRegistry.waterSourceGroup));

    }
}
