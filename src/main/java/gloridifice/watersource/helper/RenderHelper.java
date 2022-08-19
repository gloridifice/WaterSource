package gloridifice.watersource.helper;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import gloridifice.watersource.common.block.entity.SimpleAnimationBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class RenderHelper {
    public static void addVertex(VertexConsumer renderer, PoseStack stack, float x, float y, float z, float u, float v, int RGBA, float alpha) {
        float red = ((RGBA >> 16) & 0xFF) / 255f;
        float green = ((RGBA >> 8) & 0xFF) / 255f;
        float blue = ((RGBA >> 0) & 0xFF) / 255f;
        renderer.vertex(stack.last().pose(), x, y, z).color(red, green, blue, alpha).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880)/*.lightmap(0, 240)*/.normal(stack.last().normal(),0, 1.0F, 0).endVertex();
    }
    public static void renderFluidAmount(SimpleAnimationBlockEntity blockEntity, Player player, String text, PoseStack matrixStackIn, float partialTicks, float animDuration){
        Minecraft mc = Minecraft.getInstance();

        //render capacity text
        Vec3 dis =  player.getPosition(0f).subtract(Vec3.atBottomCenterOf(blockEntity.getBlockPos()));
        Direction direction = Direction.getNearest(dis.x(), dis.y(), dis.z());
        float duration = animDuration * 20;

        float animTime = blockEntity.getAnimTime();
        float scale = blockEntity.getScale();
        Quaternion quaternion = blockEntity.getQuaternion();

        boolean isIn = dis.length() <= 4;

        if (isIn){
            animTime = Math.min(duration, animTime + partialTicks);
        }else {
            animTime = Math.max(0, animTime - partialTicks);
        }
        //animation
        matrixStackIn.pushPose();

        scale = lerp(0f, 1f, smoothStep(0f,1f,animTime / duration));

        float a = (float) mc.font.width(text) / 200 * scale;
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

        matrixStackIn.scale(0.010416667F * scale, -0.010416667F * Math.abs(scale) , 0.010416667F * scale);

        Font font = mc.font;
        font.draw(matrixStackIn, text, 0F, 0F, 0xFFFFFF/*, false, bufferIn, false, 0, combinedLightIn*/);

        matrixStackIn.popPose();

/*
        matrixStackIn.pushPose();

        matrixStackIn.translate(0.5f, 0.3f, 0.5f);
        matrixStackIn.scale(0.02f * scale, 0.02f * scale, 0.02f * scale);

        matrixStackIn.mulPose(mc.getEntityRenderDispatcher().cameraOrientation());
        matrixStackIn.mulPose(new Quaternion(0,0,180, true));

        font.draw(matrixStackIn, "          "  + text, 0F, 0F, 0xFFFFFF*/
/*, false, bufferIn, false, 0, combinedLightIn*//*
);
        matrixStackIn.popPose();

        blockEntity.setAnimTime(animTime);
*/

    }

    public static float lerp(float a , float b, float f){
        return a + f * (b - a);
    }

    public static float smoothStep(float edge0, float edge1, float x) {
        // Scale, bias and saturate x to 0..1 range
        x = clamp((x - edge0) / (edge1 - edge0), 0.0f, 1.0f);
        // Evaluate polynomial
        return x * x * (3 - 2 * x);
    }

    public static float clamp(float x, float lowerLimit, float upperLimit) {
        if (x < lowerLimit)
            x = lowerLimit;
        if (x > upperLimit)
            x = upperLimit;
        return x;
    }

}
