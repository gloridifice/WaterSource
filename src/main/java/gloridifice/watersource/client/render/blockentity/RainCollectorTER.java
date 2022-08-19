package gloridifice.watersource.client.render.blockentity;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import gloridifice.watersource.common.block.entity.RainCollectorBlockEntity;
import gloridifice.watersource.helper.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fluids.FluidStack;

public class RainCollectorTER implements BlockEntityRenderer<RainCollectorBlockEntity> {
    public RainCollectorTER(BlockEntityRendererProvider.Context context) {

    }
    @Override
    public void render(RainCollectorBlockEntity rainCollector, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Minecraft mc = Minecraft.getInstance();

        VertexConsumer buffer = bufferIn.getBuffer(RenderType.translucent());
        rainCollector.getTank().ifPresent(tank -> {
            FluidStack tankFluid = tank.getFluid();
/*
            TextureAtlas still0 = (TextureAtlas) mc.getTextureManager().getTexture(tankFluid.getFluid().getAttributes().getStillTexture());
            TextureAtlasSprite still = still0.getSprite(tankFluid.getFluid().getAttributes().getStillTexture());
*/
            TextureAtlasSprite still = mc.getBlockRenderer().getBlockModelShaper().getTexture(tankFluid.getFluid().defaultFluidState().createLegacyBlock(), rainCollector.getLevel(), rainCollector.getBlockPos());
            int colorRGB = tankFluid.getFluid().getAttributes().getColor();
            float alpha = 1.0f;
            float height = (float) tankFluid.getAmount() / (float) tank.getCapacity() * 0.75f;
            float vHeight = (still.getV1() - still.getV0()) * (1f - (float) tankFluid.getAmount() / (float) tank.getCapacity());
            matrixStackIn.pushPose();
            //GlStateManager.disable Cull();
            GlStateManager._enableBlend();

            RenderHelper.addVertex(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.125f, still.getU0(), still.getV0(), colorRGB, alpha);
            RenderHelper.addVertex(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.875f, still.getU1(), still.getV0(), colorRGB, alpha);
            RenderHelper.addVertex(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.875f, still.getU1(), still.getV1(), colorRGB, alpha);
            RenderHelper.addVertex(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.125f, still.getU0(), still.getV1(), colorRGB, alpha);

            RenderHelper.addVertex(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.125f, still.getU0(), still.getV0() + vHeight, colorRGB, alpha);
            RenderHelper.addVertex(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.875f, still.getU1(), still.getV0() + vHeight, colorRGB, alpha);
            RenderHelper.addVertex(buffer, matrixStackIn, 0.875f, 0.125f, 0.875f, still.getU1(), still.getV1(), colorRGB, alpha);
            RenderHelper.addVertex(buffer, matrixStackIn, 0.875f, 0.125f, 0.125f, still.getU0(), still.getV1(), colorRGB, alpha);

            RenderHelper.addVertex(buffer, matrixStackIn, 0.125f, 0.125f, 0.125f, still.getU0(), still.getV0(), colorRGB, alpha);
            RenderHelper.addVertex(buffer, matrixStackIn, 0.125f, 0.125f, 0.875f, still.getU1(), still.getV0(), colorRGB, alpha);
            RenderHelper.addVertex(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.875f, still.getU1(), still.getV1() - vHeight, colorRGB, alpha);
            RenderHelper.addVertex(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.125f, still.getU0(), still.getV1() - vHeight, colorRGB, alpha);

            RenderHelper.addVertex(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.125f, still.getU0(), still.getV0() + vHeight, colorRGB, alpha);
            RenderHelper.addVertex(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.125f, still.getU1(), still.getV0() + vHeight, colorRGB, alpha);
            RenderHelper.addVertex(buffer, matrixStackIn, 0.875f, 0.125f, 0.125f, still.getU1(), still.getV1(), colorRGB, alpha);
            RenderHelper.addVertex(buffer, matrixStackIn, 0.125f, 0.125f, 0.125f, still.getU0(), still.getV1(), colorRGB, alpha);

            RenderHelper.addVertex(buffer, matrixStackIn, 0.125f, 0.125f, 0.875f, still.getU0(), still.getV0() + vHeight, colorRGB, alpha);
            RenderHelper.addVertex(buffer, matrixStackIn, 0.875f, 0.125f, 0.875f, still.getU1(), still.getV0() + vHeight, colorRGB, alpha);
            RenderHelper.addVertex(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.875f, still.getU1(), still.getV1(), colorRGB, alpha);
            RenderHelper.addVertex(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.875f, still.getU0(), still.getV1(), colorRGB, alpha);

            GlStateManager._disableBlend();
            matrixStackIn.popPose();
        });
    }


}
