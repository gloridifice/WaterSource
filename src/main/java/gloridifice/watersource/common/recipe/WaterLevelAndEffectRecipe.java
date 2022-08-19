package gloridifice.watersource.common.recipe;

import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static gloridifice.watersource.registry.RecipeTypesRegistry.WATER_LEVEL_RECIPE;

public class WaterLevelAndEffectRecipe implements Recipe<Inventory>, Comparable<WaterLevelAndEffectRecipe> {
    protected final int waterLevel, waterSaturationLevel;
    protected final ResourceLocation id;
    protected final List<MobEffectInstance> mobEffectInstances;
    protected final String group;
    protected final Ingredient ingredient;
    protected final Fluid fluid;
    protected final CompoundTag compoundTag;
    protected float priority = 0;

    public WaterLevelAndEffectRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredient, int waterLevel, int waterSaturationLevel, List<MobEffectInstance> effectInstances, Fluid fluid, CompoundTag compoundTag) {
        this.id = idIn;
        this.group = groupIn;
        this.waterLevel = waterLevel;
        this.waterSaturationLevel = waterSaturationLevel;
        this.ingredient = ingredient;
        this.mobEffectInstances = effectInstances;
        this.fluid = fluid;
        this.compoundTag = compoundTag;

        float priority = 0;
        if (!ingredient.isEmpty()) priority += 1.5F;
        if (compoundTag != null) priority += 1F;
        if (fluid != null) priority += 1F;
        if (effectInstances.size() > 0) priority += 0.1F;

        this.priority = priority;
    }

    public int getWaterSaturationLevel() {
        return waterSaturationLevel;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public List<MobEffectInstance> getMobEffectInstances() {
        return mobEffectInstances;
    }

    public Fluid getFluid() {
        return fluid;
    }

    public CompoundTag getCompoundTag() {
        return compoundTag;
    }

    public float getPriority() {
        return priority;
    }

    public boolean conform(ItemStack conformStack) {
        IFluidHandler fluidHandler = FluidUtil.getFluidHandler(conformStack).orElse(null);
        boolean flag = true;
        if (getCompoundTag() != null) {
            flag &= getCompoundTag().equals(conformStack.getTag());
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
        return RecipeSerializersRegistry.WATER_LEVEL_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return WATER_LEVEL_RECIPE.get();
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public int compareTo(@NotNull WaterLevelAndEffectRecipe o) {
        return o.getPriority() > this.getPriority() ? 1 : -1;
    }
}
