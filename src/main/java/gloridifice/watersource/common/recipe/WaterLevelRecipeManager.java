package gloridifice.watersource.common.recipe;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Objects;

public class WaterLevelRecipeManager {
    public static ArrayList<WaterLevelItemRecipe> recipes = new ArrayList();
    public static void add(ItemStack itemStack,int waterLevel,int waterSaturationLevel){
        recipes.add(new WaterLevelItemRecipe(itemStack, waterLevel, waterSaturationLevel));
    }

    public static void add(WaterLevelItemRecipe recipe){
        recipes.add(recipe);
    }
    public static boolean hasWaterLevel(ItemStack itemStack){
        for (WaterLevelItemRecipe r : recipes){
            if (r.conform(itemStack)){
                return true;
            }
        }
        return false;
    }
    public static WaterLevelItemRecipe getRecipeFromItemStack(ItemStack itemStack){
        for (WaterLevelItemRecipe r : recipes){
            if (r.conform(itemStack)){
                return r;
            }
        }
        return null;
    }
}
