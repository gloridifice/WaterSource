package xyz.koiro.watersource.hud

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import xyz.koiro.watersource.attechment.ModAttachmentTypes

object ModClientHUD {
    fun initialize(){
        HudRenderCallback.EVENT.register{ drawContext: DrawContext, deltaTime: Float ->
            val mc = MinecraftClient.getInstance()
            val player = mc.player!!
            val textRenderer = mc.textRenderer
            player.getAttached(ModAttachmentTypes.WATER_LEVEL)?.let {
                drawContext.drawText(textRenderer,
                    "Water Level: ${it.level}/${it.maxLevel}, Saturation: ${it.saturation}/${it.maxSaturation}, " +
                            "Exhaustion: ${it.exhaustion}/${it.maxExhaustion}", 10, 10, 0xFFFFFF, false)
            }
        }
    }
}