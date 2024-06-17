package xyz.koiro.watersource.render.blockentity

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.model.json.ModelTransformationMode
import net.minecraft.client.util.math.MatrixStack
import org.joml.AxisAngle4f
import org.joml.Math
import org.joml.Quaternionf
import org.joml.Vector2f
import org.joml.Vector3f
import xyz.koiro.watersource.api.FluidCubeRenderUtils.fluidCube
import xyz.koiro.watersource.api.FluidRenderUtils
import xyz.koiro.watersource.api.VectorUtils.newY
import xyz.koiro.watersource.world.block.entity.FilterBlockEntity

class FilterBERenderer : BlockEntityRenderer<FilterBlockEntity> {
    override fun render(
        entity: FilterBlockEntity,
        tickDelta: Float,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int,
        overlay: Int
    ) {
        val mc = MinecraftClient.getInstance()
        val blockState = entity.cachedState
        val renderCtx = entity.renderCtx
        val fluid = entity.fluidStorageData.fluid
        val amount = entity.fluidStorageData.amount
        val capacity = entity.fluidStorageData.capacity

        if (entity.isUp) {
            matrices.push()
            matrices.translate(0.5f, -0.1f, 0.5f)
            if (entity.isWorking) {
                renderCtx.strainerRotY += tickDelta * 0.1f
            }
            matrices.multiply(Quaternionf(AxisAngle4f(renderCtx.strainerRotY, newY())))
            val strainer = entity.getStrainerStorage(mc.world!!)?.stack ?: return
            mc.itemRenderer.renderItem(
                strainer,
                ModelTransformationMode.GROUND,
                light,
                0,
                matrices,
                vertexConsumers,
                mc.world,
                42
            )
            matrices.pop()
        }

        if (!entity.fluidStorageData.isBlank()) renderCtx.fluidCache = fluid

        val fluidCache = renderCtx.fluidCache ?: return
        val fluidSprite = FluidRenderUtils.getFluidSprite(fluidCache)?.getOrNull(0) ?: return
        val fluidColor = FluidRenderUtils.getFluidColor(fluidCache) ?: return

        matrices.push()
        RenderSystem.enableBlend()
        val consumer = vertexConsumers.getBuffer(RenderLayer.getGlintTranslucent())
        val u0 = fluidSprite.minU
        val v0 = fluidSprite.minV
        val u1 = fluidSprite.maxU
        val v1 = fluidSprite.maxV

        val size = Vector3f(0.75f).mul(Vector3f(1f, renderCtx.heightRatio, 1f))
        val alpha = if (renderCtx.targetHeightRatio == 0f) (renderCtx.heightRatio / 0.1f).coerceIn(0f, 1f) else 1f
        consumer.fluidCube(
            matrices,
            center = Vector3f(0.5f, 0.125f, 0.5f).add(newY(size.y / 2f)),
            size = size,
            unitSizeOfTex = Vector3f(1f),
            minUV = Vector2f(u0, v0),
            maxUV = Vector2f(u1, v1),
            fluidColor,
            alpha
        )

        RenderSystem.disableBlend()
        matrices.pop()

        renderCtx.targetHeightRatio = amount.toFloat() / capacity.toFloat()
        renderCtx.heightRatio = Math.lerp(renderCtx.heightRatio, renderCtx.targetHeightRatio, 0.15f * tickDelta)
        if (renderCtx.heightRatio < 0.01f) {
            renderCtx.fluidCache = null
            renderCtx.heightRatio = 0f
        }
    }
}