package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.recipe.ThirstRecipe;
import gloridifice.watersource.common.recipe.WaterFilterRecipe;
import gloridifice.watersource.common.recipe.WaterLevelAndEffectRecipe;
import gloridifice.watersource.common.recipe.type.StrainerFilterRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class RecipeTypesRegistry {
    public static final DeferredRegister<RecipeType<?>> MOD_RECIPE_TYPES = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, WaterSource.MODID);
    public static final RegistryObject<RecipeType<ThirstRecipe>> THIRST_RECIPE = MOD_RECIPE_TYPES.register( "thirst", () -> new RecipeType<>(){});
    public static final RegistryObject<RecipeType<WaterLevelAndEffectRecipe>> WATER_LEVEL_RECIPE = MOD_RECIPE_TYPES.register("water_level", () -> new RecipeType<>(){});

    public static final RegistryObject<RecipeType<StrainerFilterRecipe>> STRAINER_FILTER_RECIPE_RECIPE = MOD_RECIPE_TYPES.register("strainer_filter", () -> new RecipeType<>(){});
    public static final RegistryObject<RecipeType<WaterFilterRecipe>> WATER_FILTER_RECIPE = MOD_RECIPE_TYPES.register("water_filter", () -> new RecipeType<>(){});

}
