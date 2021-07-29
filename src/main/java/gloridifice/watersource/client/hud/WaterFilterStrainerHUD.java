package gloridifice.watersource.client.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.tile.WaterFilterDownTile;
import gloridifice.watersource.common.tile.WaterFilterUpTile;
import gloridifice.watersource.registry.BlockRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.IProfiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.CapabilityItemHandler;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = WaterSource.MODID)
public class WaterFilterStrainerHUD extends AbstractGui {
    int animeTime = 0;
    protected Minecraft mc;

    public WaterFilterStrainerHUD(Minecraft mc) {
        this.mc = mc;
    }

    public void render(MatrixStack matrixStack, BlockPos bpos) {
        TileEntity tile = bpos != null ? mc.world.getTileEntity(bpos) : null;
        IProfiler profiler = mc.getProfiler();
        if (tile == null) {
            animeTime -= animeTime > 0 ? 1 : 0;
        }
        else if (tile instanceof WaterFilterUpTile) {
            profiler.startSection("waterFilterStrainer");
            renderWaterFilterStrainer(matrixStack, tile);
            profiler.endSection();
        }
        else if (tile instanceof WaterFilterDownTile) {
            profiler.startSection("waterFilterStrainer");
            renderWaterFilterStrainer(matrixStack, mc.world.getTileEntity(bpos.up()));
            profiler.endSection();
        }
    }

    private void renderWaterFilterStrainer(MatrixStack matrixStack, TileEntity tile) {
        ItemStack stack = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(data -> data.getStackInSlot(0)).orElse(ItemStack.EMPTY);
        if (stack.isEmpty()) return;
        Minecraft mc = Minecraft.getInstance();
        FontRenderer fontRenderer = mc.fontRenderer;

        float scale = 1.3f;
        int x = 10;
        int y = mc.getMainWindow().getScaledHeight() / 2;
        RenderSystem.pushMatrix();
        RenderSystem.enableRescaleNormal();
        RenderSystem.scalef(scale, scale, 1f);
        RenderSystem.enableBlend();
        mc.getItemRenderer().renderItemIntoGUI(stack, x, (int) ((float) (y - 16) / scale));
        RenderSystem.popMatrix();

        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.blendColor(1.0F, 1.0F, 1.0F, 0.1F);
        String text = I18n.format("watersource.misc.damage") + (stack.getMaxDamage() - stack.getDamage()) + "/" + stack.getMaxDamage();
        if (stack.getItem() == BlockRegistry.ITEM_DIRTY_STRAINER) text = I18n.format("watersource.misc.dirty_strainer");
        fontRenderer.drawString(matrixStack, text, x + 4, y + 6, 0xFFFFFF);
        RenderSystem.popMatrix();
    }
}
