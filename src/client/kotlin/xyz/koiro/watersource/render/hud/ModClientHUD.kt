package xyz.koiro.watersource.render.hud

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import xyz.koiro.watersource.WSClientConfig
import xyz.koiro.watersource.api.WaterBallRenderer
import xyz.koiro.watersource.world.attachment.ModAttachmentTypes
import xyz.koiro.watersource.world.effect.ModStatusEffects
import kotlin.math.ceil
import kotlin.random.Random

object ModClientHUD {
    var elapsedTime: Float = 0f
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

    fun drawWaterLevelHUD(context: DrawContext, elapsedTime: Float) {
        val mc = MinecraftClient.getInstance()
        val player = mc.player!!

        if (mc.options.hudHidden || mc.interactionManager?.hasStatusBars() != true) return
        if (!WSClientConfig.format.showWaterLevelBar) return

        val textRenderer = mc.textRenderer
        val random = Random.Default
        player.getAttached(ModAttachmentTypes.WATER_LEVEL)?.let { waterLevelData ->
            val level = waterLevelData.level
            val saturation = waterLevelData.saturation
            val offsetBetweenFoodBar = 10 + WSClientConfig.format.waterLevelBarOffsetY
            var y: Int = context.scaledWindowHeight - 39 - offsetBetweenFoodBar
            if (player.isSubmergedInWater || player.air < player.maxAir) y -= 10
            val xStart: Int = context.scaledWindowWidth / 2 + 91
            val isThirty = player.hasStatusEffect(ModStatusEffects.THIRSTY)

            val yOffsetList = if (saturation <= 0.0f && elapsedTime.toInt() % (level * 3 + 1) == 0) {
                List(10) {
                    random.nextInt(3) - 1
                }
            } else List(10) { 0 }

            // Draw Empties
            for (i in 0..<10) {
                val x = xStart - i * 8 - 9
                WaterBallRenderer(x, y + yOffsetList[i], isThirty, WaterBallRenderer.Part.Empty).draw(context)
            }

            // Draw Balls (Level)
            val ballCount = ceil(level.toFloat() / 2f).toInt()
            val endIsHalf = level % 2 != 0
            for (i in 0..<ballCount) {
                val x = xStart - i * 8 - 9
                val isEndAndEndIsHalf = i == ballCount - 1 && endIsHalf
                val part =
                    if (isEndAndEndIsHalf) WaterBallRenderer.Part.RightHalf else WaterBallRenderer.Part.Full
                WaterBallRenderer(x, y + yOffsetList[i], isThirty, part).draw(context)
            }

            if (WSClientConfig.format.showWaterSaturationInBar) {
                // Draw Frames (Saturation)
                val frameCount = ceil(saturation.toFloat() / 2f).toInt()
                val fEndIsHalf = saturation % 2 != 0
                for (i in 0..<frameCount) {
                    val x = xStart - i * 8 - 9
                    val isEndAndEndIsHalf = i == frameCount - 1 && fEndIsHalf
                    val fY = y + yOffsetList[i]
                    if (isEndAndEndIsHalf) {
                        WaterBallRenderer(x, fY, isThirty, WaterBallRenderer.Part.SaturationDown).draw(context)
                    } else {
                        WaterBallRenderer(x, fY, isThirty, WaterBallRenderer.Part.SaturationUp).draw(context)
                        WaterBallRenderer(x, fY, isThirty, WaterBallRenderer.Part.SaturationDown).draw(context)
                    }
                }
            }
        }
    }
}