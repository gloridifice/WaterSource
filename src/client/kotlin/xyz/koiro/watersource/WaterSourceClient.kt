@file:Suppress("UNREACHABLE_CODE", "UnstableApiUsage")

package xyz.koiro.watersource

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler
import net.minecraft.client.render.RenderLayer
import net.minecraft.util.Identifier
import xyz.koiro.watersource.render.blockentity.ModBlockEntityRenderers
import xyz.koiro.watersource.render.color.ModItemColorProviders
import xyz.koiro.watersource.config.ModConfigLoader
import xyz.koiro.watersource.event.ModClientItemTooltipEventHandlers
import xyz.koiro.watersource.render.hud.ModClientHUD
import xyz.koiro.watersource.network.ModClientNetworking
import xyz.koiro.watersource.render.ModFluidAndBlockRenderRegistry
import xyz.koiro.watersource.world.block.ModBlocks
import xyz.koiro.watersource.world.fluid.ModFluids

object WaterSourceClient : ClientModInitializer {
    override fun onInitializeClient() {
        ModItemColorProviders.initialize()
        ModClientNetworking.initialize()
        ModClientHUD.initialize()
        ModClientItemTooltipEventHandlers.initialize()
        ModBlockEntityRenderers.initialize()
        ModFluidAndBlockRenderRegistry.initialize()
        WSClientConfig.reload()
    }
}