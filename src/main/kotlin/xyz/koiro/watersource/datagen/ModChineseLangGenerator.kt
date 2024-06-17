package xyz.koiro.watersource.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import xyz.koiro.watersource.world.block.ModBlocks
import xyz.koiro.watersource.world.item.ModItems
import java.util.concurrent.CompletableFuture

class ModChineseLangGenerator(dataOutput: FabricDataOutput, lookup: CompletableFuture<WrapperLookup>) : FabricLanguageProvider(dataOutput,"zh_cn", lookup) {
    override fun generateTranslations(
        registryLookup: RegistryWrapper.WrapperLookup?,
        translationBuilder: TranslationBuilder
    ) {
        ModItems.reflectAutoGenDataItems().forEach {
            translationBuilder.add(it.first, it.second.cnLang)
        }
        ModBlocks.reflectAutoGenDataBlocks().forEach {
            translationBuilder.add(it.first, it.second.cnLang)
        }
        ModLanguages.list.forEach {
            translationBuilder.add(it.key, it.zhCN)
        }
    }
}