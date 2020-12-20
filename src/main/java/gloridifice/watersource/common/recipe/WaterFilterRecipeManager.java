package gloridifice.watersource.common.recipe;

import gloridifice.watersource.common.data.tag.ModTags;
import gloridifice.watersource.common.item.StrainerBlockItem;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class WaterFilterRecipeManager {
    public static ArrayList<WaterFilterRecipe> recipes = new ArrayList<>();
    public static void add(StrainerBlockItem strainerItem, Fluid inputFluid, Fluid outputFluid){
        recipes.add(new WaterFilterRecipe(strainerItem,inputFluid, outputFluid));
    }

    public static WaterFilterRecipe getRecipeFromInput(ItemStack strainer,Fluid inputFluid){
        for (WaterFilterRecipe recipe : recipes){
            if (ModTags.Item.STRAINERS.contains(strainer.getItem()) && inputFluid.isEquivalentTo(recipe.getInputFluid())){
                return recipe;
            }
        }
        return null;
    }
}
