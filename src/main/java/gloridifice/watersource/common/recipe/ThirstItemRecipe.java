package gloridifice.watersource.common.recipe;

import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class ThirstItemRecipe implements IThirstRecipe {
    protected final int duration, amplifier, probability;
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

    public boolean conform(ItemStack i) {
        for (ItemStack stack : ingredient.getItems()) {
            if (stack.is(i.getItem())) {
                return true;
            }
        }
        return false;
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
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.THIRST_ITEM_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return NormalRecipeTypes.THIRST_ITEM_RECIPE;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    public static ThirstItemRecipe getRecipeFromItem(Level world, ItemStack itemStack) {
        List<ThirstItemRecipe> list = new ArrayList<>();
        if (world != null) {
            list.addAll(world.getRecipeManager().getAllRecipesFor(NormalRecipeTypes.THIRST_ITEM_RECIPE));
            list.addAll(world.getRecipeManager().getAllRecipesFor(NormalRecipeTypes.THIRST_FLUID_RECIPE));
            list.addAll(world.getRecipeManager().getAllRecipesFor(NormalRecipeTypes.THIRST_NBT_RECIPE));
        }
        for (ThirstItemRecipe recipe : list) {
            if (recipe.conform(itemStack)) {
                return recipe;
            }
        }
        return null;
    }
}
