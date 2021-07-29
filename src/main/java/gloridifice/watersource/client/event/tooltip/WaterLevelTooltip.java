package gloridifice.watersource.client.event.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.recipe.IThirstRecipe;
import gloridifice.watersource.common.recipe.ThirstItemRecipe;
import gloridifice.watersource.common.recipe.WaterLevelItemRecipe;
import gloridifice.watersource.registry.ConfigRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

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
            WaterLevelItemRecipe recipe = WaterLevelItemRecipe.getRecipeFromItem(mc.world, event.getItemStack());
            if (recipe != null) {
                StringBuilder stringBuilder = new StringBuilder(" ");
                for (int i = 0; i < recipe.getWaterLevel() / 2; i++) stringBuilder.append("  ");
                if (ModList.get().isLoaded("quark")) {
                    x += getQuarkLineCount(event.getItemStack());
                }
                event.getToolTip().add(x, new StringTextComponent(stringBuilder.toString()));
            }
        }
    }

    @SubscribeEvent
    public static void onRenderTooltipEvent(RenderTooltipEvent.PostText event) {
        tick++;
        tick %= 2000;
        if (ConfigRegistry.OPEN_FOOD_WATER_LEVEL.get()) {
            Minecraft mc = Minecraft.getInstance();
            WaterLevelItemRecipe wRecipe = WaterLevelItemRecipe.getRecipeFromItem(mc.world, event.getStack());
            if (wRecipe != null) {
                event.getLines();
                RenderSystem.pushMatrix();
                RenderSystem.enableBlend();
                IThirstRecipe tRecipe = ThirstItemRecipe.getRecipeFromItem(mc.world, event.getStack());
                RenderSystem.color3f(1.0F, 1.0F, 1.0F);

                int a = 0;
                for (int i = 0; i < event.getLines().size(); i++) {
                    String s = event.getLines().get(i).getString();
                    s = TextFormatting.getTextWithoutFormattingCodes(s);
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

                Minecraft.getInstance().getTextureManager().bindTexture(OVERLAY_BAR);
                if (wRecipe.getWaterLevel() % 2 == 0) {
                    for (int i = 0; i < wRecipe.getWaterLevel() / 2; i++) {
                        AbstractGui.blit(event.getMatrixStack(), OffsetX + i * 9, OffsetY, 36 + texU2, 0, 9, 9, 256, 256);
                        AbstractGui.blit(event.getMatrixStack(), OffsetX + i * 9, OffsetY, texU1, 0, 9, 9, 256, 256);
                    }
                }
                else {
                    AbstractGui.blit(event.getMatrixStack(), OffsetX, OffsetY, 36 + texU2, 0, 9, 9, 256, 256);
                    AbstractGui.blit(event.getMatrixStack(), OffsetX, OffsetY, 9 + texU1, 0, 9, 9, 256, 256);
                    for (int n = 1; n < (wRecipe.getWaterLevel() + 1) / 2; n++) {
                        AbstractGui.blit(event.getMatrixStack(), OffsetX + n * 9, OffsetY, 36 + texU2, 0, 9, 9, 256, 256);
                        AbstractGui.blit(event.getMatrixStack(), OffsetX + n * 9, OffsetY, texU1, 0, 9, 9, 256, 256);
                    }
                }
                //SaturationLevel
                if (tRecipe == null) {
                    RenderSystem.color4f(1f, 1f, 1f, (float) Math.abs(Math.sin(tick / 40D)));
                    if (wRecipe.getWaterLevel() % 2 == 0) {
                        for (int i = 0; i < wRecipe.getWaterSaturationLevel() / 2; i++) {
                            AbstractGui.blit(event.getMatrixStack(), OffsetX + i * 9, OffsetY + 1, 9 + texU1, 9, 9, 9, 256, 256);
                            AbstractGui.blit(event.getMatrixStack(), OffsetX + i * 9, OffsetY - 1, texU1, 9, 9, 9, 256, 256);
                        }
                    }
                    else {
                        AbstractGui.blit(event.getMatrixStack(), OffsetX + (wRecipe.getWaterSaturationLevel() / 2 - 1) * 9, OffsetY - 1, texU1, 9, 9, 9, 256, 256);
                        for (int n = 0; n < (wRecipe.getWaterSaturationLevel() + 1) / 2 - 1; n++) {
                            AbstractGui.blit(event.getMatrixStack(), OffsetX + n * 9, OffsetY - 1, texU1, 9, 9, 9, 256, 256);
                            AbstractGui.blit(event.getMatrixStack(), OffsetX + n * 9, OffsetY + 1, 9 + texU1, 9, 9, 9, 256, 256);
                        }
                    }
                    RenderSystem.color4f(1f, 1f, 1f, 1f);
                }
                RenderSystem.disableBlend();
                RenderSystem.popMatrix();
            }
        }
    }

    public static int getQuarkLineCount(ItemStack itemStack) {
        int i = 0;
        List<EffectInstance> potions = PotionUtils.getEffectsFromStack(itemStack);
        for (EffectInstance instance : potions) {
            if (instance.getPotion() == Effects.SLOWNESS) {
                i++;
            }
            if (instance.getPotion() == Effects.SPEED) {
                i++;
            }
            if (instance.getPotion() == Effects.LUCK) {
                i++;
            }
            if (instance.getPotion() == Effects.WEAKNESS) {
                i++;
            }
            if (instance.getPotion() == Effects.STRENGTH) {
                i++;
            }
            //for Quark
            if (instance.getPotion() == ForgeRegistries.POTIONS.getValue(new ResourceLocation("quark", "resilience"))) {
                i++;
            }
        }
        if (itemStack.isFood()) {
            i++;
        }
        return i;
    }
}
