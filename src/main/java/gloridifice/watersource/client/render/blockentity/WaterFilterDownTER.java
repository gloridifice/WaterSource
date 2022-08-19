package gloridifice.watersource.client.render.blockentity;

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

                //render capacity text
                String text = fluidTankDown.getFluidAmount() + "mB/" + fluidTankDown.getCapacity() + "mB";
                RenderHelper.renderFluidAmount(blockEntity, player, text, matrixStackIn, partialTicks, 1.5f);
            }
        });

    }
    
}