package xyz.koiro.watersource.api

import org.joml.Vector3f

object ColorUtils {
    fun hexToRgb(RGB: Int): Vector3f {
        val red: Float = ((RGB shr 16) and 0xFF) / 255f
        val green: Float = ((RGB shr 8) and 0xFF) / 255f
        val blue: Float = ((RGB shr 0) and 0xFF) / 255f
        return Vector3f(red, green, blue)
    }
}