package gloridifice.watersource.common.item;

import net.minecraft.core.NonNullList;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModFoodItem extends ModNormalItem {
    public ModFoodItem(String name, FoodProperties food) {
        super(name, new Properties().food(food));
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        super.fillItemCategory(group, items);
        if (group == CreativeModeTab.TAB_FOOD) {
            items.add(new ItemStack(this));
        }
    }
}
