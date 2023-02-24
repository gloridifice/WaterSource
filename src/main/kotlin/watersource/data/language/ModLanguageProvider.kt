package watersource.data.language

import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.LanguageProvider
import watersource.data.language.ModLanguagesHandler.addLocalTransitions

class ModLanguageProvider(gen: DataGenerator, modid: String, locale: String) : LanguageProvider(gen, modid, locale) {
    var modLocale : String;
    init {
        modLocale = locale;
    }
    override fun addTranslations() {
        this.addLocalTransitions(this.modLocale)
    }
}