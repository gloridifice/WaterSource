package xyz.koiro.watersource.datagen.provider

import com.google.common.hash.Hashing
import com.google.common.hash.HashingOutputStream
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
import xyz.koiro.watersource.WaterSource
import xyz.koiro.watersource.data.HydrationData
import xyz.koiro.watersource.data.ModResourceRegistries
import xyz.koiro.watersource.identifier
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.file.Path
import java.util.concurrent.CompletableFuture

abstract class HydrationDataProvider(output: DataOutput) :
    ModDataProvider<HydrationData>(output, ModResourceRegistries.HYDRATION_KEY, "Hydration Data") {

    override fun writeData(id: Identifier, data: HydrationData, path: Path, writer: DataWriter) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val hashingOutputStream = HashingOutputStream(Hashing.sha1(), byteArrayOutputStream)
        Json.encodeToStream(data.format(), hashingOutputStream)
        writer.write(path, byteArrayOutputStream.toByteArray(), hashingOutputStream.hash())
    }

    companion object {
        fun dryItem(
            item: Item,
            dryLevel: Int,
            vararg effectInstance: HydrationData.ProbabilityStatusEffectInstance
        ): HydrationData {
            val effects = ArrayList(effectInstance.toList())
            return HydrationData(
                0,
                0,
                dryLevel,
                matchList = arrayListOf(HydrationData.ItemMatch(item)),
                effects = effects
            )
        }

        fun item(
            item: Item,
            level: Int,
            saturation: Int,
            vararg effectInstance: HydrationData.ProbabilityStatusEffectInstance
        ): HydrationData {
            val effects = ArrayList(effectInstance.toList())
            return HydrationData(
                level,
                saturation,
                null,
                matchList = arrayListOf(HydrationData.ItemMatch(item)),
                effects = effects
            )
        }

        fun fluid(
            fluid: Fluid,
            level: Int,
            saturation: Int,
            vararg effectInstance: HydrationData.ProbabilityStatusEffectInstance
        ): HydrationData {
            val effects = ArrayList(effectInstance.toList())
            return HydrationData(
                level, saturation, null, matchList = arrayListOf(HydrationData.FluidMatch(fluid)),
                effects = effects
            )
        }

        fun itemTag(
            tag: TagKey<Item>,
            level: Int,
            saturation: Int,
            vararg effectInstance: HydrationData.ProbabilityStatusEffectInstance
        ): HydrationData {
            val effects = ArrayList(effectInstance.toList())
            return HydrationData(
                level, saturation, null, matchList = arrayListOf(HydrationData.ItemTagMatch(tag)),
                effects = effects
            )
        }

        fun fluidTag(
            tag: TagKey<Fluid>,
            level: Int,
            saturation: Int,
            vararg effectInstance: HydrationData.ProbabilityStatusEffectInstance
        ): HydrationData {
            val effects = ArrayList(effectInstance.toList())
            return HydrationData(
                level, saturation, null, matchList = arrayListOf(HydrationData.FluidTagMatch(tag)),
                effects = effects
            )
        }
    }

    override fun getName(): String {
        return "Hydration Data Provider"
    }
}