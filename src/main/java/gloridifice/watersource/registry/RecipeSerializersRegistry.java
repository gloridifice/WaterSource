package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.recipe.*;
import gloridifice.watersource.common.recipe.serializer.StrainerFilterRecipeSerializer;
import gloridifice.watersource.common.recipe.serializer.ThirstRecipeSerializer;
import gloridifice.watersource.common.recipe.serializer.WaterFilterRecipeSerializer;
import gloridifice.watersource.common.recipe.serializer.WaterLevelRecipeSerializer;
import gloridifice.watersource.common.recipe.type.StrainerFilterRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeSerializersRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> MOD_RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, WaterSource.MODID);
    public final static RegistryObject<StrainerFilterRecipeSerializer<StrainerFilterRecipe>> STRAINER_FILTER_RECIPE_SERIALIZER = MOD_RECIPE_SERIALIZERS.register("crafting_strainer_filter", StrainerFilterRecipeSerializer::new);

    public final static RegistryObject<ThirstRecipeSerializer<ThirstRecipe>> THIRST_RECIPE_SERIALIZER = MOD_RECIPE_SERIALIZERS.register("thirst", ThirstRecipeSerializer::new);

    public final static RegistryObject<WaterLevelRecipeSerializer<WaterLevelAndEffectRecipe>> WATER_LEVEL_RECIPE_SERIALIZER = MOD_RECIPE_SERIALIZERS.register("water_level", WaterLevelRecipeSerializer::new);

    public final static RegistryObject<WaterFilterRecipeSerializer<WaterFilterRecipe>> WATER_FILTER_RECIPE_SERIALIZER = MOD_RECIPE_SERIALIZERS.register("water_filter", () -> new WaterFilterRecipeSerializer<>(WaterFilterRecipe::new, Fluids.EMPTY, Fluids.EMPTY));
}
