package gloridifice.watersource.client.tooltip.component;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import gloridifice.watersource.client.hud.WaterLevelHUD;
import gloridifice.watersource.common.recipe.ModRecipeManager;
import gloridifice.watersource.common.recipe.ThirstRecipe;
import gloridifice.watersource.common.recipe.WaterLevelAndEffectRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;


public class WaterTooltipRenderer implements ClientTooltipComponent {

    protected final ItemStack itemStack;
    protected final int width;
    protected final int height;
    private static int tick = 0;

    public WaterTooltipRenderer(WaterTooltipComponent component) {
        this.itemStack = component.itemStack;
        this.width = component.width;
        this.height = component.height;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth(Font font) {
        return width;
    }

    @Override
    public void renderImage(Font font, int tooltipX, int tooltipY, PoseStack pose, ItemRenderer itemRenderer, int something) {
        tick++;
        tick %= 10000;
        Minecraft mc = Minecraft.getInstance();
        WaterLevelAndEffectRecipe wlRecipe = ModRecipeManager.getWERecipeFromItem(mc.level, itemStack);
        ThirstRecipe tRecipe = ModRecipeManager.getThirstRecipeFromItem(mc.level, itemStack);
        if (wlRecipe != null) {
            int max = Math.max(wlRecipe.getWaterLevel(), wlRecipe.getWaterSaturationLevel());
            //if (max <= 8) {
            pose.pushPose();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.setShaderTexture(0, WaterLevelHUD.OVERLAY_BAR);
            RenderSystem.enableBlend();
            int maxBalls = (int) Math.ceil((double) max / 2);
            int wlBalls = (int) Math.ceil((double) wlRecipe.getWaterLevel() / 2);
            //render balls' outlines
            for (int i = 0; i < maxBalls; i++) {
                int x = tooltipX + i * 9 - 1;
                int y = tooltipY - 1;
                int u = 36;
                int v = 0;
                if (tRecipe != null) {
                    u += 18;
                }
                GuiComponent.blit(pose, x, y, u, v, 9, 9, 256, 256);
            }
            //render water level balls
            for (int i = 0; i < wlBalls; i++) {
                int x = tooltipX + i * 9 - 1;
                int y = tooltipY - 1;
                int u = 0;
                int v = 18;
                if (i == wlBalls - 1 && wlRecipe.getWaterLevel() % 2 != 0) {
                    u += 9;
                }
                if (tRecipe != null) {
                    u += 18;
                }
                GuiComponent.blit(pose, x, y, u, v, 9, 9, 256, 256);
            }
            //render water saturation level balls
            if (tRecipe == null) {
                for (int i = 0; i < wlRecipe.getWaterSaturationLevel(); i++) {
                    int x = tooltipX + (int) ((double) i / 2) * 9 - 1;
                    int y = tooltipY - 2;
                    int u = 0;
                    int v = 9;
                    if ((i + 1) % 2 == 0) {
                        u += 9;
                        y += 2;
                    }
                    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, (float) Math.abs(Math.sin(tick / 60d)));
                    GuiComponent.blit(pose, x, y, u, v, 9, 9, 256, 256);
                    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                }
            }
            pose.popPose();

        }
    }
}
