package gloridifice.watersource.common.recipes;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class WaterLevelRecipeManager {
    public static ArrayList<WaterLevelRecipe> recipes = new ArrayList();
    public static void add(ItemStack itemStack,int waterLevel,int waterSaturationLevel){
        recipes.add(new WaterLevelRecipe(itemStack, waterLevel, waterSaturationLevel));
    }
    public static void add(WaterLevelRecipe recipe){
        recipes.add(recipe);
    }
    public static boolean hasWaterLevel(ItemStack itemStack){
        for (WaterLevelRecipe r : recipes){
            if (r.getItemStack().equals(itemStack)){
                return true;
            }
        }
        return false;
    }
    public static WaterLevelRecipe getRecipeFromItemStack(ItemStack itemStack){
        for (WaterLevelRecipe r : recipes){
            if (r.getItemStack().isItemEqual(itemStack)){
                return r;
            }
        }
        return null;
    }
}
