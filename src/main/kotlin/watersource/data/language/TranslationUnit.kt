package watersource.data.language

import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

class TranslationUnit(var key: String) {
    private val translations : HashMap<String, String> = HashMap()
    constructor(item : Item) : this(item.getDescriptionId())
    constructor(block : Block) : this(block.getDescriptionId())
    init {
        ModLanguagesHandler.translationUnits.add(this)
    }
    fun zh_cn(content: String) : TranslationUnit{
        put("zh_cn", content)
        return this
    }
    fun en_us(content: String) : TranslationUnit{
        put("en_us", content)
        return this
    }
    fun put(locale : String, key: String){
        translations[locale] = key
    }
    fun getContent(locale : String) : String = translations[locale].toString()
}