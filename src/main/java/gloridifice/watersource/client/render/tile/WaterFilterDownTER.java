package gloridifice.watersource.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import gloridifice.watersource.common.tile.WaterFilterDownTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraftforge.fluids.FluidStack;

public class WaterFilterDownTER extends TileEntityRenderer<WaterFilterDownTile> {
    public WaterFilterDownTER(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(WaterFilterDownTile tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Minecraft mc = Minecraft.getInstance();
        IVertexBuilder buffer = bufferIn.getBuffer(RenderType.getTranslucent());
        matrixStackIn.push();
        GlStateManager.disableCull();
        tileEntityIn.getDownTank().ifPresent(fluidTankDown -> {
            if (!fluidTankDown.isEmpty()) {
                FluidStack fluidStackDown = fluidTankDown.getFluid();
                TextureAtlasSprite still = mc.getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(fluidStackDown.getFluid().getAttributes().getStillTexture());
                int colorRGB = fluidStackDown.getFluid().getAttributes().getColor();

                float height = (float) fluidStackDown.getAmount() / (float) fluidTankDown.getCapacity() * 0.75f;
                float vHeight = (still.getMaxV() - still.getMinV()) * (1f - (float) fluidStackDown.getAmount() / (float) fluidTankDown.getCapacity());

                add(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.125f, still.getMinU(), still.getMinV(), colorRGB,1.0f);
                add(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.875f, still.getMaxU(), still.getMinV(), colorRGB,1.0f);
                add(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.875f, still.getMaxU(), still.getMaxV(), colorRGB,1.0f);
                add(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.125f, still.getMinU(), still.getMaxV(), colorRGB,1.0f);

                add(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.125f, still.getMinU(), still.getMinV() + vHeight, colorRGB,1.0f);
                add(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.875f, still.getMaxU(), still.getMinV() + vHeight, colorRGB,1.0f);
                add(buffer, matrixStackIn, 0.875f, 0.125f, 0.875f, still.getMaxU(), still.getMaxV(), colorRGB,1.0f);
                add(buffer, matrixStackIn, 0.875f, 0.125f, 0.125f, still.getMinU(), still.getMaxV(), colorRGB,1.0f);

                add(buffer, matrixStackIn, 0.125f, 0.125f, 0.125f, still.getMinU(), still.getMinV(), colorRGB,1.0f);
                add(buffer, matrixStackIn, 0.125f, 0.125f, 0.875f, still.getMaxU(), still.getMinV(), colorRGB,1.0f);
                add(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.875f, still.getMaxU(), still.getMaxV() - vHeight, colorRGB,1.0f);
                add(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.125f, still.getMinU(), still.getMaxV() - vHeight, colorRGB,1.0f);

                add(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.125f, still.getMinU(), still.getMinV() + vHeight, colorRGB,1.0f);
                add(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.125f, still.getMaxU(), still.getMinV() + vHeight, colorRGB,1.0f);
                add(buffer, matrixStackIn, 0.875f, 0.125f, 0.125f, still.getMaxU(), still.getMaxV(), colorRGB,1.0f);
                add(buffer, matrixStackIn, 0.125f, 0.125f, 0.125f, still.getMinU(), still.getMaxV(), colorRGB,1.0f);

                add(buffer, matrixStackIn, 0.125f, 0.125f, 0.875f, still.getMinU(), still.getMinV() + vHeight, colorRGB,1.0f);
                add(buffer, matrixStackIn, 0.875f, 0.125f, 0.875f, still.getMaxU(), still.getMinV() + vHeight, colorRGB,1.0f);
                add(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.875f, still.getMaxU(), still.getMaxV(), colorRGB,1.0f);
                add(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.875f, still.getMinU(), still.getMaxV(), colorRGB,1.0f);

/*                add(buffer, matrixStackIn, 0.125f, 0.125f, 0.125f, still.getMinU(), still.getMinV(), colorRGBA);
                add(buffer, matrixStackIn, 0.125f, 0.125f, 0.875f, still.getMaxU(), still.getMinV(), colorRGBA);
                add(buffer, matrixStackIn, 0.875f, 0.125f, 0.875f, still.getMaxU(), still.getMaxV(), colorRGBA);
                add(buffer, matrixStackIn, 0.875f, 0.125f, 0.125f, still.getMinU(), still.getMaxV(), colorRGBA);*/

            }
        });
        GlStateManager.enableCull();
        matrixStackIn.pop();
    }

    private void add(IVertexBuilder renderer, MatrixStack stack, float x, float y, float z, float u, float v) {
        renderer.pos(stack.getLast().getMatrix(), x, y, z)
                .color(1.0f, 1.0f, 1.0f, 1.0f)
                .tex(u, v)
                .lightmap(0, 240)
                .normal(1, 0, 0)
                .endVertex();
    }

    private void add(IVertexBuilder renderer, MatrixStack stack, float x, float y, float z, float u, float v, int RGB, float alpha) {
        float red = ((RGB >> 16) & 0xFF) / 255f;
        float green = ((RGB >> 8) & 0xFF) / 255f;
        float blue = ((RGB >> 0) & 0xFF) / 255f;
        renderer.pos(stack.getLast().getMatrix(), x, y, z)
                .color(red, green, blue, alpha)
                .tex(u, v)
                .lightmap(0, 240)
                .normal(1, 0, 0)
                .endVertex();
    }
    private void add(IVertexBuilder renderer, MatrixStack stack, float x, float y, float z, float u, float v, float colorR, float colorG, float colorB, float alpha) {
        renderer.pos(stack.getLast().getMatrix(), x, y, z)
                .color(colorR, colorG, colorB, alpha)
                .tex(u, v)
                .lightmap(0, 240)
                .normal(1, 0, 0)
                .endVertex();
    }
}
