package gloridifice.watersource.common.recipe;

import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import static gloridifice.watersource.common.recipe.NormalRecipeTypes.THIRST_NBT_RECIPE;

public class ThirstNBTRecipe extends ThirstItemRecipe{
    public ThirstNBTRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredient, int duration, int amplifier, int probability) {
        super(idIn, groupIn, ingredient, duration, amplifier, probability);
    }

    @Override
    public boolean conform(ItemStack i) {
        for (ItemStack stack : ingredient.getMatchingStacks()){
            if (stack.isItemEqual(i) && stack.getTag() != null && i.getTag() != null && stack.getTag().equals(i.getTag())){
                return true;
            }
        }
        return false;
    }

    @Override
    public IRecipeType<?> getType() {
        return THIRST_NBT_RECIPE;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.THIRST_NBT_RECIPE_SERIALIZER.get();
    }
}
