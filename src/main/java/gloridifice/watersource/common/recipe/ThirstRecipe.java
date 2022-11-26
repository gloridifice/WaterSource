package gloridifice.watersource.common.recipe;

import gloridifice.watersource.registry.RecipeSerializersRegistry;
import gloridifice.watersource.registry.RecipeTypesRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.ArrayList;
import java.util.List;

public class ThirstRecipe implements IThirstRecipe {
    protected final int duration, amplifier;
    protected float probability;
    protected final ResourceLocation id;
    protected final String group;
    protected final Ingredient ingredient;
    protected final Fluid fluid;
    protected final CompoundTag compoundTag;

    public ThirstRecipe( ResourceLocation id, String group, int duration, int amplifier, float probability, Ingredient ingredient, Fluid fluid, CompoundTag compoundTag) {
        this.duration = duration;
        this.amplifier = amplifier;
        this.probability = probability;
        this.id = id;
        this.group = group;
        this.ingredient = ingredient;
        this.fluid = fluid;
        if (compoundTag != null) compoundTag.remove("palette");
        this.compoundTag = compoundTag;
    }


    public int getAmplifier() {
        return amplifier;
    }

    public int getDuration() {
        return duration;
    }

    public float getProbability() {return probability;}

    public Ingredient getIngredient() {
        return ingredient;
    }

    public CompoundTag getCompoundTag() {
        return compoundTag;
    }

    public Fluid getFluid() {
        return fluid;
    }

    public boolean conform(ItemStack conformStack) {
        IFluidHandler fluidHandler = FluidUtil.getFluidHandler(conformStack).orElse(null);
        boolean flag = true;
        if (getCompoundTag() != null) {
            if (conformStack.getTag() == null) return false;
            for (String key : getCompoundTag().getAllKeys()) {
                flag &= getCompoundTag().get(key).equals(conformStack.getTag().get(key));
            }
        }
        if (getFluid() != null) {
            flag &= fluidHandler != null && fluidHandler.getFluidInTank(0).getFluid() == getFluid();
        }
        if (!ingredient.isEmpty()){
            boolean i = true;
            for (ItemStack ingredientStack : ingredient.getItems()) {
                i &= !ingredientStack.is(conformStack.getItem());
            }
            flag &= !i;
        }
        return flag;
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
        return RecipeSerializersRegistry.THIRST_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeTypesRegistry.THIRST_RECIPE.get();
    }

    @Override
    public String getGroup() {
        return this.group;
    }

}
