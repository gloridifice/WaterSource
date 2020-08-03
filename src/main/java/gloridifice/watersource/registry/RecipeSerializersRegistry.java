package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.recipe.type.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeSerializersRegistry {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = new DeferredRegister<>(ForgeRegistries.RECIPE_SERIALIZERS, WaterSource.MODID);

    public final static RegistryObject<SpecialRecipeSerializer<PurifiedWaterBottleRecipe>> CRAFTING_PURIFIED_WATER_BOTTLE = RECIPE_SERIALIZERS.register("crafting_purified_water_bottle", () -> new SpecialRecipeSerializer<>(PurifiedWaterBottleRecipe::new));
    public final static RegistryObject<SpecialRecipeSerializer<SoulWaterBottleRecipe>> CRAFTING_SOUL_WATER_BOTTLE = RECIPE_SERIALIZERS.register("crafting_soul_water_bottle", () -> new SpecialRecipeSerializer<>(SoulWaterBottleRecipe::new));
    public final static RegistryObject<SpecialRecipeSerializer<PurifiedWaterBagRecipe>> CRAFTING_PURIFIED_WATER_BAG = RECIPE_SERIALIZERS.register("crafting_purified_water_bag", () -> new SpecialRecipeSerializer<>(PurifiedWaterBagRecipe::new));
    public final static RegistryObject<SpecialRecipeSerializer<PurifiedWaterCupRecipe>> CRAFTING_PURIFIED_WATER_CUP = RECIPE_SERIALIZERS.register("crafting_purified_water_cup", () -> new SpecialRecipeSerializer<>(PurifiedWaterCupRecipe::new));
    public final static RegistryObject<SpecialRecipeSerializer<SoulWaterBagRecipe>> CRAFTING_SOUL_WATER_BAG = RECIPE_SERIALIZERS.register("crafting_soul_water_bag", () -> new SpecialRecipeSerializer<>(SoulWaterBagRecipe::new));
    public final static RegistryObject<SpecialRecipeSerializer<SoulWaterCupRecipe>> CRAFTING_SOUL_WATER_CUP = RECIPE_SERIALIZERS.register("crafting_soul_water_cup", () -> new SpecialRecipeSerializer<>(SoulWaterCupRecipe::new));

}
