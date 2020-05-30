package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.recipe.type.StrainerUsingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeSerializersRegistry {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = new DeferredRegister<>(ForgeRegistries.RECIPE_SERIALIZERS, WaterSource.MODID);

    public final static RegistryObject<SpecialRecipeSerializer<StrainerUsingRecipe>> STRAINER_USING_CRAFTING = RECIPE_SERIALIZERS.register("crafting_strainer_using", () -> new SpecialRecipeSerializer<>(StrainerUsingRecipe::new));

}
