package xyz.koiro.watersource.data

import kotlinx.serialization.json.Json
import net.minecraft.fluid.Fluid
import net.minecraft.item.ItemStack
import net.minecraft.resource.Resource
import net.minecraft.util.Identifier
import xyz.koiro.watersource.WaterSource
import xyz.koiro.watersource.api.storage.getOrCreateFluidStorageData
import xyz.koiro.watersource.datagen.provider.HydrationDataProvider
import kotlin.math.floor

object HydrationDataManager :
    DataManager<HydrationData>(ModResourceRegistries.HYDRATION_KEY, WaterSource.identifier(ModResourceRegistries.HYDRATION_KEY)) {


    fun findByItemStack(itemStack: ItemStack): HydrationData? {
        val data = sequence?.find {
            it.match(itemStack)
        } ?: itemStack.getOrCreateFluidStorageData()?.let { data -> sequence?.find { it.match(data.fluid) } }
        return data
            ?: if (itemStack.isFood) {
                itemStack.item.foodComponent?.hunger?.let {
                    if (it > 1) {
                        HydrationDataProvider.dryItem(itemStack.item, floor(it / 2f).toInt())
                    } else null
                }
            } else null
    }

    fun findByFluid(fluid: Fluid): HydrationData? {
        return sequence?.find {
            it.match(fluid)
        }
    }

    override fun processResourceToData(resId: Identifier, res: Resource): HydrationData {
        val stream = res.inputStream
        val string = String(stream.readAllBytes())
        val format = Json.decodeFromString<HydrationData.Format>(string)
        val data = HydrationData(format)
        return data
    }

    override fun getFabricId(): Identifier {
        return WaterSource.identifier(ModResourceRegistries.HYDRATION_KEY)
    }
}