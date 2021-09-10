package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.recipe.*;
import gloridifice.watersource.common.recipe.serializer.ThirstItemRecipeSerializer;
import gloridifice.watersource.common.recipe.serializer.WaterFilterRecipeSerializer;
import gloridifice.watersource.common.recipe.serializer.WaterLevelRecipeSerializer;
import gloridifice.watersource.common.recipe.type.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.ResourceLocation;
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

    public final static RegistryObject<ThirstItemRecipeSerializer<ThirstItemRecipe>> THIRST_ITEM_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("thirst_item", () -> new ThirstItemRecipeSerializer<>(ThirstItemRecipe::new, 2000, 0, 75));
    public final static RegistryObject<ThirstItemRecipeSerializer<ThirstFluidRecipe>> THIRST_FLUID_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("thirst_fluid", () -> new ThirstItemRecipeSerializer<>(ThirstFluidRecipe::new, 2000, 0, 75));
    public final static RegistryObject<ThirstItemRecipeSerializer<ThirstNBTRecipe>> THIRST_NBT_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("thirst_nbt", () -> new ThirstItemRecipeSerializer<>(ThirstNBTRecipe::new, 2000, 0, 75));

    public final static RegistryObject<WaterLevelRecipeSerializer<WaterLevelItemRecipe>> WATER_LEVEL_ITEM_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("water_level_item", () -> new WaterLevelRecipeSerializer<>(WaterLevelItemRecipe::new, 2, 2));
    public final static RegistryObject<WaterLevelRecipeSerializer<WaterLevelFluidRecipe>> WATER_LEVEL_FLUID_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("water_level_fluid", () -> new WaterLevelRecipeSerializer<>(WaterLevelFluidRecipe::new, 2, 2));
    public final static RegistryObject<WaterLevelRecipeSerializer<WaterLevelNBTRecipe>> WATER_LEVEL_NBT_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("water_level_nbt", () -> new WaterLevelRecipeSerializer<>(WaterLevelNBTRecipe::new, 2, 2));

    public final static RegistryObject<WaterFilterRecipeSerializer<WaterFilterRecipe>> WATER_FILTER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("water_filter", () -> new WaterFilterRecipeSerializer<>(WaterFilterRecipe::new, Fluids.EMPTY, Fluids.EMPTY));

    //public final static RegistryObject<CookingRecipeSerializer<PurifiedWaterCookingRecipe>> PURIFIED_WATER_COOKING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("purified_water_cooking", () -> new CookingRecipeSerializer(FurnaceRecipe::new, 1));
}
