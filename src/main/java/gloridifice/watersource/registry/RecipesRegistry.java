package gloridifice.watersource.registry;

import gloridifice.watersource.common.recipes.WaterLevelRecipeManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class RecipesRegistry {
    public static void init(){
        addWaterLevel();
    }
    public static void addWaterLevel(){
        WaterLevelRecipeManager.add(new ItemStack(Items.APPLE),3,4);
    }
}
