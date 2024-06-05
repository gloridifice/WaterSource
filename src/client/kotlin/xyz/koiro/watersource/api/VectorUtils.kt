package xyz.koiro.watersource.api

import org.joml.Vector3f

object VectorUtils {
    fun newX(x: Float = 1f) =
        Vector3f(x, 0f, 0f)

    fun newY(y: Float = 1f) =
        Vector3f(0f, y, 0f)

    fun newZ(z: Float = 1f) =
        Vector3f(0f, 0f, z)

    fun Vector3f.copy() = Vector3f(this)
}
