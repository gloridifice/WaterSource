package gloridifice.watersource.common.recipes;

import net.minecraft.item.ItemStack;

public class WaterLevelRecipe {
    private final int waterLevel, waterSaturationLevel;
    private final ItemStack itemStack;
    public WaterLevelRecipe(ItemStack itemStack, int waterLevel, int waterSaturationLevel) {
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
}
