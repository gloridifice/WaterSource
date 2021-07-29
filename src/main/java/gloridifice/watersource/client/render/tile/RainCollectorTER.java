package gloridifice.watersource.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import gloridifice.watersource.common.tile.RainCollectorTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraftforge.fluids.FluidStack;

public class RainCollectorTER extends TileEntityRenderer<RainCollectorTile> {
    public RainCollectorTER(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(RainCollectorTile rainCollector, float v, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int i, int i1) {
        Minecraft mc = Minecraft.getInstance();

        IVertexBuilder buffer = bufferIn.getBuffer(RenderType.getTranslucent());
        rainCollector.getTank().ifPresent(tank -> {
            FluidStack tankFluid = tank.getFluid();
            TextureAtlasSprite still = mc.getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(tankFluid.getFluid().getAttributes().getStillTexture());
            int colorRGB = tankFluid.getFluid().getAttributes().getColor();
            float alpha = 1.0f;
            float height = (float) tankFluid.getAmount() / (float) tank.getCapacity() * 0.75f;
            float vHeight = (still.getMaxV() - still.getMinV()) * (1f - (float) tankFluid.getAmount() / (float) tank.getCapacity());
            matrixStackIn.push();
            //GlStateManager.disable Cull();

            GlStateManager.enableBlend();
            GlStateManager.enableAlphaTest();

            add(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.125f, still.getMinU(), still.getMinV(), colorRGB, alpha);
            add(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.875f, still.getMaxU(), still.getMinV(), colorRGB, alpha);
            add(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.875f, still.getMaxU(), still.getMaxV(), colorRGB, alpha);
            add(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.125f, still.getMinU(), still.getMaxV(), colorRGB, alpha);

            add(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.125f, still.getMinU(), still.getMinV() + vHeight, colorRGB, alpha);
            add(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.875f, still.getMaxU(), still.getMinV() + vHeight, colorRGB, alpha);
            add(buffer, matrixStackIn, 0.875f, 0.125f, 0.875f, still.getMaxU(), still.getMaxV(), colorRGB, alpha);
            add(buffer, matrixStackIn, 0.875f, 0.125f, 0.125f, still.getMinU(), still.getMaxV(), colorRGB, alpha);

            add(buffer, matrixStackIn, 0.125f, 0.125f, 0.125f, still.getMinU(), still.getMinV(), colorRGB, alpha);
            add(buffer, matrixStackIn, 0.125f, 0.125f, 0.875f, still.getMaxU(), still.getMinV(), colorRGB, alpha);
            add(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.875f, still.getMaxU(), still.getMaxV() - vHeight, colorRGB, alpha);
            add(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.125f, still.getMinU(), still.getMaxV() - vHeight, colorRGB, alpha);

            add(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.125f, still.getMinU(), still.getMinV() + vHeight, colorRGB, alpha);
            add(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.125f, still.getMaxU(), still.getMinV() + vHeight, colorRGB, alpha);
            add(buffer, matrixStackIn, 0.875f, 0.125f, 0.125f, still.getMaxU(), still.getMaxV(), colorRGB, alpha);
            add(buffer, matrixStackIn, 0.125f, 0.125f, 0.125f, still.getMinU(), still.getMaxV(), colorRGB, alpha);

            add(buffer, matrixStackIn, 0.125f, 0.125f, 0.875f, still.getMinU(), still.getMinV() + vHeight, colorRGB, alpha);
            add(buffer, matrixStackIn, 0.875f, 0.125f, 0.875f, still.getMaxU(), still.getMinV() + vHeight, colorRGB, alpha);
            add(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.875f, still.getMaxU(), still.getMaxV(), colorRGB, alpha);
            add(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.875f, still.getMinU(), still.getMaxV(), colorRGB, alpha);

            GlStateManager.disableBlend();
            GlStateManager.disableAlphaTest();
            matrixStackIn.pop();
        });
    }

    private void add(IVertexBuilder renderer, MatrixStack stack, float x, float y, float z, float u, float v, int RGBA, float alpha) {
        float red = ((RGBA >> 16) & 0xFF) / 255f;
        float green = ((RGBA >> 8) & 0xFF) / 255f;
        float blue = ((RGBA >> 0) & 0xFF) / 255f;
        renderer.pos(stack.getLast().getMatrix(), x, y, z).color(red, green, blue, alpha).tex(u, v).lightmap(0, 240).normal(0, 0, 0).endVertex();
    }
}
