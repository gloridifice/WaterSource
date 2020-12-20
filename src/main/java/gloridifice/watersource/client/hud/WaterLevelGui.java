package gloridifice.watersource.client.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.capability.WaterLevelCapability;
import gloridifice.watersource.registry.ConfigRegistry;
import gloridifice.watersource.registry.EffectRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = WaterSource.MODID)
public class WaterLevelGui extends AbstractGui {
    protected static int tick = 0;
    public final static ResourceLocation OVERLAY_BAR = new ResourceLocation(WaterSource.MODID, "textures/gui/hud/icons.png");
    protected final static int WIDTH = 9;
    protected final static int HEIGHT = 9;

    protected Minecraft mc;

    public WaterLevelGui(Minecraft mc) {
        this.mc = mc;
    }

    public void renderWaterLevel(MatrixStack matrix, int screenWidth, int screenHeight, WaterLevelCapability.Data capData, double toughness) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        mc.getTextureManager().bindTexture(OVERLAY_BAR);

        EffectInstance effectInstance = mc.player.getActivePotionEffect(EffectRegistry.THIRST);
        int waterLevel = capData.getWaterLevel();
        int waterSaturationLevel = capData.getWaterSaturationLevel();
        float waterExhaustionLevel = capData.getWaterExhaustionLevel();
        int OffsetX = screenWidth / 2 + 91;
        int OffsetY = screenHeight - 50;
        if (ModList.get().isLoaded("toughnessbar") && toughness != 0){
            OffsetY -= 10;
        }
        int OffsetY1 = OffsetY;
        int texU = 0;
        int texV = 0;
        int texU1 = texU;
        int texU2 = texU;
        if (effectInstance != null) {
            texU1 += 18;
            texU2 += 9;
        }
        for (int k6 = 0; k6 < 10; ++k6) {
            if (waterSaturationLevel <= 0.0F && tick % (waterLevel * 3 + 1) == 0) {
                OffsetY1 = OffsetY + (tick / 2 + k6 + waterLevel) % 3 - 1;
            }
            int OffsetX1 = OffsetX - k6 * 8 - 9;
            this.blit(matrix, OffsetX1, OffsetY1, 36 + texU2, texV, WIDTH, HEIGHT);

            if (k6 * 2 + 1 < waterLevel) {
                this.blit(matrix, OffsetX1, OffsetY1, texU1, texV, WIDTH, HEIGHT);
            } else if (k6 * 2 + 1 == waterLevel) {
                this.blit(matrix, OffsetX1, OffsetY1, texU1 + 9, texV, WIDTH, HEIGHT);
            }

            //Water Saturation Level↓
            if (ConfigRegistry.OPEN_WATER_SATURATION_LEVEL.get()) {
                if (k6 * 2 + 1 < waterSaturationLevel) {
                    this.blit(matrix, OffsetX1, OffsetY1 - 1, texU1, texV + 9, 9, 3);
                    this.blit(matrix, OffsetX1, OffsetY1 + 7, texU1 + 9, texV + 9, 9, 3);
                } else if (k6 * 2 + 1 == waterSaturationLevel) {
                    this.blit(matrix, OffsetX1, OffsetY1 - 1, texU1, texV + 9, 9, 3);
                }
            }
        }
        //test↓
        if (ConfigRegistry.IS_DEBUG_MODE.get()){
            WaterLevelGui.drawString(matrix, mc.fontRenderer, String.valueOf(waterExhaustionLevel), OffsetX, OffsetY - 10, 16777215);
        }

        RenderSystem.enableBlend(); // TODO why not disable?
        RenderSystem.disableAlphaTest();
        mc.getTextureManager().bindTexture(HUDHandler.DEFAULT);
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        tick++;
        tick %= 1200;
    }
}
