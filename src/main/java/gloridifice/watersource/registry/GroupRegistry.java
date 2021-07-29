package gloridifice.watersource.registry;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class GroupRegistry {
    public static ItemGroup waterSourceGroup = new WaterSourceGroup();

    public static class WaterSourceGroup extends ItemGroup {
        public WaterSourceGroup() {
            super("watersource_group");
        }

        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemRegistry.itemPurifiedWaterBottle);
        }
    }
}
