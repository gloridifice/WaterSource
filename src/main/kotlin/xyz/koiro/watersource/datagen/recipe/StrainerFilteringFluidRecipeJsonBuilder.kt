package xyz.koiro.watersource.datagen.recipe

import net.minecraft.advancement.Advancement
import net.minecraft.advancement.AdvancementCriterion
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder
import net.minecraft.data.server.recipe.RecipeExporter
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.recipe.Ingredient
import net.minecraft.util.Identifier
import xyz.koiro.watersource.world.recipe.StrainerFilteringFluidRecipe

class StrainerFilteringFluidRecipeJsonBuilder(
    val id: Identifier,
    val inFluid: Fluid,
    val outFluid: Fluid,
    val strainer: Ingredient
) : CraftingRecipeJsonBuilder {
    var advancementBuilder: Advancement.Builder? = null
    var advancementId: Identifier? = null
    var group: String? = null

    fun advancement(advancementId: Identifier, advancementBuilder: Advancement.Builder): CraftingRecipeJsonBuilder {
        this.advancementId = advancementId
        this.advancementBuilder = advancementBuilder
        return this
    }

    override fun criterion(name: String?, criterion: AdvancementCriterion<*>?): CraftingRecipeJsonBuilder? {
        this.advancementBuilder?.criterion(name, criterion)
        return this
    }

    override fun group(group: String?): CraftingRecipeJsonBuilder {
        this.group = group
        return this
    }

    override fun getOutputItem(): Item {
        return Items.APPLE
    }


    override fun offerTo(exporter: RecipeExporter, recipeId: Identifier) {
        exporter.accept(recipeId, StrainerFilteringFluidRecipe(inFluid, outFluid, strainer), advancementBuilder?.build(recipeId.withPrefixedPath("recipes/")))
    }
}
