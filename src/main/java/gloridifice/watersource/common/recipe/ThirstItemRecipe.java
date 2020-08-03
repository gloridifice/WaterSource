package gloridifice.watersource.common.recipe;

import net.minecraft.item.ItemStack;

public class ThirstItemRecipe implements IThirstRecipe{
    protected final int duration,amplifier;
    protected final ItemStack itemStack;
    protected final int probability;
    public ThirstItemRecipe(ItemStack itemStack, int duration, int amplifier, int probability) {
        this.duration = duration;
        this.amplifier = amplifier;
        this.itemStack = itemStack;
        this.probability = probability;
    }
    public int getAmplifier() {
        return amplifier;
    }
    public int getDuration() {
        return duration;
    }

    public int getProbability() {
        return probability;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
    public boolean conform(ItemStack i){
        return itemStack.isItemEqual(i);
    }
}
