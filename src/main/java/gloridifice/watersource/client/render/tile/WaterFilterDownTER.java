package gloridifice.watersource.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import gloridifice.watersource.common.tile.WaterFilterDownTile;
import gloridifice.watersource.common.tile.WaterFilterUpTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fluids.FluidStack;

public class WaterFilterDownTER extends TileEntityRenderer<WaterFilterDownTile> {
    public WaterFilterDownTER(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(WaterFilterDownTile tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Minecraft mc = Minecraft.getInstance();
        IVertexBuilder buffer = bufferIn.getBuffer(RenderType.getTranslucentNoCrumbling());
        PlayerEntity player = mc.player;
        tileEntityIn.getDownTank().ifPresent(fluidTankDown -> {
            if (!fluidTankDown.isEmpty()) {
                FluidStack fluidStackDown = fluidTankDown.getFluid();
                TextureAtlasSprite still = mc.getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(fluidStackDown.getFluid().getAttributes().getStillTexture());
                int colorRGB = fluidStackDown.getFluid().getAttributes().getColor();

                float height = (float) fluidStackDown.getAmount() / (float) fluidTankDown.getCapacity() * 0.75f;
                float vHeight = (still.getMaxV() - still.getMinV()) * (1f - (float) fluidStackDown.getAmount() / (float) fluidTankDown.getCapacity());
                matrixStackIn.push();
                GlStateManager.disableCull();
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
                GlStateManager.enableCull();
                matrixStackIn.pop();
/*                add(buffer, matrixStackIn, 0.125f, 0.125f, 0.125f, still.getMinU(), still.getMinV(), colorRGBA);
                add(buffer, matrixStackIn, 0.125f, 0.125f, 0.875f, still.getMaxU(), still.getMinV(), colorRGBA);
                add(buffer, matrixStackIn, 0.875f, 0.125f, 0.875f, still.getMaxU(), still.getMaxV(), colorRGBA);
                add(buffer, matrixStackIn, 0.875f, 0.125f, 0.125f, still.getMinU(), still.getMaxV(), colorRGBA);*/
                Vector3d vector3d = new Vector3d(player.getPosition().getX() - tileEntityIn.getPos().getX(), player.getPosition().getY() - tileEntityIn.getPos().getY(),player.getPosition().getZ() - tileEntityIn.getPos().getZ());
                Direction direction = Direction.getFacingFromVector(vector3d.x,vector3d.y,vector3d.z);
                FontRenderer fontRenderer = this.renderDispatcher.fontRenderer;
                String s = fluidTankDown.getFluidAmount() + "mB/" + fluidTankDown.getCapacity() + "mB";
                matrixStackIn.push();

                switch (direction){
                    case SOUTH:
                        matrixStackIn.translate(0.1,0.25,1.05);
                        break;
                    case NORTH:
                        matrixStackIn.rotate(new Quaternion(0,180,0,true));
                        matrixStackIn.translate(-0.9,0.25,0.1);
                        break;
                    case EAST:
                        matrixStackIn.rotate(new Quaternion(0,90,0,true));
                        matrixStackIn.translate(-0.9,0.25,1.05);
                        break;
                    case WEST:
                        matrixStackIn.rotate(new Quaternion(0,270,0,true));
                        matrixStackIn.translate(0.1,0.25,0.05);
                        break;
                }
                matrixStackIn.scale(0.010416667F, -0.010416667F, 0.010416667F);
                fontRenderer.renderString(s,0f,0.25f,0xFFFFFF,false,matrixStackIn.getLast().getMatrix(),bufferIn,false,0,combinedLightIn);
                matrixStackIn.pop();
            }
        });

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
