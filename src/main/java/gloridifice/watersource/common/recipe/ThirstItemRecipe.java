package gloridifice.watersource.common.recipe;

import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ThirstItemRecipe implements IThirstRecipe{
    protected final int duration,amplifier,probability;
    protected final ResourceLocation id;
    protected final String group;
    protected final Ingredient ingredient;
    public ThirstItemRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredient, int duration, int amplifier, int probability) {
        this.id = idIn;
        this.group = groupIn;
        this.duration = duration;
        this.amplifier = amplifier;
        this.ingredient = ingredient;
        this.probability = probability;
    }
    public int getAmplifier() {
        return amplifier;
    }

    public int getDuration() {
        return duration;
    }

    public int getProbability() {
        return probability;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }
    public boolean conform(ItemStack i){
        for (ItemStack stack : ingredient.getMatchingStacks()){
            if (stack.isItemEqual(i)){
                return true;
            }
        }
        return false;
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
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.THIRST_ITEM_RECIPE_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return NormalRecipeTypes.THIRST_ITEM_RECIPE;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    public static ThirstItemRecipe getRecipeFromItem(World world, ItemStack itemStack){
        List<ThirstItemRecipe> list = new ArrayList<>();
        list.addAll(world.getRecipeManager().getRecipesForType(NormalRecipeTypes.THIRST_ITEM_RECIPE));
        list.addAll(world.getRecipeManager().getRecipesForType(NormalRecipeTypes.THIRST_FLUID_RECIPE));
        for (ThirstItemRecipe recipe : list){
            if (recipe.conform(itemStack)){
                return recipe;
            }
        }
        return null;
    }
}
