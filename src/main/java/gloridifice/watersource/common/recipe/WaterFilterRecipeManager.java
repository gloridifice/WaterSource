package gloridifice.watersource.common.recipe;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.item.StrainerBlockItem;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;


import java.util.ArrayList;

public class WaterFilterRecipeManager {
    public static ArrayList<WaterFilterRecipe> recipes = new ArrayList();
    public static void add(StrainerBlockItem strainerItem, Fluid inputFluid, Fluid outputFluid){
        recipes.add(new WaterFilterRecipe(strainerItem,inputFluid, outputFluid));
    }

    public static WaterFilterRecipe getRecipeFromInput(ItemStack strainer,Fluid inputFluid){
        for (WaterFilterRecipe recipe : recipes){
            if (ItemTags.getCollection().get(new ResourceLocation(WaterSource.MODID,"strainers")).contains(strainer.getItem()) && inputFluid.isEquivalentTo(recipe.getInputFluid())){
                return recipe;
            }
        }
        return null;
    }
}
