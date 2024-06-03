package xyz.koiro.watersource.config

import com.akuleshov7.ktoml.Toml
import com.akuleshov7.ktoml.source.decodeFromStream
import kotlinx.serialization.encodeToString
import net.fabricmc.loader.api.FabricLoader
import xyz.koiro.watersource.WSConfig
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.createFile
import kotlin.io.path.createParentDirectories
import kotlin.io.path.exists

object ModConfigLoader {
    fun getConfigDir(): Path {
        return FabricLoader.getInstance().configDir
    }

    inline fun <reified T> loadOrCreateConfig(path: String, defaultConfig: T): T {
        val dir = getConfigDir()
        val filePath = Paths.get(dir.toString(), "watersource", "${path}.toml")
        if (filePath.exists() && filePath.toFile().isFile) {
            val stream = filePath.toFile().inputStream()
            val format = Toml.decodeFromStream<T>(stream)
            return format
        } else {
            filePath.createParentDirectories()
            filePath.createFile()
            filePath.toFile().writeText(Toml.encodeToString<T>(defaultConfig))
        }
        return defaultConfig
    }
}