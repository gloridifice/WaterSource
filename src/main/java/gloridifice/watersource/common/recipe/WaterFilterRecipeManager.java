package gloridifice.watersource.common.recipe;

import gloridifice.watersource.common.item.StrainerBlockItem;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class WaterFilterRecipeManager {
    public static ArrayList<WaterFilterRecipe> recipes = new ArrayList();
    public static void add(StrainerBlockItem strainerItem, Fluid inputFluid, Fluid outputFluid){
        recipes.add(new WaterFilterRecipe(strainerItem,inputFluid, outputFluid));
    }

    public static WaterFilterRecipe getRecipeFromInput(ItemStack itemStack,Fluid inputFluid){
        for (WaterFilterRecipe recipe : recipes){
            if (itemStack.isItemEqual(new ItemStack(recipe.StrainerItem)) && inputFluid.isEquivalentTo(recipe.getInputFluid())){
                return recipe;
            }
        }
        return null;
    }
}
