package gloridifice.watersource.client.hud;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.block.entity.StrainerBlockEntity;
import gloridifice.watersource.registry.CapabilityRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.CapabilityItemHandler;

import static net.minecraft.world.entity.ai.attributes.Attributes.ARMOR_TOUGHNESS;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = WaterSource.MODID)
public class HUDHandler {
    public final static ResourceLocation DEFAULT = new ResourceLocation("minecraft", "textures/gui/icons.png");
    private final static WaterLevelHUD WATER_LEVEL_HUD = new WaterLevelHUD(Minecraft.getInstance());
    private final static WaterFilterStrainerHUD WATER_FILTER_STRAINER_HUD = new WaterFilterStrainerHUD(Minecraft.getInstance());

    @SubscribeEvent(receiveCanceled = true)
    public static void onRenderGameOverlayEvent(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        int screenHeight = event.getWindow().getGuiScaledHeight();
        int screenWidth = event.getWindow().getGuiScaledWidth();
        LocalPlayer playerEntity = Minecraft.getInstance().player;
        HitResult hitResult = mc.hitResult;
        //render water level
        if (event.getType() == RenderGameOverlayEvent.ElementType.LAYER) {
            if (playerEntity != null && !playerEntity.isCreative() && !playerEntity.isSpectator() && !mc.options.hideGui) {
                playerEntity.getCapability(CapabilityRegistry.PLAYER_WATER_LEVEL).ifPresent(t -> {
                    WATER_LEVEL_HUD.render(event.getMatrixStack(), event.getPartialTicks(), screenWidth, screenHeight, t, playerEntity.getAttribute(ARMOR_TOUGHNESS).getValue());
                });
            }
        }
        //render
        if (hitResult != null && event.getType() == RenderGameOverlayEvent.ElementType.LAYER) {
            if (!mc.options.hideGui){
                BlockPos pos = hitResult.getType() == HitResult.Type.BLOCK ? ((BlockHitResult) hitResult).getBlockPos() : null;
                if (pos != null) WATER_FILTER_STRAINER_HUD.render(event.getMatrixStack(), pos);
            }
        }
    }
}
