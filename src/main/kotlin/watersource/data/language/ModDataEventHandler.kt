package watersource.data.language

import net.minecraft.data.DataProvider
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import watersource.WaterSource

@Mod.EventBusSubscriber(modid = WaterSource.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object ModDataEventHandler {
    @SubscribeEvent
    fun gatherData(event: GatherDataEvent) {
        //language
        event.addClientProvider(EnUsLanguageProvider(event.generator, WaterSource.ID, "en_us"))
    }

    fun GatherDataEvent.addClientProvider(provider: DataProvider) {
        this.generator.addProvider(
            this.includeClient(),
            provider
        )
    }

    fun GatherDataEvent.addServerProvider(provider: DataProvider) {
        this.generator.addProvider(
            this.includeServer(),
            provider
        )
    }
}