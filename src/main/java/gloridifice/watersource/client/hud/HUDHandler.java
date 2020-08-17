package gloridifice.watersource.client.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import gloridifice.watersource.WaterSource;
import gloridifice.watersource.client.hud.WaterLevelGui;
import gloridifice.watersource.common.capability.WaterLevelCapability;
import gloridifice.watersource.common.tile.WaterFilterDownTile;
import gloridifice.watersource.common.tile.WaterFilterUpTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.IProfiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.CapabilityItemHandler;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = WaterSource.MODID)
public class HUDHandler {
    public final static ResourceLocation DEFAULT = new ResourceLocation("minecraft", "textures/gui/icons.png");

    private final static WaterLevelGui WATER_LEVEL_GUI = new WaterLevelGui(Minecraft.getInstance());

    @SubscribeEvent(receiveCanceled = true)
    public static void onRenderGameOverlayEvent(RenderGameOverlayEvent.Post event){
        Minecraft mc = Minecraft.getInstance();
        int screenHeight = event.getWindow().getScaledHeight();
        int screenWidth = event.getWindow().getScaledWidth();
        ClientPlayerEntity playerEntity = Minecraft.getInstance().player;
        IProfiler profiler = mc.getProfiler();
        RayTraceResult pos = mc.objectMouseOver;
        if (event.getType() == RenderGameOverlayEvent.ElementType.FOOD){
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
        if (pos != null && event.getType() == RenderGameOverlayEvent.ElementType.TEXT){
            BlockPos bpos = pos.getType() == RayTraceResult.Type.BLOCK ? ((BlockRayTraceResult) pos).getPos() : null;
            TileEntity tile = bpos != null ? mc.world.getTileEntity(bpos) : null;
            if (tile instanceof WaterFilterUpTile){
                profiler.startSection("waterFilterStrainer");
                renderWaterFilterStrainer((WaterFilterUpTile)tile);
                profiler.endSection();
            }
            if (tile instanceof WaterFilterDownTile){
                profiler.startSection("waterFilterStrainer");
                renderWaterFilterStrainer((WaterFilterUpTile)mc.world.getTileEntity(bpos.up()));
                profiler.endSection();
            }
        }
    }
    private static void renderWaterFilterStrainer(WaterFilterUpTile tile){
        ItemStack stack = tile.getStrainer().map(data -> data.getStackInSlot(0)).orElse(ItemStack.EMPTY);
        if (stack.isEmpty())return;
        Minecraft mc = Minecraft.getInstance();
        FontRenderer fontRenderer = mc.fontRenderer;
        float scale = 1.3f;
        int x = mc.getMainWindow().getScaledWidth()/2;
        int y = mc.getMainWindow().getScaledHeight()/2;
        RenderSystem.enableRescaleNormal();
        RenderSystem.pushMatrix();
        RenderSystem.scalef(scale,scale,1f);
        mc.getItemRenderer().renderItemAndEffectIntoGUI(stack,(int)((float)(x - 9)/scale),(int)((float)(y - 16)/scale));
        RenderSystem.popMatrix();
        String text = I18n.format("watersource.misc.damage") + (stack.getMaxDamage() - stack.getDamage()) + "/" + stack.getMaxDamage();
        fontRenderer.drawString(text,x - fontRenderer.getStringWidth(text)/2,y + 6,0xFFFFFF);//
    }
}
