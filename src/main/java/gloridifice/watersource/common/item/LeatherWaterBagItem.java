package gloridifice.watersource.common.item;

import gloridifice.watersource.registry.ItemRegistry;
import net.minecraft.world.item.ItemStack;

public class LeatherWaterBagItem extends DurableDrinkContainerItem{
    public LeatherWaterBagItem(String name, Properties properties, int capacity) {
        super(name, properties, capacity);
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return new ItemStack(ItemRegistry.LEATHER_WATER_BAG);
    }
}
