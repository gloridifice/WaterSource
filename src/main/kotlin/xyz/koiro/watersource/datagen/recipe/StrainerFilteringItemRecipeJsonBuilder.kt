package xyz.koiro.watersource.datagen.recipe

import com.google.gson.JsonObject
import net.minecraft.advancement.Advancement
import net.minecraft.advancement.AdvancementRewards
import net.minecraft.advancement.CriterionMerger
import net.minecraft.advancement.criterion.CriterionConditions
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.util.Identifier
import xyz.koiro.watersource.api.SerializeUtils
import xyz.koiro.watersource.identifier
import xyz.koiro.watersource.world.recipe.ModRecipes
import java.util.function.Consumer

class StrainerFilteringItemRecipeJsonBuilder(
    val id: Identifier,
    val input: Ingredient,
    val output: ItemStack,
    val cost: Int,
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

    override fun criterion(name: String?, conditions: CriterionConditions?): CraftingRecipeJsonBuilder {
        this.advancementBuilder?.criterion(name, conditions)
        return this
    }

    override fun group(group: String?): CraftingRecipeJsonBuilder {
        this.group = group
        return this
    }

    override fun getOutputItem(): Item {
        return output.item
    }

    override fun offerTo(exporter: Consumer<RecipeJsonProvider>?, recipeId: Identifier?) {
        advancementBuilder?.parent(CraftingRecipeJsonBuilder.ROOT)
            ?.criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
            ?.rewards(AdvancementRewards.Builder.recipe(recipeId))?.criteriaMerger(CriterionMerger.OR)
        exporter?.accept(
            Provider(
                id,
                input,
                output,
                cost,
                strainer,
                advancementBuilder,
                advancementId,
                group,
            )
        )
    }

    class Provider(
        val id: Identifier,
        val input: Ingredient,
        val output: ItemStack,
        val cost: Int,
        val strainer: Ingredient,
        val advancementBuilder: Advancement.Builder?,
        val advancementIdentifier: Identifier?,
        val group: String?,
    ) : RecipeJsonProvider {
        override fun serialize(json: JsonObject) {
            if (!group.isNullOrBlank()) json.addProperty("group", group)
            val outputJson = SerializeUtils.itemStackToJsonObject(output)
            json.add("input", input.toJson())
            json.add("output", outputJson)
            json.addProperty("cost", cost)
            json.add("strainer", strainer.toJson())
        }

        override fun getRecipeId(): Identifier = id

        override fun getSerializer(): RecipeSerializer<*> = ModRecipes.STRAINER_FILTERING_ITEM_SERIALIZER

        override fun toAdvancementJson(): JsonObject? = this.advancementBuilder?.toJson()

        override fun getAdvancementId(): Identifier? = this.advancementIdentifier
    }
}
