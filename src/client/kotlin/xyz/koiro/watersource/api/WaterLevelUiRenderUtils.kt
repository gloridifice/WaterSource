package xyz.koiro.watersource.api

object WaterLevelUiRenderUtils {
    val anticlockwiseSaturationParts = listOf(
        WaterBallRenderer.Part.SaturationDown,
        WaterBallRenderer.Part.SaturationRight,
        WaterBallRenderer.Part.SaturationUp,
        WaterBallRenderer.Part.SaturationLeft,
    )

    val clockwiseSaturationParts = listOf(
        WaterBallRenderer.Part.SaturationDown,
        WaterBallRenderer.Part.SaturationLeft,
        WaterBallRenderer.Part.SaturationUp,
        WaterBallRenderer.Part.SaturationRight,
    )
}