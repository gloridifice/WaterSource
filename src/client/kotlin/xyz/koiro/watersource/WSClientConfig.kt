package xyz.koiro.watersource

import kotlinx.serialization.SerialInfo
import kotlinx.serialization.Serializable

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
}