package xyz.koiro.watersource.data

import com.mojang.brigadier.StringReader
import kotlinx.serialization.json.Json
import net.minecraft.fluid.Fluid
import net.minecraft.item.ItemStack
import net.minecraft.registry.BuiltinRegistries
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import net.minecraft.resource.Resource
import net.minecraft.util.Identifier
import net.minecraft.util.JsonReaderUtils
import xyz.koiro.watersource.WSConfig
import xyz.koiro.watersource.WaterSource
import xyz.koiro.watersource.api.storage.getOrCreateFluidStorageData

object HydrationDataManager :
    DataManager<HydrationData>(
        ModResourceRegistries.HYDRATION_KEY,
        WaterSource.identifier(ModResourceRegistries.HYDRATION_KEY)
    ) {
    val registryLookup = BuiltinRegistries.createWrapperLookup()

    fun findByItemStack(itemStack: ItemStack): HydrationData? {
        val data = sequence?.find {
            it.match(itemStack)
        } ?: itemStack.getOrCreateFluidStorageData()?.let { data -> sequence?.find { it.match(data.fluid) } }
        if (!WSConfig.format.enableDryFeature && data?.isDry() == true) return null
        return data ?: WSConfig.getDefaultFoodDryData(itemStack)
    }

    fun findByFluid(fluid: Fluid): HydrationData? {
        return sequence?.find {
            it.match(fluid)
        }
    }

    override fun processResourceToData(resId: Identifier, res: Resource): HydrationData {
        val reader = StringReader(String(res.inputStream.readAllBytes()))
        val data = JsonReaderUtils.parse(registryLookup, reader, HydrationData.CODEC)
        return data
    }

    override fun getFabricId(): Identifier {
        return WaterSource.identifier(ModResourceRegistries.HYDRATION_KEY)
    }
}