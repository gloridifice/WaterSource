package gloridifice.watersource.common.data.provider;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.data.CustomRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraftforge.common.data.ForgeRecipeProvider;

import java.util.function.Consumer;

public class RecipeProvider extends ForgeRecipeProvider {
    public RecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }
    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
    {
        CustomRecipeBuilder.customRecipe(RecipeSerializersRegistry.CRAFTING_PURIFIED_WATER_BOTTLE.get()).build(consumer, WaterSource.MODID + ":" + "purified_water_bottle");
        CustomRecipeBuilder.customRecipe(RecipeSerializersRegistry.CRAFTING_SOUL_WATER_BOTTLE.get()).build(consumer, WaterSource.MODID + ":" + "soul_water_bottle");
        CustomRecipeBuilder.customRecipe(RecipeSerializersRegistry.CRAFTING_PURIFIED_WATER_BAG.get()).build(consumer, WaterSource.MODID + ":" + "purified_water_bag");
        CustomRecipeBuilder.customRecipe(RecipeSerializersRegistry.CRAFTING_PURIFIED_WATER_CUP.get()).build(consumer, WaterSource.MODID + ":" + "purified_water_cup");

        //ShapelessRecipeBuilder.shapelessRecipe(ItemRegistry.itemPurifiedWaterBottle).addIngredient(Ingredient.fromTag(ModTags.Item.PURIFICATION_STRAINER)).addIngredient(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER))).setGroup("purified_water").build(consumer);
    }
}
