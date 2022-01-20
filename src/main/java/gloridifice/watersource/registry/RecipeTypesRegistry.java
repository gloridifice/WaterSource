package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.recipe.ThirstRecipe;
import gloridifice.watersource.common.recipe.WaterFilterRecipe;
import gloridifice.watersource.common.recipe.WaterLevelAndEffectRecipe;
import gloridifice.watersource.common.recipe.type.StrainerFilterRecipe;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeTypesRegistry {
    public static final RecipeType<ThirstRecipe> THIRST_RECIPE = RecipeType.register(WaterSource.MODID + ":thirst");
    public static final RecipeType<WaterLevelAndEffectRecipe> WATER_LEVEL_RECIPE = RecipeType.register(WaterSource.MODID + ":water_level");

    public static final RecipeType<StrainerFilterRecipe> STRAINER_FILTER_RECIPE_RECIPE = RecipeType.register((WaterSource.MODID + ":strainer_filter"));
    public static final RecipeType<WaterFilterRecipe> WATER_FILTER_RECIPE = RecipeType.register(WaterSource.MODID + ":water_filter");

}
