package xyz.koiro.watersource

import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier
import net.minecraft.world.World
import org.slf4j.LoggerFactory
import xyz.koiro.watersource.data.ModResourceRegistries
import xyz.koiro.watersource.event.ModEventsHandlers
import xyz.koiro.watersource.world.block.ModBlocks
import xyz.koiro.watersource.world.effect.ModStatusEffects
import xyz.koiro.watersource.world.fluid.ModFluids
import xyz.koiro.watersource.world.item.ModFuelRegister
import xyz.koiro.watersource.world.recipe.ModRecipes
import xyz.koiro.watersource.world.item.ModItems

object WaterSource : ModInitializer {
    val LOGGER = LoggerFactory.getLogger("Water Source")
    const val MODID = "watersource"

    override fun onInitialize() {
        ModItems.active()
        ModFuelRegister.initialize()
        ModBlocks.active()
        ModFluids.active()
        ModStatusEffects.active()
        ModItemGroups.active()
        ModRecipes.initialize()
        ModResourceRegistries.initialize()
        ModEventsHandlers.initialize()
        ModCommands.initialize()
    }

    fun identifier(name: String): Identifier = Identifier(MODID, name)

    fun World.getWaterSourceDifficulty(): ModDifficulty {
        return this.difficulty?.let {
            when(it){
                net.minecraft.world.Difficulty.PEACEFUL -> ModDifficulty.PEACEFUL
                net.minecraft.world.Difficulty.EASY -> ModDifficulty.EASY
                net.minecraft.world.Difficulty.NORMAL -> ModDifficulty.NORMAL
                net.minecraft.world.Difficulty.HARD -> ModDifficulty.HARD
            }
        } ?: ModDifficulty.NORMAL
    }

    enum class ModDifficulty {
        PEACEFUL, EASY, NORMAL, HARD
    }
}