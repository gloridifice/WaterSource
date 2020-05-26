package gloridifice.watersource.registry;

import gloridifice.watersource.common.recipes.ThirstItemRecipeManager;
import gloridifice.watersource.common.recipes.ThirstNbtRecipe;
import gloridifice.watersource.common.recipes.WaterLevelRecipeManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;

public class RecipesRegistry {
    public static void init(){
        addWaterLevel();
        addThirstItem();
    }
    public static void addWaterLevel(){
        WaterLevelRecipeManager.add(new ItemStack(Items.APPLE),3,4);
    }
    public static void addThirstItem(){
        ThirstItemRecipeManager.add(new ThirstNbtRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER),2000,0,75));
    }
}
