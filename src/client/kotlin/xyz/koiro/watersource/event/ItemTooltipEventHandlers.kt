package xyz.koiro.watersource.event

import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.tooltip.TooltipBackgroundRenderer
import net.minecraft.util.ActionResult
import xyz.koiro.watersource.WSClientConfig
import xyz.koiro.watersource.api.WaterBallRenderer
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
                if (WSClientConfig.format.showHydrationDryDataOnItemTooltip)
                    drawDry(context, hydrationData, isThirty, x, y)
            } else {
                if (WSClientConfig.format.showHydrationRestorationDataOnItemTooltip)
                    drawRestoration(context, hydrationData, isThirty, x, y)
            }
        }
        ActionResult.PASS
    }

    fun drawDry(context: DrawContext, hydrationData: HydrationData, isThirty: Boolean, x: Int, y: Int) {
        val dryLevel = hydrationData.dryLevel!!

        val w = 32
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
        WaterBallRenderer(newX, levelY, isThirty, WaterBallRenderer.Part.DryDown, true).draw(context)

        val tr = MinecraftClient.getInstance().textRenderer
        val textX = newX + 11
        context.drawText(tr, "-${dryLevel}", textX, levelY + 1, 0x888888, false)

        context.matrices.pop()
    }

    fun drawRestoration(context: DrawContext, hydrationData: HydrationData, isThirty: Boolean, x: Int, y: Int) {
        val level = hydrationData.level
        val saturation = hydrationData.saturation

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
                WaterBallRenderer(newX, newY, isThirty, WaterBallRenderer.Part.Empty).draw(context)
            }

            for (i in 0..<levelCount) {
                val newX = fX + i * 8 + 1
                val isEndAndHalf = i == levelCount - 1 && isLevelEndHalf
                val part = if (isEndAndHalf) WaterBallRenderer.Part.LeftHalf else WaterBallRenderer.Part.Full
                WaterBallRenderer(newX, newY, isThirty, part).draw(context)
            }

            for (i in 0..<satCount) {
                val newX = fX + i * 8 + 1
                val isEndAndHalf = i == satCount - 1 && isSatEndHalf
                if (isEndAndHalf) {
                    WaterBallRenderer(newX, newY, isThirty, WaterBallRenderer.Part.SaturationDown, true).draw(
                        context
                    )
                } else {
                    WaterBallRenderer(newX, newY, isThirty, WaterBallRenderer.Part.SaturationUp, true).draw(
                        context
                    )
                    WaterBallRenderer(newX, newY, isThirty, WaterBallRenderer.Part.SaturationDown, true).draw(
                        context
                    )
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
            WaterBallRenderer(newX, levelY, isThirty, WaterBallRenderer.Part.LeftHalf).draw(context)
            WaterBallRenderer(newX, satY, isThirty, WaterBallRenderer.Part.Empty).draw(context)
            WaterBallRenderer(newX, satY, isThirty, WaterBallRenderer.Part.SaturationDown, true).draw(context)

            val tr = MinecraftClient.getInstance().textRenderer
            val textX = newX + 11
            context.drawText(tr, "+${level}", textX, levelY + 1, 0xAAAAAA, false)
            context.drawText(tr, "+${saturation}", textX, satY + 1, 0xAAAAAA, false)

            context.matrices.pop()
        }
    }
}