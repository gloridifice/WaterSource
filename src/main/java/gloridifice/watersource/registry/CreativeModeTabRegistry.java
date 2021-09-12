package gloridifice.watersource.registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CreativeModeTabRegistry {
    public static CreativeModeTab WATER_SOURCE_TAB = new WaterSourceGroup();

    public static class WaterSourceGroup extends CreativeModeTab {
        public WaterSourceGroup() {
            super("watersource_tab");
        }
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemRegistry.itemPurifiedWaterBottle);
        }
    }
}
