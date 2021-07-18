package gloridifice.watersource.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.item.StrainerBlockItem;
import gloridifice.watersource.common.tile.WaterFilterUpTile;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class WaterFilterUpTER extends TileEntityRenderer<WaterFilterUpTile> {
    public WaterFilterUpTER(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }


    @Override
    public void render(WaterFilterUpTile tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;
        //Render strainer
        int tick = tileEntityIn.getProcessTicks();
        tileEntityIn.getProps().ifPresent(itemStackHandler -> {
            //todo 
            if (itemStackHandler.getStackInSlot(0).isEmpty())return;
            float speed = 0;
            if (tileEntityIn.getStrainer().map(data -> data.getStackInSlot(0)).orElse(ItemStack.EMPTY).isEmpty()){
                if (!tileEntityIn.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).map(data -> data.getFluidInTank(0).isEmpty()).orElse(true))
                speed = 10f;
            }
            matrixStackIn.push();
            matrixStackIn.translate(0.5,0.4 + Math.sin((float)tick / (20f - speed/2f))/13f,0.5);
            matrixStackIn.rotate(new Quaternion(0f, (float) tick/(30f - speed) ,0f,false));
            mc.getItemRenderer().renderItem(itemStackHandler.getStackInSlot(0), ItemCameraTransforms.TransformType.GROUND,combinedLightIn,combinedOverlayIn,matrixStackIn,bufferIn);
            matrixStackIn.pop();
        });
        tileEntityIn.getStrainer().ifPresent(itemStackHandler -> {
            matrixStackIn.push();
            matrixStackIn.translate(0,-0.125,0);
            if (!itemStackHandler.getStackInSlot(0).isEmpty() && itemStackHandler.getStackInSlot(0).getItem() instanceof StrainerBlockItem){
                Block block = Block.getBlockFromItem(itemStackHandler.getStackInSlot(0).getItem());
                BlockRendererDispatcher rendererDispatcher = mc.getBlockRendererDispatcher();
                rendererDispatcher.renderBlock(block.getDefaultState(),matrixStackIn,bufferIn,60,combinedOverlayIn, EmptyModelData.INSTANCE);
            }
            matrixStackIn.pop();
        });
        IVertexBuilder buffer = bufferIn.getBuffer(RenderType.getTranslucent());

        tileEntityIn.getUpTank().ifPresent(fluidTankUp -> {
            if (!fluidTankUp.isEmpty()) {

                FluidStack fluidStackUp = fluidTankUp.getFluid();
                TextureAtlasSprite still = mc.getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(fluidStackUp.getFluid().getAttributes().getStillTexture());
                int colorRGB = fluidStackUp.getFluid().getAttributes().getColor();
                float alpha = 1.0f;
                float height = (float) fluidStackUp.getAmount() / (float) fluidTankUp.getCapacity() * 0.75f;
                float vHeight = (still.getMaxV() - still.getMinV()) * (1f - (float) fluidStackUp.getAmount() / (float) fluidTankUp.getCapacity());
                matrixStackIn.push();
                //GlStateManager.disableCull();
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
                matrixStackIn.pop();
/*                add(buffer, matrixStackIn, 0.125f, 0.126f, 0.125f, still.getMinU(), still.getMinV(), colorRGBA);
                add(buffer, matrixStackIn, 0.125f, 0.126f, 0.875f, still.getMaxU(), still.getMinV(), colorRGBA);
                add(buffer, matrixStackIn, 0.875f, 0.126f, 0.875f, still.getMaxU(), still.getMaxV(), colorRGBA);
                add(buffer, matrixStackIn, 0.875f, 0.126f, 0.125f, still.getMinU(), still.getMaxV(), colorRGBA);*/
                Vector3d vector3d = new Vector3d(player.getPosition().getX() - tileEntityIn.getPos().getX(), player.getPosition().getY() - tileEntityIn.getPos().getY(),player.getPosition().getZ() - tileEntityIn.getPos().getZ());
                Direction direction = Direction.getFacingFromVector(vector3d.x,vector3d.y,vector3d.z);
                FontRenderer fontRenderer = this.renderDispatcher.fontRenderer;
                String s = fluidTankUp.getFluidAmount() + "mB/" + fluidTankUp.getCapacity() + "mB";
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
                fontRenderer.renderString(s,0F,0F,0xFFFFFF,false,matrixStackIn.getLast().getMatrix(), bufferIn,false,0, combinedLightIn);
                matrixStackIn.pop();
            }
        });
        //GlStateManager.enableCull();
    }
    private void add(IVertexBuilder renderer, MatrixStack stack, float x, float y, float z, float u, float v) {
        renderer.pos(stack.getLast().getMatrix(), x, y, z)
                .color(1.0f, 1.0f, 1.0f, 1.0f)
                .tex(u, v)
                .lightmap(0, 240)
                .normal(1, 0, 0)
                .endVertex();
    }

    private void add(IVertexBuilder renderer, MatrixStack stack, float x, float y, float z, float u, float v, int RGBA, float alpha) {
        float red = ((RGBA >> 16) & 0xFF) / 255f;
        float green = ((RGBA >> 8) & 0xFF) / 255f;
        float blue = ((RGBA >> 0) & 0xFF) / 255f;
        renderer.pos(stack.getLast().getMatrix(), x, y, z)
                .color(red, green, blue, alpha)
                .tex(u, v)
                .lightmap(0, 240)
                .normal(0, 0, 0)
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
