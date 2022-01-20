package gloridifice.watersource.client.render.tile;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import gloridifice.watersource.common.block.entity.WaterFilterUpBlockEntity;
import gloridifice.watersource.common.item.StrainerBlockItem;
import gloridifice.watersource.helper.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class WaterFilterUpTER implements BlockEntityRenderer<WaterFilterUpBlockEntity> {
    public WaterFilterUpTER(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(WaterFilterUpBlockEntity blockEntity, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        //Render strainer
        long gameTime = mc.level.getGameTime();
        double animationTime = (double) gameTime + (double) partialTicks;
        int tick = blockEntity.getProcessTicks();
        blockEntity.getProps().ifPresent(itemStackHandler -> {
            if (itemStackHandler.getStackInSlot(0).isEmpty()) return;
            float speed = 0;
            if (blockEntity.getStrainer().map(data -> data.getStackInSlot(0)).orElse(ItemStack.EMPTY).isEmpty()) {
                if (!blockEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).map(data -> data.getFluidInTank(0).isEmpty()).orElse(true))
                    speed = 10f;
            }
            matrixStackIn.pushPose();
            matrixStackIn.translate(0.5, 0.4 + Math.sin((float) tick / (20f - speed / 2f)) / 13f, 0.5);
            matrixStackIn.mulPose(new Quaternion(0f, (float) tick / (30f - speed), 0f, false));
            mc.getItemRenderer().renderStatic(itemStackHandler.getStackInSlot(0), ItemTransforms.TransformType.GROUND, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, 1);
            matrixStackIn.popPose();
        });
        blockEntity.getStrainer().ifPresent(itemStackHandler -> {
            matrixStackIn.pushPose();
            matrixStackIn.translate(0, -0.125, 0);
            if (!itemStackHandler.getStackInSlot(0).isEmpty() && itemStackHandler.getStackInSlot(0).getItem() instanceof StrainerBlockItem) {
                Block block = Block.byItem(itemStackHandler.getStackInSlot(0).getItem());
                BlockRenderDispatcher blockRenderer = mc.getBlockRenderer();
                blockRenderer.renderSingleBlock(block.defaultBlockState(), matrixStackIn, bufferIn, 60, combinedOverlayIn, EmptyModelData.INSTANCE);
            }
            matrixStackIn.popPose();
        });
        VertexConsumer buffer = bufferIn.getBuffer(RenderType.translucentNoCrumbling());

        blockEntity.getUpTank().ifPresent(fluidTankUp -> {
            if (!fluidTankUp.isEmpty()) {

                FluidStack fluidStackUp = fluidTankUp.getFluid();
                TextureAtlasSprite still = mc.getBlockRenderer().getBlockModelShaper().getTexture(fluidStackUp.getFluid().defaultFluidState().createLegacyBlock(), blockEntity.getLevel(), blockEntity.getBlockPos());
                int colorRGB = fluidStackUp.getFluid().getAttributes().getColor();
                float alpha = 1.0f;
                float height = (float) fluidStackUp.getAmount() / (float) fluidTankUp.getCapacity() * 0.75f;
                float vHeight = (still.getV1() - still.getV0()) * (1f - (float) fluidStackUp.getAmount() / (float) fluidTankUp.getCapacity());
                matrixStackIn.pushPose();
                //GlStateManager.disableCull();
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
                matrixStackIn.popPose();
/*              RenderHelper.addVertex(buffer, matrixStackIn, 0.125f, 0.126f, 0.125f, still.getU0(), still.getV0(), colorRGBA);
                RenderHelper.addVertex(buffer, matrixStackIn, 0.125f, 0.126f, 0.875f, still.getU1(), still.getV0(), colorRGBA);
                RenderHelper.addVertex(buffer, matrixStackIn, 0.875f, 0.126f, 0.875f, still.getU1(), still.getV1(), colorRGBA);
                RenderHelper.addVertex(buffer, matrixStackIn, 0.875f, 0.126f, 0.125f, still.getU0(), still.getV1(), colorRGBA);*/
                //render capacity text
                Vec3i vec3i = new Vec3i(player.getPosition(0f).x() - blockEntity.getBlockPos().getX(), player.getPosition(0f).y() - blockEntity.getBlockPos().getY(), player.getPosition(0f).z() - blockEntity.getBlockPos().getZ());
                Direction direction = Direction.getNearest(vec3i.getX(), vec3i.getY(), vec3i.getZ());
                //flag update
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
                //animation
                
                //Font font = this.renderDispatcher.fontRenderer;
                Font font = mc.font;
                String s = fluidTankUp.getFluidAmount() + "mB/" + fluidTankUp.getCapacity() + "mB";
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
                double a = (double) mc.font.width(s) / 200 * ra;
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
                font.draw(matrixStackIn, s, 0F, 0F, 0xFFFFFF/*, false, bufferIn, false, 0, combinedLightIn*/);
                matrixStackIn.popPose();
                previousIsIn = vec3i.distSqr(new Vec3i(0,0,0)) <= 6;
                blockEntity.setCacheTimeEnter(cacheTimeEnter);
                blockEntity.setCacheTimeExit(cacheTimeExit);
                blockEntity.setLeftAnimeEnd(isLeftAnimeEnd);
                blockEntity.setPreviousIsIn(previousIsIn);
            }
        });
        //GlStateManager.enableCull();
    }
}
