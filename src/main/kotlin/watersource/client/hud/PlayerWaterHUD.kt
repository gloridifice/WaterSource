package watersource.client.hud

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiComponent
import net.minecraft.client.player.LocalPlayer
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Player
import watersource.WaterSource
import watersource.world.capability.PlayerWaterCapability.Companion.waterCap
import kotlin.math.floor
import kotlin.math.round


class PlayerWaterHUD : GuiComponent() {
    companion object {
        val OVERLAY_TEXTURE = ResourceLocation(WaterSource.ID, "textures/gui/hud/icons.png")
    }

    var tick = 0f

    fun render(poseStack: PoseStack, partialTicks: Float, screenWidth: Int, screenHeight: Int, player: LocalPlayer?) {
        tick += partialTicks

        if (player == null) return
        if (!player.waterCap().isPresent) return
        val waterCap = player.waterCap().orElse(null)

        poseStack.pushPose()
        RenderSystem.enableBlend()
        RenderSystem.setShaderTexture(0, OVERLAY_TEXTURE)
        val water = round(waterCap.waterLevel)
        val saturation = waterCap.saturationLevel
        val exhaustion = waterCap.exhaustionLevel

        val makeWayCount = 0
        val offsetX = screenWidth / 2 + 91
        val offsetY = screenHeight - 50 - makeWayCount * 10

        val size = 9
        val vNormal = 0
        val vThirst = 9
        val uFullBall = 0
        val uRightBall = 9
        val uLeftFrame = 27
        val uRightFrame = 36
        val uEmpty = 45

        val texV = vNormal


        var fullBallCount = water / 2
        var halfBallCount = water % 2
        var frameCount = saturation

        for (i in 9 downTo 0) {
            val x = offsetX + i * size
            val y = if (shouldShake(water, saturation)) {
                offsetY + (tick.toInt() + i + water.toInt()) % 3 - 1
            }else offsetY

            //render empty
            this.blit(poseStack, x, y, uEmpty, texV, size, size)

            //render water ball
            when {
                fullBallCount > 0 -> {
                    this.blit(poseStack, x, y, uFullBall, texV, size, size)
                    fullBallCount--
                }

                halfBallCount > 0 -> {
                    this.blit(poseStack, x, y, uRightBall, texV, size, size)
                    halfBallCount--
                }
            }

            //render saturation todo config
            if (frameCount > 0) {
                this.blit(poseStack, x, y, uRightFrame, texV, size, size)
                frameCount--
            }else break
            if (frameCount > 0) {
                this.blit(poseStack, x, y,uLeftFrame, texV, size, size)
                frameCount--
            }else break
        }

        poseStack.popPose()
    }
    fun shouldShake(water: Float, saturation: Float): Boolean{
       return saturation <= 0f && floor(tick).toInt() % (water.toInt() * 3 + 1) == 0
    }

}