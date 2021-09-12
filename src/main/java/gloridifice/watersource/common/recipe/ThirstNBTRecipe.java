package gloridifice.watersource.common.recipe;

import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import static gloridifice.watersource.common.recipe.NormalRecipeTypes.THIRST_NBT_RECIPE;

public class ThirstNBTRecipe extends ThirstItemRecipe {
    public ThirstNBTRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredient, int duration, int amplifier, int probability) {
        super(idIn, groupIn, ingredient, duration, amplifier, probability);
    }

    @Override
    public boolean conform(ItemStack i) {
        for (ItemStack stack : ingredient.getItems()) {
            if (stack.is(i.getItem()) && stack.getTag() != null && i.getTag() != null && stack.getTag().equals(i.getTag())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public RecipeType<?> getType() {
        return THIRST_NBT_RECIPE;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.THIRST_NBT_RECIPE_SERIALIZER.get();
    }
}
