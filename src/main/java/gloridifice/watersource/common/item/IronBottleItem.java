package gloridifice.watersource.common.item;

import gloridifice.watersource.registry.ItemRegistry;
import net.minecraft.world.item.ItemStack;

public class IronBottleItem extends DurableDrinkContainerItem{
    public IronBottleItem(String name, Properties properties, int capacity) {
        super(name, properties, capacity);
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return new ItemStack(ItemRegistry.IRON_BOTTLE);
    }
}
