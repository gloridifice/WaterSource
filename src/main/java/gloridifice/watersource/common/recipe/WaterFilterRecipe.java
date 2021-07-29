package gloridifice.watersource.common.recipe;

import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import static gloridifice.watersource.common.recipe.NormalRecipeTypes.WATER_FILTER_RECIPE;

public class WaterFilterRecipe implements IRecipe<IInventory> {
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
    public boolean matches(IInventory iInventory, World world) {
        return false;
    }

    @Override
    public ItemStack getCraftingResult(IInventory iInventory) {
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

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.WATER_FILTER_RECIPE_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return WATER_FILTER_RECIPE;
    }

    public static WaterFilterRecipe getRecipeFromInput(World world, ItemStack strainer, Fluid fluidInput) {
        List<WaterFilterRecipe> list = new ArrayList<>();
        list.addAll(world.getRecipeManager().getRecipesForType(WATER_FILTER_RECIPE));
        for (WaterFilterRecipe recipe : list) {
            if (recipe.inputFluid.isEquivalentTo(fluidInput)) {
                for (ItemStack itemStack : recipe.getStrainerIngredient().getMatchingStacks()) {
                    if (itemStack.isItemEqual(strainer)) return recipe;
                }
            }
        }
        return null;
    }
}
