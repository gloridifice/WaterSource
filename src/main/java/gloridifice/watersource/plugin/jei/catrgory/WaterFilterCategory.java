package gloridifice.watersource.plugin.jei.catrgory;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.recipe.WaterFilterRecipe;
import gloridifice.watersource.registry.BlockRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.plugins.vanilla.ingredients.fluid.FluidStackRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;

import static gloridifice.watersource.plugin.jei.JEICompat.WATER_FILTER;

public class WaterFilterCategory implements IRecipeCategory<WaterFilterRecipe> {
    private final IDrawable icon;
    private final IGuiHelper guiHelper;

    public WaterFilterCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        icon = guiHelper.createDrawableIngredient(BlockRegistry.BLOCK_WOODEN_WATER_FILTER.asItem().getDefaultInstance());
    }


    @Override
    public ResourceLocation getUid() {
        return WATER_FILTER;
    }

    @Override
    public Class<? extends WaterFilterRecipe> getRecipeClass() {
        return WaterFilterRecipe.class;
    }

    @Override
    public String getTitle() {
        return new TranslationTextComponent("jei.info.watersource.water_filter").getString();
    }

    @Override
    public IDrawable getBackground() {
        return guiHelper.createDrawable(new ResourceLocation(WaterSource.MODID, "textures/gui/jei/recipes.png"), 0, 0, 48, 75);
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(WaterFilterRecipe waterFilterRecipe, IIngredients iIngredients) {
        FluidStack fluidInput = new FluidStack(waterFilterRecipe.getInputFluid(), 1000);
        FluidStack fluidOutput = new FluidStack(waterFilterRecipe.getOutputFluid(), 1000);
        iIngredients.setInputIngredients(Arrays.asList(waterFilterRecipe.getStrainerIngredient()));
        iIngredients.setInput(VanillaTypes.FLUID, fluidInput);
        iIngredients.setOutput(VanillaTypes.FLUID, fluidOutput);
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, WaterFilterRecipe waterFilterRecipe, IIngredients iIngredients) {
        IGuiItemStackGroup guiItemStacks = iRecipeLayout.getItemStacks();
        IGuiFluidStackGroup fluidStackGroup = iRecipeLayout.getFluidStacks();
        guiItemStacks.init(0, true, 5, 28);
        guiItemStacks.set(0, iIngredients.getInputs(VanillaTypes.ITEM).get(0));

        fluidStackGroup.init(1, true, new FluidStackRenderer(1000, false, 16, 16, null), 6, 6, 16, 16, 0, 0);
        fluidStackGroup.set(1, iIngredients.getInputs(VanillaTypes.FLUID).get(0));

        fluidStackGroup.init(2, true, new FluidStackRenderer(1000, false, 16, 16, null), 6, 53, 16, 16, 0, 0);
        fluidStackGroup.set(2, iIngredients.getOutputs(VanillaTypes.FLUID).get(0));

    }
}
