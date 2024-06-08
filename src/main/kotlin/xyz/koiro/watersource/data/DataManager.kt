package xyz.koiro.watersource.data

import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.resource.Resource
import net.minecraft.resource.ResourceManager
import net.minecraft.util.Identifier
import xyz.koiro.watersource.WaterSource

abstract class DataManager<Data>(val startingPath: String, val id: Identifier): SimpleSynchronousResourceReloadListener {
    val map = HashMap<String, Data>()
    var sequence: Sequence<Data>? = null

    fun findResource(manager: ResourceManager): MutableMap<Identifier, Resource> {
        return manager.findResources(startingPath) { it.path.endsWith(".json") }
    }

    abstract fun processResourceToData(resId: Identifier, res: Resource): Data
//        val stream = res.inputStream
//        val string = String(stream.readAllBytes())
//        val format = Json.decodeFromString<HydrationData.Format>(string)
//        val data = HydrationData(format)

    override fun reload(manager: ResourceManager) {
        val res = findResource(manager)
        WaterSource.LOGGER.info("${id} - Resource count: ${res.count()}")
        res.forEach { (id, res) ->
            try {
                map.putIfAbsent(id.toString(), processResourceToData(id, res))
                WaterSource.LOGGER.info("Successfully load <${this.id}> data <${id}>")
            } catch (e: Exception) {
                WaterSource.LOGGER.error("Error occurred while loading <${this.id}> json <${id}>")
            }
        }
        WaterSource.LOGGER.info("All <${this.id}> data loaded. Count: ${map.count()}.")
        sequence = map.values.asSequence()
    }

    override fun getFabricId(): Identifier {
        return id
    }
}