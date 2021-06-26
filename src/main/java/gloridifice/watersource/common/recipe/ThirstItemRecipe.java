package gloridifice.watersource.common.recipe;

import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ThirstItemRecipe implements IThirstRecipe{
    protected final int duration,amplifier,probability;
    protected final ItemStack itemStack;
    public ThirstItemRecipe(ItemStack itemStack, int duration, int amplifier, int probability) {
        this.duration = duration;
        this.amplifier = amplifier;
        this.itemStack = itemStack;
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

    public ItemStack getItemStack() {
        return itemStack;
    }
    public boolean conform(ItemStack i){
        return itemStack.isItemEqual(i);
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
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }

    @Override
    public ResourceLocation getId() {
        return null;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.THIRST_ITEM_RECIPE_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return null;
    }

    @Override
    public String getGroup() {
        return this.getGroup();
    }
}
