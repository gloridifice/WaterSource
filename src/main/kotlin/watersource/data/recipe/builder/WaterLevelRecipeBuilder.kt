package watersource.data.recipe.builder

import com.google.gson.JsonObject
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.CriterionTriggerInstance
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.registries.ForgeRegistries
import watersource.WaterSource
import watersource.world.recipe.ModRecipeSerializers
import watersource.world.recipe.serializer.WaterLevelRecipeSerializer.KEYS
import watersource.world.recipe.type.WaterLevelRecipeSort
import java.util.function.Consumer

class WaterLevelRecipeBuilder : RecipeBuilder {
    var item : Item? = null
    var fluid : Fluid? = null
    var water = 0f
    var saturationModifier = 0f
    var nbt = ""
    var tag = ""
    var sort = WaterLevelRecipeSort.ITEM
    var group: String = ""
    override fun unlockedBy(p_176496_: String, p_176497_: CriterionTriggerInstance): RecipeBuilder {
        Advancement.Builder.advancement().addCriterion(p_176496_, p_176497_)
        return this
    }

    override fun group(group: String?): RecipeBuilder {
        this.group = group ?: ""
        return this
    }
    companion object{
        fun fluid(fluid: Fluid, water: Float, saturationModifier: Float = 0f): RecipeBuilder{
            val builder = WaterLevelRecipeBuilder()
            builder.sort = WaterLevelRecipeSort.FLUID
            builder.fluid = fluid
            builder.water = water
            builder.saturationModifier = saturationModifier
            return builder
        }
        fun fluid(tag: ResourceLocation, water: Float,saturationModifier: Float = 0f): RecipeBuilder{
            val builder = WaterLevelRecipeBuilder()
            builder.sort = WaterLevelRecipeSort.FLUID
            builder.tag = tag.toString()
            builder.water = water
            builder.saturationModifier = saturationModifier
            return builder
        }
        fun item(item: Item, water: Float, saturationModifier: Float = 0f): RecipeBuilder{
            val builder = WaterLevelRecipeBuilder()
            builder.sort = WaterLevelRecipeSort.ITEM
            builder.item = item
            builder.water = water
            builder.saturationModifier = saturationModifier
            return builder
        }
        fun item(tag: ResourceLocation, water: Float, saturationModifier: Float = 0f): RecipeBuilder{
            val builder = WaterLevelRecipeBuilder()
            builder.sort = WaterLevelRecipeSort.ITEM
            builder.tag = tag.toString()
            builder.water = water
            builder.saturationModifier = saturationModifier
            return builder
        }
    }

    fun nbt(nbt: String): RecipeBuilder{
        this.nbt = nbt
        return this
    }
    fun nbt(nbt: CompoundTag): RecipeBuilder{
        this.nbt = nbt.toString()
        return this
    }
    override fun getResult(): Item {
        return Items.AIR
    }

    override fun save(consumer: Consumer<FinishedRecipe>, id: ResourceLocation) {
        val name = when(sort){
            WaterLevelRecipeSort.ITEM -> ForgeRegistries.ITEMS.getKey(item).toString()
            WaterLevelRecipeSort.FLUID -> ForgeRegistries.FLUIDS.getKey(fluid).toString()
        }
        val recipeId = ResourceLocation(WaterSource.ID, "${sort.name.lowercase()}_${ResourceLocation(name).path}")
        consumer.accept(Result(recipeId, group, water, saturationModifier, sort, tag, name, nbt))
    }

    class Result(val resourceId : ResourceLocation, val group: String = "", val water: Float, val saturationModifier: Float, val sort: WaterLevelRecipeSort, val tag : String = "", val name : String = "", val nbt: String ="") :
        FinishedRecipe {

        override fun serializeRecipeData(jsonObj: JsonObject) {
            jsonObj.addProperty(KEYS.groupKey, group)

            when(sort){
                WaterLevelRecipeSort.ITEM -> {
                    val itemObj = JsonObject()

                    if (name.isNotEmpty()) itemObj.addProperty(KEYS.nameKey, name)
                    if (tag.isNotEmpty()) itemObj.addProperty(KEYS.tagKey, tag)
                    itemObj.addProperty(KEYS.waterKey, water)
                    itemObj.addProperty(KEYS.saturationModifierKey, saturationModifier)
                    if (nbt.isNotEmpty()) itemObj.addProperty(KEYS.nbtKey, nbt)

                    jsonObj.add(KEYS.itemKey, itemObj)
                }
                WaterLevelRecipeSort.FLUID -> {
                    val fluidObj = JsonObject()

                    if (name.isNotEmpty()) fluidObj.addProperty(KEYS.nameKey, name)
                    if (tag.isNotEmpty()) fluidObj.addProperty(KEYS.tagKey, tag)
                    fluidObj.addProperty(KEYS.waterKey, water)
                    fluidObj.addProperty(KEYS.saturationModifierKey, saturationModifier)

                    jsonObj.add(KEYS.fluidKey, fluidObj)
                }
            }
        }

        override fun getId(): ResourceLocation = resourceId
        override fun getType(): RecipeSerializer<*> = ModRecipeSerializers.WATER_LEVEL
        override fun serializeAdvancement(): JsonObject? = null
        override fun getAdvancementId(): ResourceLocation? = null
    }
}
