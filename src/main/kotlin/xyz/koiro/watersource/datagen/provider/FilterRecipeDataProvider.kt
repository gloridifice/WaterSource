package xyz.koiro.watersource.datagen.provider

import com.google.common.hash.Hashing
import com.google.common.hash.HashingOutputStream
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
import net.minecraft.data.DataOutput
import net.minecraft.data.DataWriter
import net.minecraft.util.Identifier
import xyz.koiro.watersource.data.FilterRecipeData
import xyz.koiro.watersource.data.HydrationData
import xyz.koiro.watersource.data.ModResourceRegistries
import java.io.ByteArrayOutputStream
import java.nio.file.Path

abstract class FilterRecipeDataProvider(output: DataOutput) :
    ModDataProvider<FilterRecipeData>(output, ModResourceRegistries.FILTER_RECIPE_KEY, "Filter Recipe") {

    override fun writeData(id: Identifier, data: FilterRecipeData, path: Path, writer: DataWriter) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val hashingOutputStream = HashingOutputStream(Hashing.sha1(), byteArrayOutputStream)
        Json.encodeToStream(data.format(), hashingOutputStream)
        writer.write(path, byteArrayOutputStream.toByteArray(), hashingOutputStream.hash())
    }
}