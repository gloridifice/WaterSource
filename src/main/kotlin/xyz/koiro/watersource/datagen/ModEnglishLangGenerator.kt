package xyz.koiro.watersource.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import xyz.koiro.watersource.world.item.ModItems

class ModEnglishLangGenerator(dataOutput: FabricDataOutput) : FabricLanguageProvider(dataOutput, "en_us") {
    override fun generateTranslations(translationBuilder: TranslationBuilder) {
        ModItems.reflectAutoGenDataItems().forEach {
            translationBuilder.add(it.first, it.second.enLang)
        }
        ModLanguages.list.forEach {
            translationBuilder.add(it.key, it.enUS)
        }
    }
}