package gloridifice.watersource.common.recipes;

import net.minecraft.item.ItemStack;

public class ThirstNbtRecipe extends ThirstItemRecipe{
    public ThirstNbtRecipe(ItemStack itemStack, int duration, int amplifier, int probability) {
        super(itemStack, duration, amplifier, probability);
    }
    public static boolean isItemStackEqual(ItemStack i){
        return itemStack.isItemEqual(i) && i.getTag() == itemStack.getTag();
    }
}
