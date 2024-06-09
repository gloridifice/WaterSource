package xyz.koiro.watersource.render.hud

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.util.hit.BlockHitResult
import xyz.koiro.watersource.WSClientConfig
import xyz.koiro.watersource.api.WaterBallRenderer
import xyz.koiro.watersource.api.WaterLevelUiRenderUtils
import xyz.koiro.watersource.world.attachment.ModAttachmentTypes
import xyz.koiro.watersource.world.block.entity.FilterBlockEntity
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

            drawFilterInfo(context, mc)
        }
    }

    fun drawFilterInfo(context: DrawContext, mc: MinecraftClient) {
        val rct = mc.player?.raycast(3.0, 0.1f, false)
        val world = mc.world
        if (rct is BlockHitResult && world != null) {
            val entity = mc.world?.getBlockEntity(rct.blockPos);
            if (entity is FilterBlockEntity) {
                val strainer = entity.getStrainerStorage(world) ?: return
                context.drawText(mc.textRenderer, entity.fluidStorageData.getDisplayText(), 10, 10, 0xFFFFFF, false)
                context.drawText(mc.textRenderer, strainer.stack.name, 10, 20, 0xFFFFFF, false)
                context.drawText(mc.textRenderer, "Ticks: ${entity.filterTicks}, Working: ${entity.isWorking}", 10, 30, 0xFFFFFF, false)
            }
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

            context.matrices.push()
            context.matrices.translate(0f, 0f, 200f)
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
                val frameCount = ceil(saturation.toFloat() / 4f).toInt()
                val restPartCount = saturation % 4
                for (i in 0..<frameCount) {
                    val x = xStart - i * 8 - 9
                    val isEndAndEndIsRest = i == frameCount - 1 && restPartCount != 0
                    val fY = y + yOffsetList[i]
                    if (isEndAndEndIsRest) {
                        for (j in 0..<restPartCount) {
                            WaterBallRenderer(x, fY, isThirty, WaterLevelUiRenderUtils.anticlockwiseSaturationParts[j]).draw(context)
                        }
                    } else {
                        WaterLevelUiRenderUtils.anticlockwiseSaturationParts.forEach {
                            WaterBallRenderer(x, fY, isThirty, it).draw(context)
                        }
                    }
                }
            }
            context.matrices.pop()
        }
    }
}