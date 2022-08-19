package gloridifice.watersource.data;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.data.provider.ModBlockTagsProvider;
import gloridifice.watersource.data.provider.ModFluidTagsProvider;
import gloridifice.watersource.data.provider.ModItemTagsProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = WaterSource.MODID)
public class DateGatherHandler {
    @SubscribeEvent
    public void onDateGen(GatherDataEvent event){
        if(event.includeServer()) {
            event.getGenerator().addProvider(new ModBlockTagsProvider(event.getGenerator(), ForgeRegistries.BLOCKS, event.getExistingFileHelper()));
            event.getGenerator().addProvider(new ModItemTagsProvider(event.getGenerator(), ForgeRegistries.ITEMS, event.getExistingFileHelper()));
            event.getGenerator().addProvider(new ModFluidTagsProvider(event.getGenerator(), ForgeRegistries.FLUIDS, event.getExistingFileHelper()));
        }
    }
}
