package gloridifice.watersource.common.recipe;

import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class WaterLevelNBTRecipe extends WaterLevelItemRecipe {
    public WaterLevelNBTRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredient, int waterLevel, int waterSaturationLevel) {
        super(idIn, groupIn, ingredient, waterLevel, waterSaturationLevel);
    }

    @Override
    public IRecipeType<?> getType() {
        return NormalRecipeTypes.WATER_LEVEL_NBT_RECIPE;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.WATER_LEVEL_NBT_RECIPE_SERIALIZER.get();
    }

    @Override
    public boolean conform(ItemStack i) {
        for (ItemStack stack : ingredient.getMatchingStacks()) {
            if (stack.isItemEqual(i) && !stack.getTag().isEmpty() && !i.getTag().isEmpty() && stack.getTag().equals(i.getTag())) {
                return true;
            }
        }
        return false;
    }
}
