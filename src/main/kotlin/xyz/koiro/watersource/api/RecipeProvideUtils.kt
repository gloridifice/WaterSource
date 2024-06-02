package xyz.koiro.watersource.api

import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.registry.tag.ItemTags
import net.minecraft.registry.tag.TagKey

fun ShapedRecipeJsonBuilder.inputWithCriterion(char: Char, tag: TagKey<Item>): ShapedRecipeJsonBuilder{
    this.input(char, tag)
    this.criterion("has_tag_${tag}", FabricRecipeProvider.conditionsFromTag(tag))
    return this
}
fun ShapedRecipeJsonBuilder.inputWithCriterion(char: Char, item: ItemConvertible): ShapedRecipeJsonBuilder{
    this.input(char, item)
    this.criterion(FabricRecipeProvider.hasItem(item), FabricRecipeProvider.conditionsFromItem(item))
    return this
}
