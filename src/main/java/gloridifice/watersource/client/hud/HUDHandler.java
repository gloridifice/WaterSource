package gloridifice.watersource.client.hud;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.capability.WaterLevelCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraft.entity.ai.attributes.Attributes.ARMOR_TOUGHNESS;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = WaterSource.MODID)
public class HUDHandler {
    public final static ResourceLocation DEFAULT = new ResourceLocation("minecraft", "textures/gui/icons.png");
    private final static WaterLevelHUD WATER_LEVEL_HUD = new WaterLevelHUD(Minecraft.getInstance());
    private final static WaterFilterStrainerHUD WATER_FILTER_STRAINER_HUD = new WaterFilterStrainerHUD(Minecraft.getInstance());

    @SubscribeEvent(receiveCanceled = true)
    public static void onRenderGameOverlayEvent(RenderGameOverlayEvent.Post event){
        Minecraft mc = Minecraft.getInstance();
        int screenHeight = event.getWindow().getScaledHeight();
        int screenWidth = event.getWindow().getScaledWidth();
        ClientPlayerEntity playerEntity = Minecraft.getInstance().player;

        RayTraceResult pos = mc.objectMouseOver;
        if (event.getType() == RenderGameOverlayEvent.ElementType.FOOD){
            if (playerEntity != null && !playerEntity.isCreative() && !playerEntity.isSpectator() && !Minecraft.getInstance().gameSettings.hideGUI){
                if (!playerEntity.isSpectator())
                {
                    playerEntity.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(t ->
                    {
                        WATER_LEVEL_HUD.render(event.getMatrixStack(),screenWidth,screenHeight,t,playerEntity.getAttribute(ARMOR_TOUGHNESS).getValue());
                    });
                }
            }
        }
        if (pos != null && event.getType() == RenderGameOverlayEvent.ElementType.TEXT){
            BlockPos bpos = pos.getType() == RayTraceResult.Type.BLOCK ? ((BlockRayTraceResult) pos).getPos() : null;
            WATER_FILTER_STRAINER_HUD.render(event.getMatrixStack(),bpos);
        }
    }
}
