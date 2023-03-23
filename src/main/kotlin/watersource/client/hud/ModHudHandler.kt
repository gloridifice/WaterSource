package watersource.client.hud

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiComponent
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.RenderGuiOverlayEvent
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import watersource.WaterSource

@Mod.EventBusSubscriber(modid = WaterSource.ID, value = [Dist.CLIENT])
object ModHudHandler {
    val PLAYER_WATER_HUD = PlayerWaterHUD()

    val mc = Minecraft.getInstance()
    @SubscribeEvent(receiveCanceled = true)
    fun onRenderOverlay(event: RenderGuiOverlayEvent.Post){
        val screenHeight = event.window.screenHeight
        val screenWidth = event.window.screenWidth

        if (event.overlay == VanillaGuiOverlay.FOOD_LEVEL.type()){

            GuiComponent.drawString(event.poseStack, mc.font, "111",screenWidth/2,screenHeight/2, 0)
            //render player water
            if (mc.level != null && !mc.options.hideGui){
                PLAYER_WATER_HUD.render(event.poseStack, event.partialTick, screenWidth, screenHeight, mc.player)
            }
        }
    }
}