package xyz.koiro.watersource.data

import kotlinx.serialization.json.Json
import net.minecraft.fluid.Fluid
import net.minecraft.item.ItemStack
import net.minecraft.resource.Resource
import net.minecraft.util.Identifier
import xyz.koiro.watersource.WaterSource

object FilterRecipeDataManager : DataManager<FilterRecipeData>(
    ModResourceRegistries.FILTER_RECIPE_KEY,
    WaterSource.identifier(ModResourceRegistries.FILTER_RECIPE_KEY)
) {
    override fun processResourceToData(resId: Identifier, res: Resource): FilterRecipeData {
        val stream = res.inputStream
        val string = String(stream.readAllBytes())
        val format = Json.decodeFromString<FilterRecipeData.Format>(string)
        val data = FilterRecipeData(format)
        return data
    }

    fun findRecipe(fluidIn: Fluid, strainerStack: ItemStack): FilterRecipeData? {
        return sequence?.find { data ->
            data.match(fluidIn, strainerStack)
        }
    }
}