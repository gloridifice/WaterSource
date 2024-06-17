package xyz.koiro.watersource.datagen.provider

import net.minecraft.data.DataOutput
import net.minecraft.data.DataProvider
import net.minecraft.data.DataWriter
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import xyz.koiro.watersource.data.HydrationData
import xyz.koiro.watersource.data.ModResourceRegistries
import java.nio.file.Path
import java.util.concurrent.CompletableFuture

abstract class HydrationDataProvider(output: DataOutput, lookup: CompletableFuture<RegistryWrapper.WrapperLookup>) :
    ModDataProvider<HydrationData>(output, ModResourceRegistries.HYDRATION_KEY, "Hydration Data", lookup) {


    override fun writeData(
        id: Identifier,
        data: HydrationData,
        path: Path,
        writer: DataWriter,
        lookup: WrapperLookup
    ): CompletableFuture<*> {
        return DataProvider.writeCodecToPath(writer, lookup, HydrationData.CODEC, data, path);
    }

    companion object {
        fun dryItem(
            item: Item,
            dryLevel: Int,
            vararg effectInstance: HydrationData.StatusEffectObject
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
            vararg effectInstance: HydrationData.StatusEffectObject
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
            vararg effectInstance: HydrationData.StatusEffectObject
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
            vararg effectInstance: HydrationData.StatusEffectObject
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
            vararg effectInstance: HydrationData.StatusEffectObject
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