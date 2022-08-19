package gloridifice.watersource.client.render.blockentity;

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

                //render capacity text
                String text = fluidTankUp.getFluidAmount() + "mB/" + fluidTankUp.getCapacity() + "mB";
                RenderHelper.renderFluidAmount(blockEntity, player, text, matrixStackIn, partialTicks,1.5f);
            }
        });
    }
}
