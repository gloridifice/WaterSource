package watersource.world.recipe.serializer

import com.google.gson.JsonObject
import net.minecraft.core.Registry
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.TagParser
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.GsonHelper
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.registries.ForgeRegistries
import watersource.world.recipe.type.WaterLevelRecipe
import watersource.world.recipe.type.WaterLevelRecipeSort
import watersource.world.recipe.type.WaterLevelRecipeSort.*

class WaterLevelRecipeSerializer : RecipeSerializer<WaterLevelRecipe> {
    object KEYS{
        const val sortKey = "sort"
        const val groupKey = "group"
        const val fluidKey = "fluid"
        const val itemKey = "item"
        const val nameKey = "name"
        const val tagKey = "tag"
        const val nbtKey = "nbt"
        const val waterKey = "water"
        const val saturationModifierKey = "saturationModifier"
    }
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return super.toString()
    }

    override fun fromJson(resourceLocation: ResourceLocation, jsonObject: JsonObject): WaterLevelRecipe {
        val recipe : WaterLevelRecipe = when {
            GsonHelper.isObjectNode(jsonObject, KEYS.fluidKey) -> {
                val fluidJson: JsonObject = GsonHelper.getAsJsonObject(jsonObject, KEYS.fluidKey)
                val name = GsonHelper.getAsString(fluidJson, KEYS.nameKey)
                val tag = GsonHelper.getAsString(fluidJson, KEYS.tagKey)
                val water = GsonHelper.getAsFloat(fluidJson, KEYS.waterKey)
                val saturationModifier = GsonHelper.getAsFloat(fluidJson, KEYS.saturationModifierKey)

                val fluid = ForgeRegistries.FLUIDS.getValue(ResourceLocation(name))
                WaterLevelRecipe.createFluidRecipe(fluid, tag, water, saturationModifier)
            }
            GsonHelper.isObjectNode(jsonObject, KEYS.itemKey) -> {
                val itemJson: JsonObject = GsonHelper.getAsJsonObject(jsonObject, KEYS.itemKey)
                val name = GsonHelper.getAsString(itemJson, KEYS.nameKey)
                val tag = GsonHelper.getAsString(itemJson, KEYS.tagKey)
                val nbtString = GsonHelper.getAsString(itemJson, KEYS.nbtKey)
                val water = GsonHelper.getAsFloat(itemJson, KEYS.waterKey)
                val saturationModifier = GsonHelper.getAsFloat(itemJson, KEYS.saturationModifierKey)

                val item = ForgeRegistries.ITEMS.getValue(ResourceLocation(name))
                val nbt = TagParser.parseTag(nbtString)
                WaterLevelRecipe.createItemRecipe(item, tag, water, saturationModifier, nbt)
            }
            else -> WaterLevelRecipe()
        }
        recipe.recipeGroup = GsonHelper.getAsString(jsonObject, KEYS.groupKey)
        return recipe
    }

    override fun fromNetwork(resourceLocation: ResourceLocation, buf: FriendlyByteBuf): WaterLevelRecipe {
        val group = buf.readUtf()
        val sort = WaterLevelRecipeSort.values()[buf.readShort().toInt()]
        val water: Float = buf.readFloat()
        val saturationModifier: Float = buf.readFloat()

        var recipe: WaterLevelRecipe =
            when (sort) {
                ITEM -> {
                    val item: Item? = buf.readById(Registry.ITEM)
                    val tag: String = buf.readUtf()
                    val nbt: CompoundTag? = buf.readNbt()
                    WaterLevelRecipe.createItemRecipe(item, tag, water, saturationModifier, nbt)
                }

                FLUID -> {
                    val fluid: Fluid? = buf.readById(Registry.FLUID)
                    val tag: String = buf.readUtf()
                    WaterLevelRecipe.createFluidRecipe(fluid, tag, water, saturationModifier)
                }
            }
        recipe.recipeGroup = group
        return recipe;
    }

    override fun toNetwork(buf: FriendlyByteBuf, recipe: WaterLevelRecipe) {
        buf.writeUtf(recipe.recipeGroup)
        buf.writeShort(recipe.sort.ordinal)
        buf.writeFloat(recipe.water)
        buf.writeFloat(recipe.saturationModifier)
        when (recipe.sort) {
            ITEM -> {
                buf.writeId(Registry.ITEM, recipe.item)
                buf.writeUtf(recipe.tag)
                buf.writeNbt(recipe.nbt)
            }

            FLUID -> {
                buf.writeId(Registry.FLUID, recipe.fluid)
                buf.writeUtf(recipe.tag)
            }
        }
    }
}