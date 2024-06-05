package xyz.koiro.watersource.render.blockentity

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.util.math.MatrixStack
import org.joml.Math
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

        val fluid = entity.fluidStorageData.fluid
        val amount = entity.fluidStorageData.amount
        val capacity = entity.fluidStorageData.capacity
        val renderCtx = entity.renderCtx

        if (!entity.fluidStorageData.isBlank()) renderCtx.fluidCache = fluid

        val fluidCache = renderCtx.fluidCache ?: return
        val fluidSprite = FluidRenderUtils.getFluidSprite(fluidCache)?.getOrNull(0) ?: return
        val fluidColor = FluidRenderUtils.getFluidColor(fluidCache) ?: return


        matrices.push()
        RenderSystem.enableBlend()
        val consumer = vertexConsumers.getBuffer(RenderLayer.getTranslucentNoCrumbling())
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

        renderCtx.targetHeightRatio = (amount / capacity).toFloat()
        renderCtx.heightRatio = Math.lerp(renderCtx.heightRatio, renderCtx.targetHeightRatio, 0.15f * tickDelta)
        if (renderCtx.heightRatio < 0.01f) {
            renderCtx.fluidCache = null
            renderCtx.heightRatio = 0f
        }
    }
}