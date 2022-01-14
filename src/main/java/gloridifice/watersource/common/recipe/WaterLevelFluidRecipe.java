package gloridifice.watersource.common.recipe;

import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidUtil;

public class WaterLevelFluidRecipe extends WaterLevelItemRecipe {
    public WaterLevelFluidRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredient, int waterLevel, int waterSaturationLevel) {
        super(idIn, groupIn, ingredient, waterLevel, waterSaturationLevel);
    }

    @Override
    public boolean conform(ItemStack stack) {
        for (ItemStack itemStack : ingredient.getItems()) {
            if (stack.is(itemStack.getItem()) && FluidUtil.getFluidHandler(itemStack) != null && FluidUtil.getFluidHandler(stack) != null) {
                if (FluidUtil.getFluidHandler(stack).map(data -> FluidUtil.getFluidHandler(itemStack).map(data1 -> data1.getFluidInTank(0).isFluidEqual(data.getFluidInTank(0))).orElse(false)).orElse(false)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public RecipeType<?> getType() {
        return NormalRecipeTypes.WATER_LEVEL_FLUID_RECIPE;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.WATER_LEVEL_FLUID_RECIPE_SERIALIZER.get();
    }
}
