package xyz.koiro.watersource.datagen

import xyz.koiro.watersource.ModItemGroups
import xyz.koiro.watersource.world.effect.ModStatusEffects

object ModLanguages {
    val INFO_CAPCITY_KYE = "watersource.info.capacity"
    val list = listOf(
        Translation("Water Source", "水源", ModItemGroups.MAIN_ITEM_GROUP_TRANSLATION_KEY),
        Translation("Thirsty", "口渴", ModStatusEffects.THIRSTY.translationKey),
        Translation("Capacity", "容量", INFO_CAPCITY_KYE),
    )

    data class Translation(
        val enUS: String,
        val zhCN: String,
        val key: String,
    )
}