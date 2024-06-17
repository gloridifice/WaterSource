package xyz.koiro.watersource.api

import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.util.math.MatrixStack
import org.joml.Vector2f
import org.joml.Vector3f
import org.joml.Vector4f
import xyz.koiro.watersource.api.VectorUtils.copy

data class VertexInfo(
    val pos: Vector3f,
    val uv: Vector2f,
    val color: Vector4f,
)

fun VertexConsumer.quadFace(
    matrixStack: MatrixStack,
    center: Vector3f,
    size: Vector2f,
    uvMin: Vector2f,
    uvMax: Vector2f,
    normal: Vector3f,
    left: Vector3f,
    color: Int,
    alpha: Float
) {
    val normalN = normal.copy().normalize()
    val leftN = left.copy().normalize()
    val up = normalN.copy().cross(leftN)
    val halfLeft = leftN.copy().mul(size.x / 2.0f)
    val halfUp = up.copy().mul(size.y / 2.0f)
    val uMin = uvMin.x
    val vMin = uvMin.y
    val uMax = uvMax.x
    val vMax = uvMax.y
    val list = listOf(
        Pair(Vector3f(center).add(halfLeft).add(halfUp), Vector2f(uMin, vMin)),
        Pair(Vector3f(center).sub(halfLeft).add(halfUp), Vector2f(uMax, vMin)),
        Pair(Vector3f(center).sub(halfLeft).sub(halfUp), Vector2f(uMax, vMax)),
        Pair(Vector3f(center).add(halfLeft).sub(halfUp), Vector2f(uMin, vMax)),
    ).map { VertexInfo(it.first, it.second, Vector4f(ColorUtils.hexToRgb(color), alpha) ) }
    list.forEachIndexed() { i, it ->
        val pos = it.pos
        val u = it.uv.x
        val v = it.uv.y
        val color = it.color

        this.vertex(matrixStack.peek().positionMatrix, pos.x, pos.y, pos.z)
            .color(color.x, color.y, color.z, color.w)
            .overlay(OverlayTexture.DEFAULT_UV)
            .texture(u, v)
            .light(240)
            .normal(matrixStack.peek(), normalN.x, normalN.y, normalN.z).next()
    }
}