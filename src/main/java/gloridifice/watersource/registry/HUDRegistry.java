package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.client.hud.WaterLevelGui;
import gloridifice.watersource.common.capability.WaterLevelCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = WaterSource.MODID)
public class HUDRegistry {
    public final static ResourceLocation DEFAULT = new ResourceLocation("minecraft", "textures/gui/icons.png");

    private final static WaterLevelGui WATER_LEVEL_GUI = new WaterLevelGui(Minecraft.getInstance());

    @SubscribeEvent(receiveCanceled = true)
    public static void onRenderGameOverlayEvent(RenderGameOverlayEvent.Post event){
        if (event.getType() == RenderGameOverlayEvent.ElementType.FOOD){
            int screenHeight = event.getWindow().getScaledHeight();
            int screenWidth = event.getWindow().getScaledWidth();
            ClientPlayerEntity playerEntity = Minecraft.getInstance().player;
            if (playerEntity != null && !playerEntity.isCreative() && !playerEntity.isSpectator() && !Minecraft.getInstance().gameSettings.hideGUI){
                if (!playerEntity.isSpectator())
                {
                    playerEntity.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(t ->
                    {
                        WATER_LEVEL_GUI.renderWaterLevel(screenWidth,screenHeight,t,playerEntity.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getValue());
                    });
                }
            }
        }
    }
}
