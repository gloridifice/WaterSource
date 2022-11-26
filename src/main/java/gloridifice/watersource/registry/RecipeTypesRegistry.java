package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.recipe.ThirstRecipe;
import gloridifice.watersource.common.recipe.WaterFilterRecipe;
import gloridifice.watersource.common.recipe.WaterLevelAndEffectRecipe;
import gloridifice.watersource.common.recipe.type.StrainerFilterRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Locale;

public class RecipeTypesRegistry {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
             DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, WaterSource.MODID);;

    public static final RegistryObject<RecipeType<ThirstRecipe>> THIRST_RECIPE =
            register("thirst");

    public static final RegistryObject<RecipeType<WaterLevelAndEffectRecipe>> WATER_LEVEL_RECIPE =
            register("water_level");

    public static final RegistryObject<RecipeType<StrainerFilterRecipe>> STRAINER_FILTER_RECIPE_RECIPE =
            register("strainer_filter");

    public static final RegistryObject<RecipeType<WaterFilterRecipe>> WATER_FILTER_RECIPE =
                    register("water_filter");


    private static <T extends Recipe<?>> RegistryObject<RecipeType<T>> register(String name)
    {
        return RECIPE_TYPES.register(name.toLowerCase(Locale.ROOT), () -> new RecipeType<T>() {
            @Override
            public String toString() {
                return name;
            }
        });
    }
}
