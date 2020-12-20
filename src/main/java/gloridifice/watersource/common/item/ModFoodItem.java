package gloridifice.watersource.common.item;

import net.minecraft.item.Food;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;


public class ModFoodItem extends ModNormalItem {
    public ModFoodItem(String name, Food food) {
        super(name, new Properties().food(food));
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        super.fillItemGroup(group, items);
        if (group == ItemGroup.FOOD){
            items.add(new ItemStack(this));
        }
    }
}
