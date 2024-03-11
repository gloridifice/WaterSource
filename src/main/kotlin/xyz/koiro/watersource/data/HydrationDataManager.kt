package xyz.koiro.watersource.data

import com.google.gson.Gson
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.fluid.Fluid
import net.minecraft.item.ItemStack
import net.minecraft.resource.ResourceManager
import net.minecraft.util.Identifier
import xyz.koiro.watersource.WaterSource

class HydrationDataManager: SimpleSynchronousResourceReloadListener{
    private val map = HashMap<String, HydrationData>()
    private var sequence: Sequence<HydrationData>? = null

    companion object{
        val SERVER = HydrationDataManager()
        val CLIENT = HydrationDataManager()
    }

    fun findByItemStack(itemStack: ItemStack): HydrationData?{
        return sequence?.find {
            it.match(itemStack)
        }
    }
    fun findByFluid(fluid: Fluid): HydrationData?{
        return sequence?.find {
            it.match(fluid)
        }
    }

    override fun reload(manager: ResourceManager) {
        val res = manager.findResources("water_level") { it.path.endsWith(".json") }
        WaterSource.LOGGER.info("Res Count: ${res.count()}")
        res.forEach { (id, resource) ->
            try {
                val stream = manager.getResource(id).get().inputStream
                val string = String(stream.readAllBytes())
                val format = Gson().fromJson(string, HydrationData.Format::class.java)
                val data = HydrationData(format)
                map.putIfAbsent(id.toString(), data)
                WaterSource.LOGGER.info("Successfully load water level data <${id}>")
            } catch (e: Exception){
                WaterSource.LOGGER.error("Error occurred while loading water level json <${id}>" )
            }
        }
        WaterSource.LOGGER.info("All water level data loaded. Count: ${map.count()}.")
        sequence = map.values.asSequence()
    }

    override fun getFabricId(): Identifier {
        return WaterSource.identifier("water_level")
    }
}