package xyz.koiro.watersource

import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory
import xyz.koiro.watersource.data.ModResourceRegistries
import xyz.koiro.watersource.event.ModEventsRegistries
import xyz.koiro.watersource.world.recipe.ModRecipes
import xyz.koiro.watersource.world.item.ModItems

object WaterSource : ModInitializer {
    val LOGGER = LoggerFactory.getLogger("Water Source")
	const val MODID = "watersource"

	override fun onInitialize() {
		ModItems.active()
		ModRecipes.initialize()
		ModResourceRegistries.initialize()
		ModEventsRegistries.initialize()
	}

	fun identifier(name: String): Identifier = Identifier(MODID, name)
}