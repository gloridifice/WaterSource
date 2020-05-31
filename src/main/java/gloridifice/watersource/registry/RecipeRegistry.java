package gloridifice.watersource.registry;

import gloridifice.watersource.common.recipe.ThirstItemRecipeManager;
import gloridifice.watersource.common.recipe.ThirstNbtRecipe;
import gloridifice.watersource.common.recipe.WaterLevelRecipeManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;

public class RecipeRegistry {
    public static void init(){
        addWaterLevel();
        addThirstItem();
    }
    public static void addWaterLevel(){
        WaterLevelRecipeManager.add(new ItemStack(Items.APPLE),2,1);
        WaterLevelRecipeManager.add(new ItemStack(Items.SWEET_BERRIES),1,1);
        WaterLevelRecipeManager.add(new ItemStack(Items.POTION),4,2);
        WaterLevelRecipeManager.add(new ItemStack(ItemRegistry.itemPurifiedWaterBottle),6,4);
        WaterLevelRecipeManager.add(new ItemStack(Items.GOLDEN_APPLE),2,6);
        WaterLevelRecipeManager.add(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE),2,6);
        WaterLevelRecipeManager.add(new ItemStack(Items.CARROT),1,0);
        WaterLevelRecipeManager.add(new ItemStack(Items.MELON_SLICE),3,1);
        WaterLevelRecipeManager.add(new ItemStack(Items.RABBIT_STEW),3,1);
        WaterLevelRecipeManager.add(new ItemStack(Items.MUSHROOM_STEW),2,1);
        WaterLevelRecipeManager.add(new ItemStack(Items.MILK_BUCKET),1,0);
    }
    public static void addThirstItem(){
        ThirstItemRecipeManager.add(new ThirstNbtRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER),2000,0,75));
    }
}
