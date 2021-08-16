package gloridifice.watersource.plugin.jei;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.recipe.NormalRecipeTypes;
import gloridifice.watersource.plugin.jei.catrgory.WaterFilterCategory;
import gloridifice.watersource.registry.BlockRegistry;
import gloridifice.watersource.registry.RecipeSerializersRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.Collectors;

@JeiPlugin
public class JEICompat implements IModPlugin {
    public static ResourceLocation WATER_FILTER = new ResourceLocation(WaterSource.MODID, "water_filter");

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(WaterSource.MODID, "recipe");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new WaterFilterCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.ITEM_WOODEN_WATER_FILTER), WATER_FILTER);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.ITEM_IRON_WATER_FILTER), WATER_FILTER);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(getRecipes(NormalRecipeTypes.WATER_FILTER_RECIPE), WATER_FILTER);
    }

    private static List getRecipes(IRecipeType type) {
        World world = Minecraft.getInstance().world;
        return world.getRecipeManager().getRecipesForType(type);
    }
}
