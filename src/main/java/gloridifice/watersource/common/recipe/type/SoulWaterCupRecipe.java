package gloridifice.watersource.common.recipe.type;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.registry.FluidRegistry;
import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;

public class SoulWaterCupRecipe extends PurifiedWaterCupRecipe {
    public SoulWaterCupRecipe(ResourceLocation idIn) {
        super(idIn);
        this.fluid = FluidRegistry.SOUL_WATER.get();
        this.strainerTag = new ResourceLocation(WaterSource.MODID, "soul_strainers");
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.CRAFTING_SOUL_WATER_CUP.get();
    }

}
