package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.recipe.*;
import gloridifice.watersource.common.recipe.serializer.ThirstItemRecipeSerializer;
import gloridifice.watersource.common.recipe.serializer.WaterFilterRecipeSerializer;
import gloridifice.watersource.common.recipe.serializer.WaterLevelRecipeSerializer;
import gloridifice.watersource.common.recipe.type.PurifiedWaterBagRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeSerializersRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, WaterSource.MODID);

/*

    public final static RegistryObject<SimpleRecipeSerializer<PurifiedWaterBottleRecipe>> CRAFTING_PURIFIED_WATER_BOTTLE = RECIPE_SERIALIZERS.register("crafting_purified_water_bottle", () -> new SimpleRecipeSerializer<>(PurifiedWaterBottleRecipe::new));
    public final static RegistryObject<SimpleRecipeSerializer<SoulWaterBottleRecipe>> CRAFTING_SOUL_WATER_BOTTLE = RECIPE_SERIALIZERS.register("crafting_soul_water_bottle", () -> new SimpleRecipeSerializer<>(SoulWaterBottleRecipe::new));
    public final static RegistryObject<SimpleRecipeSerializer<PurifiedWaterCupRecipe>> CRAFTING_PURIFIED_WATER_CUP = RECIPE_SERIALIZERS.register("crafting_purified_water_cup", () -> new SimpleRecipeSerializer<>(PurifiedWaterCupRecipe::new));
    public final static RegistryObject<SimpleRecipeSerializer<SoulWaterBagRecipe>> CRAFTING_SOUL_WATER_BAG = RECIPE_SERIALIZERS.register("crafting_soul_water_bag", () -> new SimpleRecipeSerializer<>(SoulWaterBagRecipe::new));
    public final static RegistryObject<SimpleRecipeSerializer<SoulWaterCupRecipe>> CRAFTING_SOUL_WATER_CUP = RECIPE_SERIALIZERS.register("crafting_soul_water_cup", () -> new SimpleRecipeSerializer<>(SoulWaterCupRecipe::new));
*/
    public final static RegistryObject<SimpleRecipeSerializer<PurifiedWaterBagRecipe>> CRAFTING_PURIFIED_WATER_BAG = RECIPE_SERIALIZERS.register("crafting_purified_water_bag", () -> new SimpleRecipeSerializer<>(PurifiedWaterBagRecipe::new));


    public final static RegistryObject<ThirstItemRecipeSerializer<ThirstItemRecipe>> THIRST_ITEM_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("thirst_item", () -> new ThirstItemRecipeSerializer<>(ThirstItemRecipe::new, 2000, 0, 75));
    public final static RegistryObject<ThirstItemRecipeSerializer<ThirstFluidRecipe>> THIRST_FLUID_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("thirst_fluid", () -> new ThirstItemRecipeSerializer<>(ThirstFluidRecipe::new, 2000, 0, 75));
    public final static RegistryObject<ThirstItemRecipeSerializer<ThirstNBTRecipe>> THIRST_NBT_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("thirst_nbt", () -> new ThirstItemRecipeSerializer<>(ThirstNBTRecipe::new, 2000, 0, 75));

    public final static RegistryObject<WaterLevelRecipeSerializer<WaterLevelItemRecipe>> WATER_LEVEL_ITEM_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("water_level_item", () -> new WaterLevelRecipeSerializer<>(WaterLevelItemRecipe::new, 2, 2));
    public final static RegistryObject<WaterLevelRecipeSerializer<WaterLevelFluidRecipe>> WATER_LEVEL_FLUID_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("water_level_fluid", () -> new WaterLevelRecipeSerializer<>(WaterLevelFluidRecipe::new, 2, 2));
    public final static RegistryObject<WaterLevelRecipeSerializer<WaterLevelNBTRecipe>> WATER_LEVEL_NBT_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("water_level_nbt", () -> new WaterLevelRecipeSerializer<>(WaterLevelNBTRecipe::new, 2, 2));

    //todo
    public final static RegistryObject<WaterFilterRecipeSerializer<WaterFilterRecipe>> WATER_FILTER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("water_filter", () -> new WaterFilterRecipeSerializer<>(WaterFilterRecipe::new, Fluids.EMPTY, Fluids.EMPTY));
}
