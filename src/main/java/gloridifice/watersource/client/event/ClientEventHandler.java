package gloridifice.watersource.client.event;

import com.mojang.blaze3d.systems.RenderSystem;
import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.recipe.ThirstItemRecipe;
import gloridifice.watersource.common.recipe.ThirstItemRecipeManager;
import gloridifice.watersource.common.recipe.WaterLevelRecipe;
import gloridifice.watersource.common.recipe.WaterLevelRecipeManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static gloridifice.watersource.client.hud.WaterLevelGui.OVERLAY_BAR;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = WaterSource.MODID)
public class ClientEventHandler {
    @SubscribeEvent
    public static void onRenderGameOverlayEvent(RenderGameOverlayEvent.Pre event){
        if (event.getType() == RenderGameOverlayEvent.ElementType.AIR){
            RenderSystem.translated(0,-12,0);
        }
    }
    @SubscribeEvent
    public static void onRenderGameOverlayEvent(RenderGameOverlayEvent.Post event){
        if (event.getType() == RenderGameOverlayEvent.ElementType.AIR){
            RenderSystem.translated(0,12,0);
        }
    }
    @SubscribeEvent
    public static void onRenderTooltipEvent(RenderTooltipEvent.PostText event){

        WaterLevelRecipe recipe = WaterLevelRecipeManager.getRecipeFromItemStack(event.getStack());
        if (recipe != null){
            ThirstItemRecipe recipe1 = ThirstItemRecipeManager.getRecipeFromItemStick(event.getStack());
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableAlphaTest();
            RenderSystem.enableBlend();

            Minecraft.getInstance().getTextureManager().bindTexture(OVERLAY_BAR);
            FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;

            int OffsetY = event.getY() + event.getHeight() - 8;
            int texU = 0;
            int texU1 = texU,texU2 = texU;
            if (recipe1 != null){
                texU1 = texU + 18;
                texU2 = texU + 9;
            }
            AbstractGui.blit(event.getX(), OffsetY, 36 + texU2, 0,9,9,256,256);
            AbstractGui.blit(event.getX(), OffsetY, texU1, 0,9,9,256,256);
            //SaturationLevel↓
            AbstractGui.blit(event.getX() + 30, OffsetY, 54 + texU2, 0,9,9,256,256);

            //TODO
            double scale = 0.9;
            RenderSystem.scaled(scale,scale,scale);
            fontRenderer.drawString("x" + ((float)recipe.getWaterLevel())/2, (int)((event.getX() + 10)/scale), (int)((OffsetY + 2)/scale), 16777215);
            //SaturationLevel↓
            fontRenderer.drawString("x" + ((float)recipe.getWaterSaturationLevel())/2, (int)((event.getX() + 41)/scale), (int)((OffsetY + 2)/scale), 16777215);
            RenderSystem.scaled(1,1,1);

            RenderSystem.enableBlend();
            RenderSystem.disableAlphaTest();
        }
    }
    @SubscribeEvent
    public static void onItemTooltipEvent(ItemTooltipEvent event){
        WaterLevelRecipe recipe = WaterLevelRecipeManager.getRecipeFromItemStack(event.getItemStack());
        if (recipe != null){
            event.getToolTip().add(new StringTextComponent("               "));
        }
    }
}
