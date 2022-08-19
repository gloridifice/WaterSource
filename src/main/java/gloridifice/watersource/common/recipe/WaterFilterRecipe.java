package gloridifice.watersource.common.recipe;

import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

import java.util.ArrayList;
import java.util.List;

import static gloridifice.watersource.registry.RecipeTypesRegistry.WATER_FILTER_RECIPE;

public class WaterFilterRecipe implements Recipe<Inventory> {
    protected final Ingredient strainerIngredient;
    protected final Fluid inputFluid, outputFluid;
    protected final ResourceLocation id;
    protected final String group;

    public WaterFilterRecipe(ResourceLocation idIn, String groupIn, Ingredient strainerIngredient, Fluid inputFluid, Fluid outputFluid) {
        this.id = idIn;
        this.group = groupIn;
        this.strainerIngredient = strainerIngredient;
        this.inputFluid = inputFluid;
        this.outputFluid = outputFluid;
    }

    public Ingredient getStrainerIngredient() {
        return strainerIngredient;
    }

    public Fluid getInputFluid() {
        return inputFluid;
    }

    public Fluid getOutputFluid() {
        return outputFluid;
    }

    @Override
    public boolean matches(Inventory iInventory, Level world) {
        return false;
    }

    @Override
    public ItemStack assemble(Inventory p_44001_) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return null;
    }

    
/*
    @Override
    public ItemStack getCraftingResult(Inventory iInventory) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int i, int i1) {
        return false;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }
*/

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.WATER_FILTER_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return WATER_FILTER_RECIPE.get();
    }

    public static WaterFilterRecipe getRecipeFromInput(Level level, ItemStack strainer, Fluid fluidInput) {
        List<WaterFilterRecipe> list = new ArrayList<>();
        list.addAll(level.getRecipeManager().getAllRecipesFor(WATER_FILTER_RECIPE.get()));
        for (WaterFilterRecipe recipe : list) {
            if (recipe.inputFluid == fluidInput) {
                for (ItemStack itemStack : recipe.getStrainerIngredient().getItems()) {
                    if (itemStack.is(strainer.getItem())) return recipe;
                }
            }
        }
        return null;
    }
}
