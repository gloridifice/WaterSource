package gloridifice.watersource.common.recipe;

import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class WaterLevelNBTRecipe extends WaterLevelItemRecipe {
    public WaterLevelNBTRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredient, int waterLevel, int waterSaturationLevel) {
        super(idIn, groupIn, ingredient, waterLevel, waterSaturationLevel);
    }

    @Override
    public RecipeType<?> getType() {
        return NormalRecipeTypes.WATER_LEVEL_NBT_RECIPE;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.WATER_LEVEL_NBT_RECIPE_SERIALIZER.get();
    }

    @Override
    public boolean conform(ItemStack i) {
        for (ItemStack stack : ingredient.getItems()) {
            if (stack.is(i.getItem()) && !stack.getTag().isEmpty() && !i.getTag().isEmpty() && stack.getTag().equals(i.getTag())) {
                return true;
            }
        }
        return false;
    }
}
