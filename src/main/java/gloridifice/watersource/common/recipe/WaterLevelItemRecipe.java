package gloridifice.watersource.common.recipe;

import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import static gloridifice.watersource.common.recipe.NormalRecipeTypes.WATER_LEVEL_ITEM_RECIPE;

public class WaterLevelItemRecipe implements IRecipe<IInventory> {
    protected final int waterLevel, waterSaturationLevel;
    protected final ResourceLocation id;
    protected final String group;
    protected final Ingredient ingredient;

    public WaterLevelItemRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredient, int waterLevel, int waterSaturationLevel) {
        this.id = idIn;
        this.group = groupIn;
        this.waterLevel = waterLevel;
        this.waterSaturationLevel = waterSaturationLevel;
        this.ingredient = ingredient;
    }

    public int getWaterSaturationLevel() {
        return waterSaturationLevel;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public boolean conform(ItemStack i) {
        for (ItemStack stack : ingredient.getMatchingStacks()) {
            if (stack.isItemEqual(i)) {
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
        return null;
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
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.WATER_LEVEL_ITEM_RECIPE_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return WATER_LEVEL_ITEM_RECIPE;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public static WaterLevelItemRecipe getRecipeFromItem(World world, ItemStack itemStack) {
        List<WaterLevelItemRecipe> list = new ArrayList<>();
        if (world != null) {
            list.addAll(world.getRecipeManager().getRecipesForType(NormalRecipeTypes.WATER_LEVEL_ITEM_RECIPE));
            list.addAll(world.getRecipeManager().getRecipesForType(NormalRecipeTypes.WATER_LEVEL_FLUID_RECIPE));
            list.addAll(world.getRecipeManager().getRecipesForType(NormalRecipeTypes.WATER_LEVEL_NBT_RECIPE));
        }
        for (WaterLevelItemRecipe recipe : list) {
            if (recipe.conform(itemStack)) {
                return recipe;
            }
        }
        return null;
    }
}
