package xyz.koiro.watersource.event

import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.tooltip.TooltipBackgroundRenderer
import net.minecraft.util.ActionResult
import xyz.koiro.watersource.WSClientConfig
import xyz.koiro.watersource.api.WaterBallRenderer
import xyz.koiro.watersource.api.WaterLevelUiRenderUtils
import xyz.koiro.watersource.data.HydrationData
import xyz.koiro.watersource.data.HydrationDataManager
import xyz.koiro.watersource.world.effect.ModStatusEffects
import kotlin.math.ceil
import kotlin.math.max

object ItemTooltipEventHandlers {
    fun initialize() {
        ModClientEvents.DRAW_MOUSEOVER_TOOLTIP.register(renderItemTooltipHydrationData)
    }

    val renderItemTooltipHydrationData = ModClientEvents.DrawMouseoverTooltip { context, x, y, stack ->
        HydrationDataManager.findByItemStack(stack)?.let { hydrationData ->
            val isThirty = hydrationData.effects.any { it.effect.effectType == ModStatusEffects.THIRSTY }
            if (hydrationData.isDry()) {
                if (WSClientConfig.format.showHydrationDryItemTooltip)
                    drawDry(context, hydrationData, isThirty, x, y)
            } else {
                if (WSClientConfig.format.showHydrationRestorationItemTooltip)
                    drawRestoration(context, hydrationData, isThirty, x, y)
            }
        }
        ActionResult.PASS
    }

    fun drawDry(context: DrawContext, hydrationData: HydrationData, isThirty: Boolean, x: Int, y: Int) {
        val dryLevel = hydrationData.dryLevel!!

        val w = 42
        val h = 11
        val fX = x - w - 10
        val fY = y - h / 2

        context.draw {
            TooltipBackgroundRenderer.render(context, fX, fY, w, h, 400)
        }

        context.matrices.push()
        context.matrices.translate(0f, 0f, 401f)

        val levelY = fY + 1
        val newX = fX + 2
        WaterBallRenderer(newX, levelY, isThirty, WaterBallRenderer.Part.Empty).draw(context)
        WaterLevelUiRenderUtils.anticlockwiseSaturationParts.forEach {
            WaterBallRenderer(newX, levelY, isThirty, it, true).draw(context)
        }

        val tr = MinecraftClient.getInstance().textRenderer
        val textX = newX + 11
        context.drawText(tr, "-${String.format("%.2f", dryLevel.toFloat() / 4f)}", textX, levelY + 1, 0x888888, false)

        context.matrices.pop()
    }

    fun drawRestoration(context: DrawContext, hydrationData: HydrationData, isThirty: Boolean, x: Int, y: Int) {
        val level = hydrationData.level
        val saturation = hydrationData.saturation

        val levelCount = ceil(level.toFloat() / 2f).toInt()
        val isLevelEndHalf = level % 2 != 0
        val satCount = ceil(saturation.toFloat() / 4f).toInt()
        val satRest = saturation % 4
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
            // Empty
            for (i in 0..<emptyCount) {
                val newX = fX + i * 8 + 1
                WaterBallRenderer(newX, newY, isThirty, WaterBallRenderer.Part.Empty).draw(context)
            }

            // Level
            for (i in 0..<levelCount) {
                val newX = fX + i * 8 + 1
                val isEndAndHalf = i == levelCount - 1 && isLevelEndHalf
                val part = if (isEndAndHalf) WaterBallRenderer.Part.LeftHalf else WaterBallRenderer.Part.Full
                WaterBallRenderer(newX, newY, isThirty, part).draw(context)
            }

            // Saturation
            if (WSClientConfig.format.showHydrationSaturationInItemTooltip)
                for (i in 0..<satCount) {
                    val newX = fX + i * 8 + 1
                    val isEndAndRest = i == satCount - 1 && satRest != 0
                    if (isEndAndRest) {
                        for (j in 0..<satRest) {
                            WaterBallRenderer(
                                newX, newY, isThirty,
                                WaterLevelUiRenderUtils.clockwiseSaturationParts[j],
                                true
                            ).draw(context)
                        }
                    } else {
                        WaterLevelUiRenderUtils.clockwiseSaturationParts.forEach {
                            WaterBallRenderer(newX, newY, isThirty, it, true).draw(context)
                        }
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
            WaterBallRenderer(newX, levelY, isThirty, WaterBallRenderer.Part.Empty).draw(context)
            WaterBallRenderer(newX, levelY, isThirty, WaterBallRenderer.Part.Full).draw(context)
            WaterBallRenderer(newX, satY, isThirty, WaterBallRenderer.Part.Empty).draw(context)
            WaterLevelUiRenderUtils.clockwiseSaturationParts.forEach {
                WaterBallRenderer(newX, satY, isThirty, it, true).draw(context)
            }

            val tr = MinecraftClient.getInstance().textRenderer
            val textX = newX + 11
            context.drawText(tr, "+${String.format("%.1", level.toFloat() / 2f)}", textX, levelY + 1, 0xAAAAAA, false)
            context.drawText(
                tr,
                "+${String.format("%.2", saturation.toFloat() / 4f)}",
                textX,
                satY + 1,
                0xAAAAAA,
                false
            )

            context.matrices.pop()
        }
    }
}