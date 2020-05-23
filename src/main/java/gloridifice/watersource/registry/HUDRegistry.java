package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.client.hud.ThirstRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = WaterSource.MODID)
public class HUDRegistry {
    public final static ResourceLocation DEFAULT = new ResourceLocation("minecraft", "textures/gui/icons.png");

    private final static ThirstRender THIRST_RENDER = new ThirstRender(Minecraft.getInstance());

    @SubscribeEvent(receiveCanceled = true)
    public static void onRenderGameOverlayEvent(RenderGameOverlayEvent.Pre event){
        int screenHeight = event.getWindow().getHeight();
        int screenWidth = event.getWindow().getWidth();
        ClientPlayerEntity playerEntity = Minecraft.getInstance().player;
        if (playerEntity != null){
            THIRST_RENDER.renderThirst(screenWidth, screenHeight,5);
        }
    }
}
