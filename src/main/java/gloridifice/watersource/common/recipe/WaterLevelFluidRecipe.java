package gloridifice.watersource.common.recipe;

import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidUtil;

public class WaterLevelFluidRecipe extends WaterLevelItemRecipe {
    public WaterLevelFluidRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredient, int waterLevel, int waterSaturationLevel) {
        super(idIn, groupIn, ingredient, waterLevel, waterSaturationLevel);
    }

    @Override
    public boolean conform(ItemStack stack) {
        for (ItemStack itemStack : ingredient.getMatchingStacks()) {
            if (stack.isItemEqual(itemStack) && FluidUtil.getFluidHandler(itemStack) != null && FluidUtil.getFluidHandler(stack) != null) {
                if (FluidUtil.getFluidHandler(stack).map(data -> FluidUtil.getFluidHandler(itemStack).map(data1 -> data1.getFluidInTank(0).isFluidEqual(data.getFluidInTank(0))).orElse(false)).orElse(false)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public IRecipeType<?> getType() {
        return NormalRecipeTypes.WATER_LEVEL_FLUID_RECIPE;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.WATER_LEVEL_FLUID_RECIPE_SERIALIZER.get();
    }
}
