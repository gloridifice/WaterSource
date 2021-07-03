package gloridifice.watersource.common.recipe;

import gloridifice.watersource.WaterSource;
import net.minecraft.item.crafting.IRecipeType;

public class NormalRecipeTypes {
    public static final IRecipeType<ThirstItemRecipe> THIRST_ITEM_RECIPE = IRecipeType.register(WaterSource.MODID + ":thirst_item");
    public static final IRecipeType<ThirstItemRecipe> THIRST_FLUID_RECIPE = IRecipeType.register(WaterSource.MODID + ":thirst_fluid");
    public static final IRecipeType<ThirstItemRecipe> THIRST_NBT_RECIPE = IRecipeType.register(WaterSource.MODID + ":thirst_nbt");
}
