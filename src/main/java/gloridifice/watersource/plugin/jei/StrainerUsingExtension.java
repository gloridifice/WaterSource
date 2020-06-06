package gloridifice.watersource.plugin.jei;

import gloridifice.watersource.common.recipe.type.PurifiedWaterBottleRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class StrainerUsingExtension implements ICraftingCategoryExtension {
    private final PurifiedWaterBottleRecipe recipe;

    StrainerUsingExtension(PurifiedWaterBottleRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void setIngredients(IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return recipe.getId();
    }

    @Override
    public void drawInfo(int recipeWidth, int recipeHeight, double mouseX, double mouseY) {

    }
}
