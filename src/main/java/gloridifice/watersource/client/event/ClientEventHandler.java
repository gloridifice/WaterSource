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
    public static void onRenderGameOverlayEvent(RenderGameOverlayEvent.Pre event){
        if (event.getType() == RenderGameOverlayEvent.ElementType.AIR){
            RenderSystem.translated(0,-12,0);
        }
    }
    @SubscribeEvent
    public static void onRenderGameOverlayEvent(RenderGameOverlayEvent.Post event){
        if (event.getType() == RenderGameOverlayEvent.ElementType.AIR){
            RenderSystem.translated(0,12,0);
        }
    }
}
