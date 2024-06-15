package xyz.koiro.watersource.api.serialize

import kotlinx.serialization.SerialInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import net.minecraft.item.Item
import net.minecraft.recipe.Ingredient
import net.minecraft.registry.Registries
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import xyz.koiro.watersource.WaterSource
import xyz.koiro.watersource.data.FilterRecipeData
import kotlin.jvm.optionals.getOrNull

@Serializable
class IngredientFormat(
    @SerialName("item")
    val itemId: String? = null,
    @SerialName("tag")
    val tagId: String? = null
) {
    fun asItem(): Item?{
       return itemId?.let { Registries.ITEM.getOrEmpty(Identifier.tryParse(it)).getOrNull() }
    }

    fun asTag(): TagKey<Item>? {
        return tagId?.let { TagKey.of(RegistryKeys.ITEM, Identifier.tryParse(tagId)) }
    }

    fun toIngredient(): Ingredient {
        val item = asItem()
        val tag = asTag()
        return when {
            tag != null -> Ingredient.fromTag(tag)
            item != null -> Ingredient.ofItems(item)
            else -> {
                Ingredient.EMPTY
            }
        }
    }

    companion object {
        fun fromIngredient(ingredient: Ingredient): IngredientFormat {
            val json = ingredient.toJson().asJsonObject
            val item = JsonHelper.getString(json, "item", null)
            val tag = JsonHelper.getString(json, "tag", null)
            return IngredientFormat(item, tag)
        }
    }
}