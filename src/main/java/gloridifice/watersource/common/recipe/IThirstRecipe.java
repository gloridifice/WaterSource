package gloridifice.watersource.common.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public interface IThirstRecipe extends IRecipe<IInventory> {
    boolean conform(ItemStack stack);
    int getProbability();
    int getDuration();
    int getAmplifier();
    ItemStack getItemStack();
}
