package gloridifice.watersource.client.event;

import com.mojang.blaze3d.systems.RenderSystem;
import gloridifice.watersource.WaterSource;
import net.minecraft.client.Minecraft;
import net.minecraft.util.profiling.InactiveProfiler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = WaterSource.MODID)
public class ClientEventHandler {
    @SubscribeEvent
    public static void onAirRenderPre(RenderGameOverlayEvent.Pre event) {
        InactiveProfiler profiler = (InactiveProfiler) Minecraft.getInstance().getProfiler();
        if (event.getType() == RenderGameOverlayEvent.ElementType.LAYER && profiler.getEntry("air") != null) {
            int translateY = -11;
            event.getMatrixStack().translate(0, translateY, 0);
        }
    }

    @SubscribeEvent
    public static void onAirRenderPost(RenderGameOverlayEvent.Post event) {
        InactiveProfiler profiler = (InactiveProfiler) Minecraft.getInstance().getProfiler();
        if (event.getType() == RenderGameOverlayEvent.ElementType.LAYER && profiler.getEntry("air") != null) {
            int translateY = 11;
            event.getMatrixStack().translate(0, translateY, 0);
        }
    }
}
