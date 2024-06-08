package xyz.koiro.watersource.datagen.provider

import net.minecraft.data.DataOutput
import net.minecraft.data.DataProvider
import net.minecraft.data.DataWriter
import net.minecraft.util.Identifier
import xyz.koiro.watersource.WaterSource
import java.io.IOException
import java.nio.file.Path
import java.util.concurrent.CompletableFuture

abstract class ModDataProvider<D>(val output: DataOutput, val directoryPath: String, val providerName: String) : DataProvider {
    class DataAdder<D> {
        val dataMap = hashMapOf<Identifier, D>()

        fun add(identifier: Identifier, data: D) {
            dataMap[identifier] = data
        }
    }

    abstract fun writeData(id: Identifier, data: D, path: Path, writer: DataWriter)
    abstract fun addData(adder: DataAdder<D>)

    override fun run(writer: DataWriter): CompletableFuture<*> {
        val dataAdder = DataAdder<D>()
        addData(dataAdder)
        val array = dataAdder.dataMap.map { (ident, data) ->
            val path = output.getResolver(DataOutput.OutputType.DATA_PACK, directoryPath).resolveJson(ident)
            CompletableFuture.runAsync {
                try {
                    writeData(ident, data, path, writer)
                } catch (e: IOException) {
                    WaterSource.LOGGER.error("Failed to save file {}", path, e)
                }
            }
        }.toTypedArray()
        return (CompletableFuture.allOf(*array))
    }

    override fun getName(): String {
        return this.providerName
    }
}