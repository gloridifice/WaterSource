package gloridifice.watersource.common.event;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.data.provider.BlockTagProvider;
import gloridifice.watersource.common.data.provider.FluidTagsProvider;
import gloridifice.watersource.common.data.provider.ItemTagProvider;
import gloridifice.watersource.common.data.provider.RecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = WaterSource.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGatherHandler {
    @SubscribeEvent
    public static void onDataGather(GatherDataEvent event)
    {
        DataGenerator gen = event.getGenerator();
        if (event.includeServer())
        {
            gen.addProvider(new RecipeProvider(gen));
            BlockTagProvider blockProvider = new BlockTagProvider(gen, event.getExistingFileHelper());
            gen.addProvider(new ItemTagProvider(gen, blockProvider, event.getExistingFileHelper()));
            gen.addProvider(blockProvider);
            gen.addProvider(new FluidTagsProvider(gen, event.getExistingFileHelper()));
        }
    }
}
