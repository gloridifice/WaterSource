package gloridifice.watersource.common.item;

import net.minecraft.core.NonNullList;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModFoodItem extends ModNormalItem {
    public ModFoodItem(FoodProperties food) {
        super(new Properties().food(food));
    }
    public ModFoodItem(Item.Properties properties, FoodProperties food) {
        super(properties.food(food));
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        super.fillItemCategory(group, items);
        if (group == CreativeModeTab.TAB_FOOD) {
            items.add(new ItemStack(this));
        }
    }
}
