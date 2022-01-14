package gloridifice.watersource.client.render.tile;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import gloridifice.watersource.common.block.entity.WaterFilterDownBlockEntity;
import gloridifice.watersource.helper.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fluids.FluidStack;

public class WaterFilterDownTER implements BlockEntityRenderer<WaterFilterDownBlockEntity> {
    public WaterFilterDownTER(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(WaterFilterDownBlockEntity blockEntity, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Minecraft mc = Minecraft.getInstance();
        VertexConsumer buffer = bufferIn.getBuffer(RenderType.translucentNoCrumbling());
        Player player = mc.player;
        long gameTime = mc.level.getGameTime();
        double animationTime = (double) gameTime + (double) partialTicks;
        blockEntity.getDownTank().ifPresent(fluidTankDown -> {
            if (!fluidTankDown.isEmpty()) {
                FluidStack fluidStackDown = fluidTankDown.getFluid();
                TextureAtlasSprite still = mc.getBlockRenderer().getBlockModelShaper().getTexture(fluidStackDown.getFluid().defaultFluidState().createLegacyBlock(), blockEntity.getLevel(), blockEntity.getBlockPos());
                int colorRGB = fluidStackDown.getFluid().getAttributes().getColor();

                float height = (float) fluidStackDown.getAmount() / (float) fluidTankDown.getCapacity() * 0.75f;
                float vHeight = (still.getV1() - still.getV0()) * (1f - (float) fluidStackDown.getAmount() / (float) fluidTankDown.getCapacity());
                matrixStackIn.pushPose();
                GlStateManager._disableCull();
                RenderHelper.addVertex(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.125f, still.getU0(), still.getV0(), colorRGB, 1.0f);
                RenderHelper.addVertex(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.875f, still.getU1(), still.getV0(), colorRGB, 1.0f);
                RenderHelper.addVertex(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.875f, still.getU1(), still.getV1(), colorRGB, 1.0f);
                RenderHelper.addVertex(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.125f, still.getU0(), still.getV1(), colorRGB, 1.0f);

                RenderHelper.addVertex(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.125f, still.getU0(), still.getV0() + vHeight, colorRGB, 1.0f);
                RenderHelper.addVertex(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.875f, still.getU1(), still.getV0() + vHeight, colorRGB, 1.0f);
                RenderHelper.addVertex(buffer, matrixStackIn, 0.875f, 0.125f, 0.875f, still.getU1(), still.getV1(), colorRGB, 1.0f);
                RenderHelper.addVertex(buffer, matrixStackIn, 0.875f, 0.125f, 0.125f, still.getU0(), still.getV1(), colorRGB, 1.0f);

                RenderHelper.addVertex(buffer, matrixStackIn, 0.125f, 0.125f, 0.125f, still.getU0(), still.getV0(), colorRGB, 1.0f);
                RenderHelper.addVertex(buffer, matrixStackIn, 0.125f, 0.125f, 0.875f, still.getU1(), still.getV0(), colorRGB, 1.0f);
                RenderHelper.addVertex(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.875f, still.getU1(), still.getV1() - vHeight, colorRGB, 1.0f);
                RenderHelper.addVertex(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.125f, still.getU0(), still.getV1() - vHeight, colorRGB, 1.0f);

                RenderHelper.addVertex(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.125f, still.getU0(), still.getV0() + vHeight, colorRGB, 1.0f);
                RenderHelper.addVertex(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.125f, still.getU1(), still.getV0() + vHeight, colorRGB, 1.0f);
                RenderHelper.addVertex(buffer, matrixStackIn, 0.875f, 0.125f, 0.125f, still.getU1(), still.getV1(), colorRGB, 1.0f);
                RenderHelper.addVertex(buffer, matrixStackIn, 0.125f, 0.125f, 0.125f, still.getU0(), still.getV1(), colorRGB, 1.0f);

                RenderHelper.addVertex(buffer, matrixStackIn, 0.125f, 0.125f, 0.875f, still.getU0(), still.getV0() + vHeight, colorRGB, 1.0f);
                RenderHelper.addVertex(buffer, matrixStackIn, 0.875f, 0.125f, 0.875f, still.getU1(), still.getV0() + vHeight, colorRGB, 1.0f);
                RenderHelper.addVertex(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.875f, still.getU1(), still.getV1(), colorRGB, 1.0f);
                RenderHelper.addVertex(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.875f, still.getU0(), still.getV1(), colorRGB, 1.0f);
                GlStateManager._enableCull();
                matrixStackIn.popPose();
                Vec3i vec3i = new Vec3i(player.getPosition(0f).x() - blockEntity.getBlockPos().getX(), player.getPosition(0f).y() - blockEntity.getBlockPos().getY(), player.getPosition(0f).z() - blockEntity.getBlockPos().getZ());
                Direction direction = Direction.getNearest(vec3i.getX(), vec3i.getY(), vec3i.getZ());
                //flag检测
                double cacheTimeEnter = blockEntity.getCacheTimeEnter();
                double cacheTimeExit = blockEntity.getCacheTimeExit();
                boolean previousIsIn = blockEntity.isPreviousIsIn();
                boolean isLeftAnimeEnd = blockEntity.isLeftAnimeEnd();

                boolean isIn = vec3i.distSqr(new Vec3i(0,0,0)) <= 6;
                if (!previousIsIn && isIn) {
                    cacheTimeEnter = animationTime;
                }
                else if (!isIn && previousIsIn) {
                    cacheTimeExit = animationTime;
                    isLeftAnimeEnd = false;
                }
                //动画实现
                Font font = mc.font;
                String s = fluidTankDown.getFluidAmount() + "mB/" + fluidTankDown.getCapacity() + "mB";
                matrixStackIn.pushPose();
                double animeTime = 0.5d;//单位秒
                double needTicks = animeTime * 20;
                double ra = 0d;
                if (animationTime - cacheTimeEnter <= needTicks) {
                    ra += Math.sin(3.1415d / (2d * needTicks) * (animationTime - cacheTimeEnter));
                }
                else ra += 1d;
                if (animationTime - cacheTimeExit <= needTicks) {
                    ra -= Math.sin(3.1415d / (2d * needTicks) * (animationTime - cacheTimeExit));
                }
                else if (isLeftAnimeEnd && !isIn) ra -= 1d;
                if (Math.abs(animationTime - cacheTimeExit - needTicks) <= 0.5d) isLeftAnimeEnd = true;

                double a = (double) font.width(s) / 200 * ra;
                switch (direction) {
                    case SOUTH:
                        matrixStackIn.translate(0.5 - a, 0.25, 1.05);
                        break;
                    case NORTH:
                        matrixStackIn.mulPose(new Quaternion(0, 180, 0, true));
                        matrixStackIn.translate(-0.5 - a, 0.25, 0.1);
                        break;
                    case EAST:
                        matrixStackIn.mulPose(new Quaternion(0, 90, 0, true));
                        matrixStackIn.translate(-0.5 - a, 0.25, 1.05);
                        break;
                    case WEST:
                        matrixStackIn.mulPose(new Quaternion(0, 270, 0, true));
                        matrixStackIn.translate(0.5 - a, 0.25, 0.05);
                        break;
                }
                matrixStackIn.scale(0.010416667F * (float) ra, -0.010416667F * (float) ra, 0.010416667F * (float) ra);
                font.draw(matrixStackIn, s, 0F, 0F, 0xFFFFFF/*, false , bufferIn, false, 0, combinedLightIn*/);
                matrixStackIn.popPose();
                previousIsIn = vec3i.distSqr(new Vec3i(0,0,0)) <= 6;
                blockEntity.setCacheTimeEnter(cacheTimeEnter);
                blockEntity.setCacheTimeExit(cacheTimeExit);
                blockEntity.setLeftAnimeEnd(isLeftAnimeEnd);
                blockEntity.setPreviousIsIn(previousIsIn);
            }
        });

    }
    
}