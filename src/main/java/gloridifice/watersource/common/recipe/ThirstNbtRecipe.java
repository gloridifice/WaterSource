package gloridifice.watersource.common.recipe;

import net.minecraft.item.ItemStack;

public class ThirstNbtRecipe extends ThirstItemRecipe implements IThirstRecipe{
    public ThirstNbtRecipe(ItemStack itemStack, int duration, int amplifier, int probability) {
        super(itemStack, duration, amplifier, probability);
    }
    @Override
    public boolean conform(ItemStack i){
        return itemStack.isItemEqual(i) && ItemStack.areItemStackTagsEqual(i,itemStack);
    }
}
