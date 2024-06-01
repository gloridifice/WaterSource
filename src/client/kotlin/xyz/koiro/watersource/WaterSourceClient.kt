@file:Suppress("UNREACHABLE_CODE", "UnstableApiUsage")

package xyz.koiro.watersource

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler
import net.minecraft.client.render.RenderLayer
import net.minecraft.util.Identifier
import xyz.koiro.watersource.color.ModItemColorProviders
import xyz.koiro.watersource.event.ModClientEventHandlers
import xyz.koiro.watersource.event.ModClientEvents
import xyz.koiro.watersource.hud.ModClientHUD
import xyz.koiro.watersource.network.ModClientNetworking
import xyz.koiro.watersource.world.fluid.ModFluids

object WaterSourceClient : ClientModInitializer {
    override fun onInitializeClient() {
        ModItemColorProviders.initialize()
        ModClientNetworking.initialize()
        ModClientHUD.initialize()
        ModClientEventHandlers.initialize()

        FluidRenderHandlerRegistry.INSTANCE.register(
            ModFluids.PURIFIED_WATER, ModFluids.FLOWING_PURIFIED_WATER, SimpleFluidRenderHandler(
                Identifier("minecraft:block/water_still"),
                Identifier("minecraft:block/water_flow"),
                0x62A9E7
            )
        )
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.PURIFIED_WATER, ModFluids.FLOWING_PURIFIED_WATER);
    }
}