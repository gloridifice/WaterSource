package gloridifice.watersource.common.recipe;

import gloridifice.watersource.WaterSource;
import net.minecraft.item.crafting.IRecipeType;

public class NormalRecipeTypes {
    public static final IRecipeType<ThirstItemRecipe> THIRST_ITEM_RECIPE = IRecipeType.register(WaterSource.MODID + ":thirst_item");
    public static final IRecipeType<ThirstItemRecipe> THIRST_FLUID_RECIPE = IRecipeType.register(WaterSource.MODID + ":thirst_fluid");
    public static final IRecipeType<ThirstItemRecipe> THIRST_NBT_RECIPE = IRecipeType.register(WaterSource.MODID + ":thirst_nbt");

    public static final IRecipeType<WaterLevelItemRecipe> WATER_LEVEL_ITEM_RECIPE = IRecipeType.register(WaterSource.MODID + ":water_level_item");
    public static final IRecipeType<WaterLevelItemRecipe> WATER_LEVEL_FLUID_RECIPE = IRecipeType.register(WaterSource.MODID + ":water_level_fluid");
    public static final IRecipeType<WaterLevelItemRecipe> WATER_LEVEL_NBT_RECIPE = IRecipeType.register(WaterSource.MODID + ":water_level_nbt");

    public static final IRecipeType<WaterFilterRecipe> WATER_FILTER_RECIPE = IRecipeType.register(WaterSource.MODID + ":water_filter");

}
