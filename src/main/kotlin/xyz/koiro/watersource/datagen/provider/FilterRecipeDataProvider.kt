package xyz.koiro.watersource.datagen.provider

import com.google.common.hash.Hashing
import com.google.common.hash.HashingOutputStream
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
import net.minecraft.data.DataOutput
import net.minecraft.data.DataProvider
import net.minecraft.data.DataWriter
import net.minecraft.registry.RegistryEntryLookup.RegistryLookup
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import net.minecraft.util.Identifier
import xyz.koiro.watersource.data.FilterRecipeData
import xyz.koiro.watersource.data.HydrationData
import xyz.koiro.watersource.data.ModResourceRegistries
import java.io.ByteArrayOutputStream
import java.nio.file.Path
import java.util.concurrent.CompletableFuture

abstract class FilterRecipeDataProvider(output: DataOutput, lookup: CompletableFuture<WrapperLookup>) :
    ModDataProvider<FilterRecipeData>(output, ModResourceRegistries.FILTER_RECIPE_KEY, "Filter Recipe", lookup) {

    override fun writeData(
        id: Identifier,
        data: FilterRecipeData,
        path: Path,
        writer: DataWriter,
        lookup: WrapperLookup
    ): CompletableFuture<*> {
        return DataProvider.writeCodecToPath(writer, lookup, FilterRecipeData.CODEC, data, path);
    }
}