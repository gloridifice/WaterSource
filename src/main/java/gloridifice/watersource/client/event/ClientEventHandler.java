package gloridifice.watersource.client.event;

import com.mojang.blaze3d.systems.RenderSystem;
import gloridifice.watersource.WaterSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = WaterSource.MODID)
public class ClientEventHandler {
    @SubscribeEvent
    public static void onAirRenderPre(RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.AIR) {
            int translateY = -11;
            RenderSystem.translated(0, translateY, 0);
        }
    }
    @SubscribeEvent
    public static void onAirRenderPost(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.AIR) {
            int translateY = 11;
            RenderSystem.translated(0, translateY, 0);
        }
    }
}
