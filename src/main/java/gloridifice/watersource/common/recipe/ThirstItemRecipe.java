package gloridifice.watersource.common.recipe;

import net.minecraft.item.ItemStack;

public class ThirstItemRecipe {
    protected static int duration,amplifier;
    protected static ItemStack itemStack;
    protected static int probability;
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

    public static int getProbability() {
        return probability;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
    public boolean conform(ItemStack i){
        return itemStack.isItemEqual(i);
    }
}
