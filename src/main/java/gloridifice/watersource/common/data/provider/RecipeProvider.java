package gloridifice.watersource.common.data.provider;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.data.CustomRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraftforge.common.data.ForgeRecipeProvider;

import java.util.function.Consumer;

public class RecipeProvider extends ForgeRecipeProvider {
    public RecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }
    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
    {
        CustomRecipeBuilder.customRecipe(RecipeSerializersRegistry.STRAINER_USING_CRAFTING.get()).build(consumer, WaterSource.MODID + ":" + "strainer_using");
    }
}
