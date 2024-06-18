package xyz.koiro.watersource.config

import com.akuleshov7.ktoml.Toml
import com.akuleshov7.ktoml.TomlInputConfig
import com.akuleshov7.ktoml.source.decodeFromStream
import kotlinx.serialization.encodeToString
import net.fabricmc.loader.api.FabricLoader
import java.nio.file.Path
import java.nio.file.Paths

object ModConfigLoader {
    fun getConfigDir(): Path {
        return FabricLoader.getInstance().configDir
    }

    inline fun <reified T> loadOrCreateConfig(path: String, defaultConfig: T): T {
        val inputConfig = TomlInputConfig(
            ignoreUnknownNames = true
        )
        val dir = getConfigDir()
        val filePath = Paths.get(dir.toString(), "watersource", "${path}.toml").toFile()
        if (filePath.exists() && filePath.isFile) {
            val stream = filePath.inputStream()
            val format = Toml(inputConfig).decodeFromStream<T>(stream)
            return format
        } else {
            val it = Paths.get(dir.toString(), "watersource").toFile()
            if (!it.exists()) {
                it.mkdirs()
            }
            if (filePath.createNewFile())
                filePath.writeText(Toml().encodeToString<T>(defaultConfig))
        }
        return defaultConfig
    }
}