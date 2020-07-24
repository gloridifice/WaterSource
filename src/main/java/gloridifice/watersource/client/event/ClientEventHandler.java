package gloridifice.watersource.client.event;

import com.mojang.blaze3d.systems.RenderSystem;
import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.recipe.ThirstItemRecipe;
import gloridifice.watersource.common.recipe.ThirstItemRecipeManager;
import gloridifice.watersource.common.recipe.WaterLevelRecipe;
import gloridifice.watersource.common.recipe.WaterLevelRecipeManager;
import gloridifice.watersource.registry.ConfigRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import static gloridifice.watersource.client.hud.WaterLevelGui.OVERLAY_BAR;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = WaterSource.MODID)
public class ClientEventHandler {
    @SubscribeEvent
    public static void onAirRenderPre(RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.AIR) {
            int translateY = -11;
            RenderSystem.translated(0, translateY, 0);
        }
    }
    @SubscribeEvent
    public static void onAirRenderPost(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.AIR) {
            int translateY = 11;
            RenderSystem.translated(0, translateY, 0);
        }
    }
}
