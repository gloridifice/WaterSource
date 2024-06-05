package xyz.koiro.watersource.api

import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.util.math.MatrixStack
import org.joml.Vector2f
import org.joml.Vector3f
import xyz.koiro.watersource.api.VectorUtils.copy
import xyz.koiro.watersource.api.VectorUtils.newX
import xyz.koiro.watersource.api.VectorUtils.newY
import xyz.koiro.watersource.api.VectorUtils.newZ

object FluidCubeRenderUtils {
    fun VertexConsumer.fluidCube(
        matrixStack: MatrixStack,
        center: Vector3f,
        size: Vector3f,
        unitSizeOfTex: Vector3f,
        minUV: Vector2f,
        maxUV: Vector2f,
        color: Int,
        alpha: Float
    ) {
        val vRatioXYZ = size.copy().div(unitSizeOfTex)
        fun drawPairFace(
            offset: Vector3f,
            faceSize: Vector2f,
            uvRatio: Vector2f,
            faceLeft: (normal: Vector3f) -> Vector3f
        ) {
            val faceNormal = offset.copy().normalize()
            val uH = (maxUV.x - minUV.x) * (1 - uvRatio.x)
            val vH = (maxUV.y - minUV.y) * (1 - uvRatio.y)

            this.quadFace(
                matrixStack,
                center.copy().add(offset.copy()),
                faceSize,
                //Vector2f(if (offset.x != 0f) size.z else size.x, size.y),
                Vector2f(minUV.x + uH, minUV.y + vH),
                maxUV,
                faceNormal.copy(),
                faceLeft(faceNormal),
                color,
                alpha
            )

            this.quadFace(
                matrixStack,
                center.copy().add(offset.copy().negate()),
                faceSize,
                Vector2f(minUV.x + uH, minUV.y + vH),
                maxUV,
                faceNormal.copy().negate(),
                faceLeft(faceNormal).negate(),
                color,
                alpha
            )
        }
        drawPairFace(newX(size.x / 2f), Vector2f(size.z, size.y), Vector2f(1f, vRatioXYZ.y)) {
            newY().cross(it).normalize()
        }
        drawPairFace(newZ(size.z / 2f), Vector2f(size.x, size.y), Vector2f(1f, vRatioXYZ.y)) {
            newY().cross(it).normalize()
        }
        drawPairFace(newY(size.y / 2f), Vector2f(size.x, size.z), Vector2f(vRatioXYZ.x, vRatioXYZ.z)) {
            newZ().cross(it).normalize()
        }
    }
}