package xyz.koiro.watersource.data

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.mojang.brigadier.StringReader
import com.mojang.serialization.JsonOps
import kotlinx.serialization.json.Json
import net.fabricmc.fabric.impl.resource.loader.ModResourcePackUtil.GSON
import net.minecraft.fluid.Fluid
import net.minecraft.item.ItemStack
import net.minecraft.registry.BuiltinRegistries
import net.minecraft.registry.RegistryEntryLookup.RegistryLookup
import net.minecraft.registry.RegistryOps
import net.minecraft.registry.RegistryWrapper
import net.minecraft.resource.Resource
import net.minecraft.util.Identifier
import net.minecraft.util.JsonReaderUtils
import xyz.koiro.watersource.WaterSource

object FilterRecipeDataManager : DataManager<FilterRecipeData>(
    ModResourceRegistries.FILTER_RECIPE_KEY,
    WaterSource.identifier(ModResourceRegistries.FILTER_RECIPE_KEY)
) {
    val registryLookup: RegistryWrapper.WrapperLookup = BuiltinRegistries.createWrapperLookup()

    override fun processResourceToData(resId: Identifier, res: Resource): FilterRecipeData {
        val reader = StringReader(String(res.inputStream.readAllBytes()))
        val data = JsonReaderUtils.parse(registryLookup, reader, FilterRecipeData.CODEC)
        return data
    }

    fun findRecipe(fluidIn: Fluid, strainerStack: ItemStack): FilterRecipeData? {
        return this.sequence?.find { data ->
            data.match(fluidIn, strainerStack)
        }
    }
}