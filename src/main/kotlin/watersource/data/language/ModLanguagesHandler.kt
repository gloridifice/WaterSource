package watersource.data.language

import net.minecraftforge.common.data.LanguageProvider
import watersource.world.level.block.ModBlocks

object ModLanguagesHandler {

    var translationUnits : ArrayList<TranslationUnit> = ArrayList()

    //在这里注册所有方块的语言信息
    fun init(){
        TranslationUnit(ModBlocks.WATER_FILTER_BLOCK).en_us("Water Filter").zh_cn("测试方块")
    }

    fun LanguageProvider.addLocalTransitions(locale: String){
        for (unit in translationUnits){
            add(unit.key, unit.getContent(locale))
        }
    }
}
