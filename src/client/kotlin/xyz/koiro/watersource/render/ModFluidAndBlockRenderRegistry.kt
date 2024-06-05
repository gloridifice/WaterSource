package xyz.koiro.watersource.render

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler
import net.minecraft.client.render.RenderLayer
import net.minecraft.util.Identifier
import xyz.koiro.watersource.world.block.ModBlocks
import xyz.koiro.watersource.world.fluid.ModFluids

object ModFluidAndBlockRenderRegistry {
    fun initialize(){
        FluidRenderHandlerRegistry.INSTANCE.register(
            ModFluids.PURIFIED_WATER, ModFluids.FLOWING_PURIFIED_WATER, SimpleFluidRenderHandler(
                Identifier("minecraft:block/water_still"),
                Identifier("minecraft:block/water_flow"),
                0x62A9E7
            )
        )
        BlockRenderLayerMap.INSTANCE.putFluids(
            RenderLayer.getTranslucent(),
            ModFluids.PURIFIED_WATER,
            ModFluids.FLOWING_PURIFIED_WATER
        );
        BlockRenderLayerMap.INSTANCE.putBlocks(
            RenderLayer.getTranslucent(),
            ModBlocks.WOODEN_FILTER
        )
    }
}