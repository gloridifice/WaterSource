@file:Suppress("UNREACHABLE_CODE", "UnstableApiUsage")

package xyz.koiro.watersource

import net.fabricmc.api.ClientModInitializer
import xyz.koiro.watersource.hud.ModClientHUD
import xyz.koiro.watersource.network.ModClientNetworking

object WaterSourceClient : ClientModInitializer {
	override fun onInitializeClient() {
		ModClientNetworking.initialize()
		ModClientHUD.initialize()
	}
}