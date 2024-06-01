package xyz.koiro.watersource.color

import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.client.MinecraftClient
import net.minecraft.util.math.BlockPos
import xyz.koiro.watersource.api.getOrCreateFluidStorageData
import xyz.koiro.watersource.world.fluid.ModFluids
import xyz.koiro.watersource.world.item.ModItems

object ModItemColorProviders {
    fun initialize() {
        ColorProviderRegistry.ITEM.register({ stack, layer ->
            if (layer == 1) {
                stack.getOrCreateFluidStorageData()?.let {
                    FluidRenderHandlerRegistry.INSTANCE.get(it.fluid).getFluidColor(
                        MinecraftClient.getInstance().world,
                        BlockPos.ORIGIN,
                        ModFluids.PURIFIED_WATER.defaultState
                    )
                } ?: 0xFFFFFF
            } else {
                0xFFFFFF
            }
        }, ModItems.WOODEN_CUP)
    }
}