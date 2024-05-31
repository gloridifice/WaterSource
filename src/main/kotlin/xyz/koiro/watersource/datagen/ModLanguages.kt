package xyz.koiro.watersource.datagen

import xyz.koiro.watersource.ModItemGroups

object ModLanguages {
    val list = listOf(
        Translation("Water Source", "水源", ModItemGroups.MAIN_ITEM_GROUP_TRANSLATION_KEY),
    )

    data class Translation(
        val enUS: String,
        val zhCN: String,
        val key: String,
    )
}