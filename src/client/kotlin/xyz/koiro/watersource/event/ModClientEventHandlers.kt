package xyz.koiro.watersource.event

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.tooltip.TooltipBackgroundRenderer
import net.minecraft.util.ActionResult
import xyz.koiro.watersource.api.DrawWaterBallUtils
import xyz.koiro.watersource.data.HydrationDataManager
import xyz.koiro.watersource.world.effect.ModStatusEffects
import kotlin.math.ceil
import kotlin.math.max

object ModClientEventHandlers {
    fun initialize() {
        ModClientEvents.DRAW_MOUSEOVER_TOOLTIP.register(renderItemTooltipHydrationData)
    }

    val renderItemTooltipHydrationData = ModClientEvents.DrawMouseoverTooltip { context, x, y, stack ->
        HydrationDataManager.INSTANCE.findByItemStack(stack)?.let { hydrationData ->
            val level = hydrationData.level
            val saturation = hydrationData.saturation
            val isThirty = hydrationData.effects.any { it.effectType == ModStatusEffects.THIRSTY }

            val levelCount = ceil(level.toFloat() / 2f).toInt()
            val isLevelEndHalf = level % 2 != 0
            val satCount = ceil(saturation.toFloat() / 2f).toInt()
            val isSatEndHalf = saturation % 2 != 0
            val emptyCount = max(levelCount, satCount)

            if (emptyCount <= 4) {
                val w = emptyCount * 8 + 2
                val h = 11
                val fX = x - w - 10
                val fY = y - h / 2

                context.draw {
                    TooltipBackgroundRenderer.render(context, fX, fY, w, h, 400)
                }

                context.matrices.push()
                context.matrices.translate(0f, 0f, 401f)

                val newY = fY + 1
                for (i in 0..<emptyCount) {
                    val newX = fX + i * 8 + 1
                    DrawWaterBallUtils(newX, newY, isThirty, DrawWaterBallUtils.Part.Empty).draw(context)
                }

                for (i in 0..<levelCount) {
                    val newX = fX + i * 8 + 1
                    val isEndAndHalf = i == levelCount - 1 && isLevelEndHalf
                    val part = if (isEndAndHalf) DrawWaterBallUtils.Part.LeftHalf else DrawWaterBallUtils.Part.Full
                    DrawWaterBallUtils(newX, newY, isThirty, part).draw(context)
                }

                for (i in 0..<satCount) {
                    val newX = fX + i * 8 + 1
                    val isEndAndHalf = i == satCount - 1 && isSatEndHalf
                    if (isEndAndHalf) {
                        DrawWaterBallUtils(newX, newY, isThirty, DrawWaterBallUtils.Part.DownFrame, true).draw(context)
                    } else {
                        DrawWaterBallUtils(newX, newY, isThirty, DrawWaterBallUtils.Part.UpFrame, true).draw(context)
                        DrawWaterBallUtils(newX, newY, isThirty, DrawWaterBallUtils.Part.DownFrame, true).draw(context)
                    }
                }

                context.matrices.pop()
            } else {
                val w = 32
                val h = 22
                val fX = x - w - 10
                val fY = y - h / 2

                context.draw {
                    TooltipBackgroundRenderer.render(context, fX, fY, w, h, 400)
                }

                context.matrices.push()
                context.matrices.translate(0f, 0f, 401f)

                val levelY = fY + 1
                val satY = levelY + 10
                val newX = fX + 2
                DrawWaterBallUtils(newX, levelY, isThirty, DrawWaterBallUtils.Part.Empty).draw(context)
                DrawWaterBallUtils(newX, levelY, isThirty, DrawWaterBallUtils.Part.LeftHalf).draw(context)
                DrawWaterBallUtils(newX, satY, isThirty, DrawWaterBallUtils.Part.Empty).draw(context)
                DrawWaterBallUtils(newX, satY, isThirty, DrawWaterBallUtils.Part.DownFrame, true).draw(context)

                val tr = MinecraftClient.getInstance().textRenderer
                val textX = newX + 11
                context.drawText(tr, "+${level}", textX, levelY + 1, 0xAAAAAA, false)
                context.drawText(tr, "+${saturation}", textX, satY + 1, 0xAAAAAA, false)

                context.matrices.pop()
            }
        }
        ActionResult.PASS
    }
}