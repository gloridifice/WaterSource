package xyz.koiro.watersource.datagen

import xyz.koiro.watersource.ModCommands
import xyz.koiro.watersource.ModItemGroups
import xyz.koiro.watersource.world.effect.ModStatusEffects
import xyz.koiro.watersource.world.enchantment.ModEnchantments

object ModLanguages {
    const val INFO_CAPACITY_KYE = "watersource.info.capacity"
    val list = listOf(
        Translation("Water Source", "水源", ModItemGroups.MAIN_ITEM_GROUP_TRANSLATION_KEY),
        Translation("Thirsty", "口渴", ModStatusEffects.THIRSTY.translationKey),
        Translation("Capacity", "容量", INFO_CAPACITY_KYE),
        Translation("Moisturizing", "保湿", ModEnchantments.MOISTURIZING.translationKey),

        // Commands
        Translation("<restoreAll> command's target must be player!", "<restoreAll> 命令的目标只能是玩家", ModCommands.RESTORE_ALL_TARGET_ERROR_TRANS_KEY),
    )

    data class Translation(
        val enUS: String,
        val zhCN: String,
        val key: String,
    )
}