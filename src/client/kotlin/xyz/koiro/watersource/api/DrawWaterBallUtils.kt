package xyz.koiro.watersource.api

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.DrawContext
import xyz.koiro.watersource.WaterSource
import xyz.koiro.watersource.hud.ModClientHUD
import kotlin.math.sin

data class DrawWaterBallUtils(
    val x: Int,
    val y: Int,
    val isThirty: Boolean,
    val part: Part,
    val twinkle: Boolean = false
) {
    companion object {
        val WATER_LEVEL_ICONS = WaterSource.identifier("textures/gui/hud/icons.png")
    }

    enum class Part(val u: Int) {
        Empty(0), Full(9), LeftHalf(18), RightHalf(27), UpFrame(36), DownFrame(45)
    }

    fun draw(context: DrawContext) {
        val y = when (part) {
            Part.UpFrame -> this.y - 1
            Part.DownFrame -> this.y + 1
            else -> this.y
        }

        val draw = { context.drawTexture(WATER_LEVEL_ICONS, x, y, part.u, if (isThirty) 9 else 0, 9, 9) }

        RenderSystem.enableBlend()
        if (twinkle) {
            val alpha = (sin(ModClientHUD.elapsedTime / 12f) + 1) * 0.35f + 0.3f
            RenderSystem.setShaderColor(1f, 1f, 1f, alpha)
            draw()
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
        } else {
            draw()
        }
        RenderSystem.disableBlend()
    }
}