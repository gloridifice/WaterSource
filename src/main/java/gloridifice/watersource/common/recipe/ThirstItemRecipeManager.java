package gloridifice.watersource.common.recipe;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ThirstItemRecipeManager {
    public static ArrayList<ThirstItemRecipe> recipes = new ArrayList<>();
    public static void add(ItemStack itemStack, int durationIn, int amplifierIn, int probability){
        recipes.add(new ThirstItemRecipe(itemStack,durationIn,amplifierIn,probability));
    }
    public static void add(ThirstItemRecipe recipe){
        recipes.add(recipe);
    }
    public static ThirstItemRecipe getRecipeFromItemStick(ItemStack itemStack){
        for (ThirstItemRecipe r : recipes){
            if (r.conform(itemStack)) return r;
        }
        return null;
    }
}
