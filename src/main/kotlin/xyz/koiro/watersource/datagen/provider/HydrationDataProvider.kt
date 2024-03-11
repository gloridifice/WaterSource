package xyz.koiro.watersource.datagen.provider

import com.google.gson.Gson
import net.minecraft.data.DataOutput
import net.minecraft.data.DataProvider
import net.minecraft.data.DataWriter
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import xyz.koiro.watersource.data.HydrationData
import java.util.concurrent.CompletableFuture

abstract class HydrationDataProvider(val output: DataOutput) : DataProvider {
    override fun run(writer: DataWriter): CompletableFuture<*> {
        val dataMap = hashMapOf<Identifier, HydrationData>()
        val gson = Gson()
        addData(dataMap)
        val array = dataMap.map { (ident, data) ->
            val path = output.getResolver(DataOutput.OutputType.DATA_PACK, "water_level").resolveJson(ident)
            val json = gson.toJsonTree(data.format)
            DataProvider.writeToPath(writer, json, path)
        }.toTypedArray()
        return (CompletableFuture.allOf(*array))
    }

    abstract fun addData(dataMap: HashMap<Identifier, HydrationData>)

    fun item(item: Item, level: Int, saturation: Int): HydrationData{
        return HydrationData(level, saturation, matchList = arrayListOf(HydrationData.ItemMatch(item)))
    }

    fun fluid(fluid: Fluid, level: Int, saturation: Int): HydrationData{
        return HydrationData(level, saturation, matchList = arrayListOf(HydrationData.FluidMatch(fluid)))
    }

    fun itemTag(tag: TagKey<Item>, level: Int, saturation: Int): HydrationData{
        return HydrationData(level, saturation, matchList = arrayListOf(HydrationData.ItemTagMatch(tag)))
    }

    fun fluidTag(tag: TagKey<Fluid>, level: Int, saturation: Int): HydrationData{
        return HydrationData(level, saturation, matchList = arrayListOf(HydrationData.FluidTagMatch(tag)))
    }
    override fun getName(): String {
        return "Hydration Data Provider"
    }
}