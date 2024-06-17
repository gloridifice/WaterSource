package xyz.koiro.watersource.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.server.recipe.*
import net.minecraft.fluid.Fluids
import net.minecraft.item.Items
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.book.RecipeCategory
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.ItemTags
import xyz.koiro.watersource.WaterSource
import xyz.koiro.watersource.api.inputWithCriterion
import xyz.koiro.watersource.datagen.recipe.StrainerFilteringFluidRecipeJsonBuilder
import xyz.koiro.watersource.datagen.recipe.StrainerFilteringItemRecipeJsonBuilder
import xyz.koiro.watersource.api.simpleStack
import xyz.koiro.watersource.world.fluid.ModFluids
import xyz.koiro.watersource.world.item.ModItems
import xyz.koiro.watersource.world.tag.ModTags
import java.util.concurrent.CompletableFuture

class ModRecipeGenerator(output: FabricDataOutput?,
                         registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>?
) : FabricRecipeProvider(output, registriesFuture) {
    override fun generate(exporter: RecipeExporter?) {
        StrainerFilteringFluidRecipeJsonBuilder(
            WaterSource.identifier("water_to_purified_water"),
            Fluids.WATER,
            ModFluids.PURIFIED_WATER,
            Ingredient.fromTag(ModTags.Item.PURIFICATION_STRAINER)
        ).offerTo(exporter)

        StrainerFilteringItemRecipeJsonBuilder(
            WaterSource.identifier("potion_to_purified_water"),
            Ingredient.ofItems(Items.POTION),
            ModItems.PURIFIED_WATER_BOTTLE.simpleStack(),
            1,
            Ingredient.fromTag(ModTags.Item.PURIFICATION_STRAINER)
        ).offerTo(exporter)

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.WOODEN_CUP_EMPTY, 2)
            .inputWithCriterion('w', ItemTags.PLANKS)
            .pattern("w w")
            .pattern("w w")
            .pattern(" w ")
            .offerTo(exporter)

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RAW_POTTERY_CUP)
            .inputWithCriterion('w', Items.CLAY_BALL)
            .pattern("w w")
            .pattern("w w")
            .pattern(" w ")
            .offerTo(exporter)
        RecipeProvider.offerSmelting(
            exporter,
            listOf(ModItems.RAW_POTTERY_CUP), RecipeCategory.MISC,
            ModItems.POTTERY_CUP_EMPTY, 0.5f, 200, "pottery"
        )

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.PAPER_STRAINER)
            .inputWithCriterion('p', Items.PAPER)
            .inputWithCriterion('c', Items.CHARCOAL)
            .pattern("ppp")
            .pattern("ccc")
            .pattern("ppp")
            .offerTo(exporter)

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.NATURAL_STRAINER)
            .inputWithCriterion('s', ItemTags.SAND)
            .inputWithCriterion('c', Items.CHARCOAL)
            .inputWithCriterion('r', Items.CLAY_BALL)
            .pattern("ccc")
            .pattern("sss")
            .pattern("rrr")
            .offerTo(exporter)

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.LEATHER_WATER_BAG_SMALL)
            .inputWithCriterion('l', Items.LEATHER)
            .inputWithCriterion('s', Items.STRING)
            .inputWithCriterion('i', ModTags.Item.BASICS_INGOT)
            .pattern("sis")
            .pattern("l l")
            .pattern("lll")
            .offerTo(exporter)

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.LEATHER_WATER_BAG_MEDIUM)
            .inputWithCriterion('b', ModItems.LEATHER_WATER_BAG_SMALL)
            .inputWithCriterion('l', Items.LEATHER)
            .inputWithCriterion('s', Items.STRING)
            .inputWithCriterion('i', Items.IRON_INGOT)
            .pattern("sis")
            .pattern("ibi")
            .pattern("lll")
            .offerTo(exporter)

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.LEATHER_WATER_BAG_LARGE)
            .inputWithCriterion('b', ModItems.LEATHER_WATER_BAG_MEDIUM)
            .inputWithCriterion('l', Items.LEATHER)
            .inputWithCriterion('d', Items.DIAMOND)
            .inputWithCriterion('h', Items.HEART_OF_THE_SEA)
            .pattern("dhd")
            .pattern("lbl")
            .pattern("lll")
            .offerTo(exporter)

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.WOODEN_FILTER_BLOCK)
            .inputWithCriterion('w', ItemTags.LOGS)
            .inputWithCriterion('p', ItemTags.PLANKS)
            .inputWithCriterion('g', Items.GLASS)
            .pattern("wgw")
            .pattern("ppp")
            .pattern("wgw")
            .offerTo(exporter)

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.IRON_FILTER_BLOCK)
            .inputWithCriterion('w', Items.IRON_INGOT)
            .inputWithCriterion('p', Items.IRON_BLOCK)
            .inputWithCriterion('g', Items.GLASS)
            .pattern("wgw")
            .pattern("wpw")
            .pattern("wgw")
            .offerTo(exporter)
    }
}