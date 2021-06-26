package gloridifice.watersource.common.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class WaterLevelItemRecipe{
    protected final int waterLevel, waterSaturationLevel;
    protected final ItemStack itemStack;
    public WaterLevelItemRecipe(ItemStack itemStack, int waterLevel, int waterSaturationLevel) {
        this.waterLevel = waterLevel;
        this.waterSaturationLevel = waterSaturationLevel;
        this.itemStack = itemStack;
    }

    public int getWaterSaturationLevel() {
        return waterSaturationLevel;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public boolean conform(ItemStack stack){
        return  this.itemStack.isItemEqual(stack);
    }


}
