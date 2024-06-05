package xyz.koiro.watersource.api

import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.minecraft.client.MinecraftClient
import net.minecraft.client.texture.Sprite
import net.minecraft.fluid.Fluid
import net.minecraft.util.math.BlockPos

object FluidRenderUtils {
    fun getFluidColor(fluid: Fluid): Int? {
        return FluidRenderHandlerRegistry.INSTANCE?.get(fluid)
            ?.getFluidColor(MinecraftClient.getInstance().world, BlockPos.ORIGIN, fluid.defaultState)
    }
    fun getFluidSprite(fluid: Fluid): Array<Sprite>? {
        return FluidRenderHandlerRegistry.INSTANCE?.get(fluid)
            ?.getFluidSprites(MinecraftClient.getInstance().world, BlockPos.ORIGIN, fluid.defaultState)
    }
}