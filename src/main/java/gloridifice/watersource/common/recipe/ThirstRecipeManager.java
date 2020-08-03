package gloridifice.watersource.common.recipe;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ThirstRecipeManager {
    public static ArrayList<IThirstRecipe> recipes = new ArrayList<>();
    public static void add(ItemStack itemStack, int durationIn, int amplifierIn, int probability){
        recipes.add(new ThirstItemRecipe(itemStack,durationIn,amplifierIn,probability));
    }
    public static void add(IThirstRecipe recipe){
        recipes.add(recipe);
    }
    public static IThirstRecipe getRecipeFromItemStick(ItemStack itemStack){
        for (IThirstRecipe r : recipes){
            if (r.conform(itemStack)) {
                return r;
            }
        }
        return null;
    }
}
