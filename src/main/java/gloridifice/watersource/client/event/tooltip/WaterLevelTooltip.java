package gloridifice.watersource.client.event.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.recipe.IThirstRecipe;
import gloridifice.watersource.common.recipe.ThirstItemRecipe;
import gloridifice.watersource.common.recipe.WaterLevelItemRecipe;
import gloridifice.watersource.registry.ConfigRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.opengl.GL11;

import java.util.List;

import static gloridifice.watersource.client.hud.WaterLevelHUD.OVERLAY_BAR;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = WaterSource.MODID)
public class WaterLevelTooltip {
    private static double tick = 0;
    private static int x;

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onItemTooltipEvent(ItemTooltipEvent event) {
        x = 1;
        if (ConfigRegistry.OPEN_FOOD_WATER_LEVEL.get()) {
            Minecraft mc = Minecraft.getInstance();
            WaterLevelItemRecipe recipe = WaterLevelItemRecipe.getRecipeFromItem(mc.level, event.getItemStack());
            if (recipe != null) {
                StringBuilder stringBuilder = new StringBuilder(" ");
                for (int i = 0; i < recipe.getWaterLevel() / 2; i++) stringBuilder.append("  ");
                if (ModList.get().isLoaded("quark")) {
                    x += getQuarkLineCount(event.getItemStack());
                }
                event.getToolTip().add(x, new TextComponent(stringBuilder.toString()));
            }
        }
    }

    @SubscribeEvent
    public static void onRenderTooltipEvent(RenderTooltipEvent.PostText event) {
        tick++;
        tick %= 2000;
        if (ConfigRegistry.OPEN_FOOD_WATER_LEVEL.get()) {
            Minecraft mc = Minecraft.getInstance();
            WaterLevelItemRecipe wRecipe = WaterLevelItemRecipe.getRecipeFromItem(mc.level, event.getStack());
            if (wRecipe != null) {
                event.getLines();
                event.getMatrixStack().pushPose();
                RenderSystem.enableBlend();
                IThirstRecipe tRecipe = ThirstItemRecipe.getRecipeFromItem(mc.level, event.getStack());
                int a = 0;
                for (int i = 0; i < event.getLines().size(); i++) {
                    String s = event.getLines().get(i).getString();
                    //s = .getTextWithoutFormattingCodes(s);
                    if (s.trim().isEmpty()) {
                        a = i;
                        break;
                    }
                }
                int OffsetY;
                if (ModList.get().isLoaded("quark")) a += getQuarkLineCount(event.getStack());
                OffsetY = getQuarkLineCount(event.getStack()) >= 1 ? event.getY() + a * 9 + 3 : event.getY() + a * 9 + 2;
                int OffsetX = event.getX() - 1;
                int texU = 0;
                int texU1 = texU, texU2 = texU;
                if (tRecipe != null) {
                    texU1 = texU + 18;
                    texU2 = texU + 9;
                }

                RenderSystem.setShaderTexture(0, OVERLAY_BAR);
                //Minecraft.getInstance().getTextureManager().bindForSetup(OVERLAY_BAR);
                if (wRecipe.getWaterLevel() % 2 == 0) {
                    for (int i = 0; i < wRecipe.getWaterLevel() / 2; i++) {
                        GuiComponent.blit(event.getMatrixStack(), OffsetX + i * 9, OffsetY, 36 + texU2, 0, 9, 9, 256, 256);
                        GuiComponent.blit(event.getMatrixStack(), OffsetX + i * 9, OffsetY, texU1, 0, 9, 9, 256, 256);
                    }
                }
                else {
                    GuiComponent.blit(event.getMatrixStack(), OffsetX, OffsetY, 36 + texU2, 0, 9, 9, 256, 256);
                    GuiComponent.blit(event.getMatrixStack(), OffsetX, OffsetY, 9 + texU1, 0, 9, 9, 256, 256);
                    for (int n = 1; n < (wRecipe.getWaterLevel() + 1) / 2; n++) {
                        GuiComponent.blit(event.getMatrixStack(), OffsetX + n * 9, OffsetY, 36 + texU2, 0, 9, 9, 256, 256);
                        GuiComponent.blit(event.getMatrixStack(), OffsetX + n * 9, OffsetY, texU1, 0, 9, 9, 256, 256);
                    }
                }
                //SaturationLevel
                if (tRecipe == null) {
                    RenderSystem.setShaderColor(1f, 1f, 1f, (float) Math.abs(Math.sin(tick / 40D)));
                   //GL11.glColor4f();
                    if (wRecipe.getWaterLevel() % 2 == 0) {
                        for (int i = 0; i < wRecipe.getWaterSaturationLevel() / 2; i++) {
                            GuiComponent.blit(event.getMatrixStack(), OffsetX + i * 9, OffsetY + 1, 9 + texU1, 9, 9, 9, 256, 256);
                            GuiComponent.blit(event.getMatrixStack(), OffsetX + i * 9, OffsetY - 1, texU1, 9, 9, 9, 256, 256);
                        }
                    }
                    else {
                        GuiComponent.blit(event.getMatrixStack(), OffsetX + (wRecipe.getWaterSaturationLevel() / 2 - 1) * 9, OffsetY - 1, texU1, 9, 9, 9, 256, 256);
                        for (int n = 0; n < (wRecipe.getWaterSaturationLevel() + 1) / 2 - 1; n++) {
                            GuiComponent.blit(event.getMatrixStack(), OffsetX + n * 9, OffsetY - 1, texU1, 9, 9, 9, 256, 256);
                            GuiComponent.blit(event.getMatrixStack(), OffsetX + n * 9, OffsetY + 1, 9 + texU1, 9, 9, 9, 256, 256);
                        }
                    }
                }
                //GL11.glColor4f(1f, 1f, 1f, 1f);
                RenderSystem.disableBlend();
                event.getMatrixStack().popPose();
            }
        }
    }

    public static int getQuarkLineCount(ItemStack itemStack) {
        int i = 0;
        List<MobEffectInstance> potions = PotionUtils.getMobEffects(itemStack);
        for (MobEffectInstance instance : potions) {
            if (instance.getEffect() == MobEffects.MOVEMENT_SLOWDOWN) {
                i++;
            }
            if (instance.getEffect() == MobEffects.MOVEMENT_SPEED) {
                i++;
            }
            if (instance.getEffect() == MobEffects.LUCK) {
                i++;
            }
            if (instance.getEffect() == MobEffects.WEAKNESS) {
                i++;
            }
            if (instance.getEffect() == MobEffects.CONDUIT_POWER) {
                i++;
            }
            //for Quark
            if (ForgeRegistries.POTIONS.getValue(new ResourceLocation("quark", "resilience")).getEffects().contains(instance.getEffect())) {
                i++;
            }
        }
        if (itemStack.getItem().getFoodProperties() != null) {
            i++;
        }
        return i;
    }
}
