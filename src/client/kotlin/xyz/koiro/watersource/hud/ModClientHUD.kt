package xyz.koiro.watersource.hud

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import xyz.koiro.watersource.WaterSource
import xyz.koiro.watersource.world.attachment.ModAttachmentTypes
import xyz.koiro.watersource.world.effect.ModStatusEffects
import kotlin.math.ceil
import kotlin.random.Random

object ModClientHUD {
    val WATER_LEVEL_ICONS = WaterSource.identifier("textures/gui/hud/icons.png")
    private var elapsedTime: Float = 0f
    fun initialize() {
        HudRenderCallback.EVENT.register { context: DrawContext, deltaTime: Float ->
            val mc = MinecraftClient.getInstance()
            elapsedTime += deltaTime
            if (elapsedTime > 100000f) elapsedTime = 0f

            mc.profiler.push("waterLevel")
            drawWaterLevelHUD(context, elapsedTime)
            mc.profiler.pop()
        }
    }

    fun drawWaterLevelHUD(context: DrawContext, elapsedTime: Float){
        val mc = MinecraftClient.getInstance()
        val player = mc.player!!

        if (mc.options.hudHidden || mc.interactionManager?.hasStatusBars() != true) return

        val textRenderer = mc.textRenderer
        val random = Random.Default
        player.getAttached(ModAttachmentTypes.WATER_LEVEL)?.let { waterLevelData ->
            val level = waterLevelData.level
            val saturation = waterLevelData.saturation
            val offsetBetweenFoodBar = 10
            val y: Int = context.scaledWindowHeight - 39 - offsetBetweenFoodBar
            val xStart: Int = context.scaledWindowWidth / 2 + 91
            val isThirty = player.hasStatusEffect(ModStatusEffects.THIRSTY)

            val yOffsetList = if (saturation <= 0.0f && elapsedTime.toInt() % (level * 3 + 1) == 0) {
                List(10){
                    random.nextInt(3) - 1
                }
            } else List(10) { 0 }

            // Draw Empties
            for (i in 0..<10) {
                val x = xStart - i * 8 - 9
                DrawWaterBallUtils(x, y + yOffsetList[i], isThirty, DrawWaterBallUtils.Part.Empty).draw(context)
            }

            // Draw Balls (Level)
            val ballCount = ceil(level.toFloat() / 2f).toInt()
            val endIsHalf = level % 2 != 0
            for (i in 0..<ballCount) {
                val x = xStart - i * 8 - 9
                val isEndAndEndIsHalf = i == ballCount - 1 && endIsHalf
                val part =
                    if (isEndAndEndIsHalf) DrawWaterBallUtils.Part.RightHalf else DrawWaterBallUtils.Part.Full
                DrawWaterBallUtils(x, y + yOffsetList[i], isThirty, part).draw(context)
            }

            // Draw Frames (Saturation)
            val frameCount = ceil(saturation.toFloat() / 2f).toInt()
            val fEndIsHalf = saturation % 2 != 0
            for (i in 0..<frameCount) {
                val x = xStart - i * 8 - 9
                val isEndAndEndIsHalf = i == frameCount - 1 && fEndIsHalf
                val fY = y + yOffsetList[i]
                if (isEndAndEndIsHalf) {
                    DrawWaterBallUtils(x, fY, isThirty, DrawWaterBallUtils.Part.DownFrame).draw(context)
                } else {
                    DrawWaterBallUtils(x, fY, isThirty, DrawWaterBallUtils.Part.UpFrame).draw(context)
                    DrawWaterBallUtils(x, fY, isThirty, DrawWaterBallUtils.Part.DownFrame).draw(context)
                }
            }
        }
    }

    data class DrawWaterBallUtils(val x: Int, val y: Int, val isThirty: Boolean, val part: Part) {
        enum class Part(val u: Int) {
            Empty(0), Full(9), LeftHalf(18), RightHalf(27), UpFrame(36), DownFrame(45)
        }

        fun draw(context: DrawContext) {
            val y = when (part) {
                Part.UpFrame -> this.y - 1
                Part.DownFrame -> this.y + 1
                else -> this.y
            }
            context.drawTexture(WATER_LEVEL_ICONS, x, y, part.u, if (isThirty) 9 else 0, 9, 9)
        }
    }
}