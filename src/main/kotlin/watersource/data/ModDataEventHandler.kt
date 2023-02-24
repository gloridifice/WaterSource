package watersource.data

import net.minecraft.data.DataGenerator
import net.minecraft.data.DataProvider
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import watersource.WaterSource
import watersource.data.language.ModLanguageProvider
import watersource.data.language.ModLanguagesHandler
import watersource.data.model.ModItemModelProvider
import watersource.data.recipe.ModRecipeProvider
import watersource.data.tag.provider.ModBlockTagProvider
import watersource.data.tag.provider.ModItemTagProvider

@Mod.EventBusSubscriber(modid = WaterSource.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object ModDataEventHandler {
    @SubscribeEvent
    fun gatherData(event: GatherDataEvent) {
        val generator = event.generator
        val existingFileHelper = event.existingFileHelper

        //language
        ModLanguagesHandler.init()
        event.addClientProvider(ModLanguageProvider(generator, WaterSource.ID, "en_us"))
        event.addClientProvider(ModLanguageProvider(generator, WaterSource.ID, "zh_cn"))

        //model
        event.addClientProvider(ModItemModelProvider(generator, WaterSource.ID, existingFileHelper))

        //recipe
        event.addServerProvider(ModRecipeProvider(generator))

        //tag
        val blockTagProvider = ModBlockTagProvider(generator, WaterSource.ID, existingFileHelper)
        event.addServerProvider(ModItemTagProvider(generator, blockTagProvider, WaterSource.ID, existingFileHelper))
        event.addServerProvider(blockTagProvider)
    }

    private fun GatherDataEvent.addClientProvider(provider: DataProvider) {
        this.generator.addProvider(
            this.includeClient(),
            provider
        )
    }

    private fun GatherDataEvent.addServerProvider(provider: DataProvider) {
        this.generator.addProvider(
            this.includeServer(),
            provider
        )
    }
}