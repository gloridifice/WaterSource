package gloridifice.watersource.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.capability.WaterLevelCapability;
import gloridifice.watersource.registry.HUDRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class WaterLevelGui extends AbstractGui {
    protected final Random rand = new Random();
    protected final static ResourceLocation OVERLAY_BAR = new ResourceLocation(WaterSource.MODID,"textures/gui/hud/icons.png");
    protected final static int WIDTH = 9;
    protected final static int HEIGHT = 9;

    protected Minecraft mc;

    public WaterLevelGui(Minecraft mc){
        this.mc = mc;
    }
    public void renderWaterLevel(int screenWidth, int screenHeight, WaterLevelCapability.Data capData)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableAlphaTest();
        mc.getTextureManager().bindTexture(OVERLAY_BAR);

        int waterLevel = capData.getWaterLevel();
        int waterSaturationLevel = capData.getWaterSaturationLevel();
        float waterExhaustionLevel = capData.getWaterExhaustionLevel();
        int OffsetX = screenWidth / 2 + 91;
        int OffsetY = screenHeight - 50;
        int OffsetY1 = OffsetY;
        int texU = 0;
        int texV = 0;
        for(int k6 = 0; k6 < 10; ++k6) {
            if (waterSaturationLevel <= 0.0F && mc.ingameGUI.getTicks() % (waterLevel * 3 + 1) == 0) {
                OffsetY1 = OffsetY + (this.rand.nextInt(3) - 1);
            }
            int OffsetX1 = OffsetX - k6 * 8 - 9;
            this.blit(OffsetX1, OffsetY1, 36, texV, WIDTH, HEIGHT);
            if (k6 * 2 + 1 < waterLevel) {
                this.blit(OffsetX1, OffsetY1, texU, texV, WIDTH, HEIGHT);
            }
            if (k6 * 2 + 1 == waterLevel) {
                this.blit(OffsetX1, OffsetY1, texU + 9, texV, WIDTH, HEIGHT);
            }
        }
        RenderSystem.disableAlphaTest();
        mc.getTextureManager().bindTexture(HUDRegistry.DEFAULT);
    }
}
