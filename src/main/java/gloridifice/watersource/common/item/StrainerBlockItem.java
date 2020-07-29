package gloridifice.watersource.common.item;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.registry.BlockRegistry;
import gloridifice.watersource.registry.GroupRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class StrainerBlockItem extends ModNormalBlockItem {
    public StrainerBlockItem(Block block, int maxDamage) {
        super(block, new Properties().maxDamage(maxDamage).setNoRepair().group(GroupRegistry.waterSourceGroup));
    }
}
