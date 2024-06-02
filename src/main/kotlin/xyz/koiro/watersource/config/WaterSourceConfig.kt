package xyz.koiro.watersource.config

import com.akuleshov7.ktoml.Toml
import com.akuleshov7.ktoml.source.decodeFromStream
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.Serializer
import kotlinx.serialization.encodeToString
import net.fabricmc.loader.api.FabricLoader
import xyz.koiro.watersource.WSConfig
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.createFile
import kotlin.io.path.createParentDirectories
import kotlin.io.path.exists

object WaterSourceConfig {
    fun getConfigDir(): Path {
        return FabricLoader.getInstance().configDir
    }

    fun loadConfig() {
        val dir = getConfigDir()
        val filePath = Paths.get(dir.toString(), "watersource", "common.toml")
        if (filePath.exists() && filePath.toFile().isFile) {
            val stream = filePath.toFile().inputStream()
            val format = Toml.decodeFromStream<WSConfig.Format>(stream)
            WSConfig.format = format
        } else {
            filePath.createParentDirectories()
            filePath.createFile()
            filePath.toFile().writeText(Toml.encodeToString<WSConfig.Format>(WSConfig.Format()))
        }
    }
}