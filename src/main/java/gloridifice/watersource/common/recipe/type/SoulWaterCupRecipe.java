package gloridifice.watersource.common.recipe.type;

import gloridifice.watersource.common.data.tag.ModTags;
import gloridifice.watersource.registry.FluidRegistry;
import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;

public class SoulWaterCupRecipe extends PurifiedWaterCupRecipe{
    public SoulWaterCupRecipe(ResourceLocation idIn) {
        super(idIn);
        this.fluid = FluidRegistry.soulWaterFluid.get();
        this.strainerTag = ModTags.Block.SOUL_STRAINER;
    }
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.CRAFTING_SOUL_WATER_CUP.get();
    }

}
