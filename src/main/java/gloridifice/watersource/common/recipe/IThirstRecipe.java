package gloridifice.watersource.common.recipe;

import net.minecraft.item.ItemStack;

public interface IThirstRecipe {
    boolean conform(ItemStack stack);
    int getProbability();
    int getDuration();
    int getAmplifier();
    ItemStack getItemStack();
}
