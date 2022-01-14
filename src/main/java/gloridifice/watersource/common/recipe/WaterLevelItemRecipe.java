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

import java.util.ArrayList;
import java.util.List;

import static gloridifice.watersource.common.recipe.NormalRecipeTypes.WATER_LEVEL_ITEM_RECIPE;

public class WaterLevelItemRecipe implements Recipe<Inventory> {
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
    public ItemStack assemble(Inventory inventory) {
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

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.WATER_LEVEL_ITEM_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return WATER_LEVEL_ITEM_RECIPE;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public static WaterLevelItemRecipe getRecipeFromItem(Level level, ItemStack itemStack) {
        List<WaterLevelItemRecipe> list = new ArrayList<>();
        if (level != null) {
            list.addAll(level.getRecipeManager().getAllRecipesFor(NormalRecipeTypes.WATER_LEVEL_ITEM_RECIPE));
            list.addAll(level.getRecipeManager().getAllRecipesFor(NormalRecipeTypes.WATER_LEVEL_FLUID_RECIPE));
            list.addAll(level.getRecipeManager().getAllRecipesFor(NormalRecipeTypes.WATER_LEVEL_NBT_RECIPE));
        }
        for (WaterLevelItemRecipe recipe : list) {
            if (recipe.conform(itemStack)) {
                return recipe;
            }
        }
        return null;
    }
}
