package xyz.koiro.watersource.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import xyz.koiro.watersource.world.block.ModBlocks
import xyz.koiro.watersource.world.item.ModItems
import java.util.concurrent.CompletableFuture

class ModEnglishLangGenerator(dataOutput: FabricDataOutput ,lookup: CompletableFuture<WrapperLookup>) : FabricLanguageProvider(dataOutput,"en_us", lookup) {
    override fun generateTranslations(registryLookup: WrapperLookup?, translationBuilder: TranslationBuilder) {
        ModItems.reflectAutoGenDataItems().forEach {
            translationBuilder.add(it.first, it.second.enLang)
        }
        ModBlocks.reflectAutoGenDataBlocks().forEach {
            translationBuilder.add(it.first, it.second.enLang)
        }
        ModLanguages.list.forEach {
            translationBuilder.add(it.key, it.enUS)
        }
    }
}