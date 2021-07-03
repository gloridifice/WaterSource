package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.recipe.ThirstFluidRecipe;
import gloridifice.watersource.common.recipe.ThirstItemRecipe;
import gloridifice.watersource.common.recipe.ThirstNBTRecipe;
import gloridifice.watersource.common.recipe.serializer.ThirstItemRecipeSerializer;
import gloridifice.watersource.common.recipe.type.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeSerializersRegistry {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, WaterSource.MODID);

    public final static RegistryObject<SpecialRecipeSerializer<PurifiedWaterBottleRecipe>> CRAFTING_PURIFIED_WATER_BOTTLE = RECIPE_SERIALIZERS.register("crafting_purified_water_bottle", () -> new SpecialRecipeSerializer<>(PurifiedWaterBottleRecipe::new));
    public final static RegistryObject<SpecialRecipeSerializer<SoulWaterBottleRecipe>> CRAFTING_SOUL_WATER_BOTTLE = RECIPE_SERIALIZERS.register("crafting_soul_water_bottle", () -> new SpecialRecipeSerializer<>(SoulWaterBottleRecipe::new));
    public final static RegistryObject<SpecialRecipeSerializer<PurifiedWaterBagRecipe>> CRAFTING_PURIFIED_WATER_BAG = RECIPE_SERIALIZERS.register("crafting_purified_water_bag", () -> new SpecialRecipeSerializer<>(PurifiedWaterBagRecipe::new));
    public final static RegistryObject<SpecialRecipeSerializer<PurifiedWaterCupRecipe>> CRAFTING_PURIFIED_WATER_CUP = RECIPE_SERIALIZERS.register("crafting_purified_water_cup", () -> new SpecialRecipeSerializer<>(PurifiedWaterCupRecipe::new));
    public final static RegistryObject<SpecialRecipeSerializer<SoulWaterBagRecipe>> CRAFTING_SOUL_WATER_BAG = RECIPE_SERIALIZERS.register("crafting_soul_water_bag", () -> new SpecialRecipeSerializer<>(SoulWaterBagRecipe::new));
    public final static RegistryObject<SpecialRecipeSerializer<SoulWaterCupRecipe>> CRAFTING_SOUL_WATER_CUP = RECIPE_SERIALIZERS.register("crafting_soul_water_cup", () -> new SpecialRecipeSerializer<>(SoulWaterCupRecipe::new));

    public final static RegistryObject<ThirstItemRecipeSerializer<ThirstItemRecipe>> THIRST_ITEM_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("thirst_item",() -> new ThirstItemRecipeSerializer<>(ThirstItemRecipe::new,2000,0,75));
    public final static RegistryObject<ThirstItemRecipeSerializer<ThirstFluidRecipe>> THIRST_FLUID_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("thirst_fluid",() -> new ThirstItemRecipeSerializer<>(ThirstFluidRecipe::new,2000,0,75));
    public final static RegistryObject<ThirstItemRecipeSerializer<ThirstNBTRecipe>> THIRST_NBT_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("thirst_nbt",() -> new ThirstItemRecipeSerializer<>(ThirstNBTRecipe::new,2000,0,75));

}
