package gloridifice.watersource.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import gloridifice.watersource.WaterSource;
import gloridifice.watersource.registry.HUDRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ThirstRender extends AbstractGui {
    private final static ResourceLocation OVERLAY_BAR = new ResourceLocation(WaterSource.MODID,"textures/gui/hud/icons.png");
    private final static int WIDTH = 9;
    private final static int HEIGHT = 9;

    private Minecraft mc;

    public ThirstRender(Minecraft mc){
        this.mc = mc;
    }
    public void renderThirst(int screenWidth, int screenHeight, int thirst)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        mc.getTextureManager().bindTexture(OVERLAY_BAR);

        int oriOffsetX = screenWidth / 2 + 91;
        int oriOffsetY = screenHeight - 50;
        int texU = 0;
        int texV = 0;
        int OffsetX = oriOffsetX;
        int OffsetY = oriOffsetY;
        for (int i = 0;i < thirst; i++){
            blit(i * 9 + OffsetX,OffsetY,texU,texV,WIDTH,HEIGHT);
        }

        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        mc.getTextureManager().bindTexture(HUDRegistry.DEFAULT);
    }
}
