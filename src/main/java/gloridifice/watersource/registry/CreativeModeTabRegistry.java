package gloridifice.watersource.registry;

import gloridifice.watersource.helper.FluidHelper;
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
            return FluidHelper.fillContainer(new ItemStack(ItemRegistry.FLUID_BOTTLE.get()), FluidRegistry.PURIFIED_WATER.get());
        }
    }
}
