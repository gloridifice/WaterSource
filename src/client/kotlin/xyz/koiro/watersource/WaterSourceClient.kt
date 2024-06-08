@file:Suppress("UNREACHABLE_CODE", "UnstableApiUsage")

package xyz.koiro.watersource

import net.fabricmc.api.ClientModInitializer
import xyz.koiro.watersource.render.blockentity.ModBlockEntityRenderers
import xyz.koiro.watersource.render.color.ModItemColorProviders
import xyz.koiro.watersource.render.ModModels
import xyz.koiro.watersource.event.ItemTooltipEventHandlers
import xyz.koiro.watersource.render.hud.ModClientHUD
import xyz.koiro.watersource.network.ModClientNetworking
import xyz.koiro.watersource.render.ModFluidAndBlockRenderRegistry

object WaterSourceClient : ClientModInitializer {
    override fun onInitializeClient() {
        ModItemColorProviders.initialize()
        ModClientNetworking.initialize()
        ModClientHUD.initialize()
        ModBlockEntityRenderers.initialize()
        ModFluidAndBlockRenderRegistry.initialize()

        // Events
        ItemTooltipEventHandlers.initialize()
        ModModels.initialize()

        // Config
        WSClientConfig.reload()
    }
}