package gloridifice.watersource.common.recipe;

import gloridifice.watersource.WaterSource;
import net.minecraft.world.item.crafting.RecipeType;

public class NormalRecipeTypes {
    public static final RecipeType<ThirstItemRecipe> THIRST_ITEM_RECIPE = RecipeType.register(WaterSource.MODID + ":thirst_item");
    public static final RecipeType<ThirstItemRecipe> THIRST_FLUID_RECIPE = RecipeType.register(WaterSource.MODID + ":thirst_fluid");
    public static final RecipeType<ThirstItemRecipe> THIRST_NBT_RECIPE = RecipeType.register(WaterSource.MODID + ":thirst_nbt");

    public static final RecipeType<WaterLevelItemRecipe> WATER_LEVEL_ITEM_RECIPE = RecipeType.register(WaterSource.MODID + ":water_level_item");
    public static final RecipeType<WaterLevelItemRecipe> WATER_LEVEL_FLUID_RECIPE = RecipeType.register(WaterSource.MODID + ":water_level_fluid");
    public static final RecipeType<WaterLevelItemRecipe> WATER_LEVEL_NBT_RECIPE = RecipeType.register(WaterSource.MODID + ":water_level_nbt");

    public static final RecipeType<WaterFilterRecipe> WATER_FILTER_RECIPE = RecipeType.register(WaterSource.MODID + ":water_filter");

}
