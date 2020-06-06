package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.recipe.type.PurifiedWaterBottleRecipe;
import gloridifice.watersource.common.recipe.type.SoulWaterBottleRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeSerializersRegistry {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = new DeferredRegister<>(ForgeRegistries.RECIPE_SERIALIZERS, WaterSource.MODID);

    public final static RegistryObject<SpecialRecipeSerializer<PurifiedWaterBottleRecipe>> CRAFTING_PURIFIED_WATER_BOTTLE = RECIPE_SERIALIZERS.register("crafting_purified_water_bottle", () -> new SpecialRecipeSerializer<>(PurifiedWaterBottleRecipe::new));
    public final static RegistryObject<SpecialRecipeSerializer<SoulWaterBottleRecipe>> CRAFTING_SOUL_WATER_BOTTLE = RECIPE_SERIALIZERS.register("crafting_soul_water_bottle", () -> new SpecialRecipeSerializer<>(SoulWaterBottleRecipe::new));
}
