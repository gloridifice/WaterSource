package xyz.koiro.watersource.datagen.provider

import com.google.common.hash.Hashing
import com.google.common.hash.HashingOutputStream
import com.google.gson.stream.JsonWriter
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
import net.minecraft.data.DataOutput
import net.minecraft.data.DataProvider
import net.minecraft.data.DataWriter
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import xyz.koiro.watersource.WaterSource
import xyz.koiro.watersource.data.HydrationData
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets
import java.util.concurrent.CompletableFuture

abstract class HydrationDataProvider(val output: DataOutput) : DataProvider {
    override fun run(writer: DataWriter): CompletableFuture<*> {
        val dataMap = hashMapOf<Identifier, HydrationData>()
        addData(dataMap)
        val array = dataMap.map { (ident, data) ->
            val path = output.getResolver(DataOutput.OutputType.DATA_PACK, "water_level").resolveJson(ident)
            CompletableFuture.runAsync {
                try {
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    val hashingOutputStream = HashingOutputStream(Hashing.sha1(), byteArrayOutputStream)
                    Json.encodeToStream(data.format, hashingOutputStream)
                    writer.write(path, byteArrayOutputStream.toByteArray(), hashingOutputStream.hash())
                } catch (e: IOException) {
                    WaterSource.LOGGER.error("Failed to save file {}", path, e)
                }
            }
        }.toTypedArray()
        return (CompletableFuture.allOf(*array))
    }

    abstract fun addData(dataMap: HashMap<Identifier, HydrationData>)

    fun item(item: Item, level: Int, saturation: Int, vararg effectInstance: StatusEffectInstance): HydrationData {
        val effects = ArrayList(effectInstance.toList())
        return HydrationData(
            level,
            saturation,
            matchList = arrayListOf(HydrationData.ItemMatch(item)),
            effects = effects
        )
    }

    fun fluid(fluid: Fluid, level: Int, saturation: Int, vararg effectInstance: StatusEffectInstance): HydrationData {
        val effects = ArrayList(effectInstance.toList())
        return HydrationData(
            level, saturation, matchList = arrayListOf(HydrationData.FluidMatch(fluid)),
            effects = effects
        )
    }

    fun itemTag(
        tag: TagKey<Item>,
        level: Int,
        saturation: Int,
        vararg effectInstance: StatusEffectInstance
    ): HydrationData {
        val effects = ArrayList(effectInstance.toList())
        return HydrationData(
            level, saturation, matchList = arrayListOf(HydrationData.ItemTagMatch(tag)),
            effects = effects
        )
    }

    fun fluidTag(
        tag: TagKey<Fluid>,
        level: Int,
        saturation: Int,
        vararg effectInstance: StatusEffectInstance
    ): HydrationData {
        val effects = ArrayList(effectInstance.toList())
        return HydrationData(
            level, saturation, matchList = arrayListOf(HydrationData.FluidTagMatch(tag)),
            effects = effects
        )
    }

    override fun getName(): String {
        return "Hydration Data Provider"
    }
}