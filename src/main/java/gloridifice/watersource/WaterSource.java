package gloridifice.watersource;

import gloridifice.watersource.registry.CapabilityRegistry;
import gloridifice.watersource.registry.ItemRegistry;
import gloridifice.watersource.registry.RecipesRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("watersource")
public class WaterSource {
    public static final String MODID = "watersource";
    public WaterSource(){
        new ItemRegistry();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }
    public void setup(FMLCommonSetupEvent event)
    {
        CapabilityRegistry.init();
        RecipesRegistry.init();
    }
}
