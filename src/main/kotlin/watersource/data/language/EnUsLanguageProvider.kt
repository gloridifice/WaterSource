package watersource.data.language

import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.LanguageProvider
import watersource.world.level.block.ModBlocks

class EnUsLanguageProvider(gen: DataGenerator, modid: String, locale: String) : LanguageProvider(gen, modid, locale) {
    override fun addTranslations() {
        this.add(ModBlocks.WATER_FILTER_BLOCK, "Test Block")
    }
}