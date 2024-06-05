package xyz.koiro.watersource

import kotlinx.serialization.Serializable
import xyz.koiro.watersource.config.ModConfigLoader

object WSClientConfig {
    var format: Format = Format()

    @Serializable
    class Format(
        val showHydrationRestorationDataOnItemTooltip: Boolean = true,
        val showHydrationDryDataOnItemTooltip: Boolean = true,
        val showWaterLevelBar: Boolean = true,
        val showWaterSaturationInBar: Boolean = true,
        val waterLevelBarOffsetY: Int = 0
    )

    fun reload() {
        format = ModConfigLoader.loadOrCreateConfig<Format>("client", Format())
    }
}