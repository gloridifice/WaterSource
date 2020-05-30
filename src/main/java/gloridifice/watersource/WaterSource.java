package gloridifice.watersource;

import gloridifice.watersource.common.network.SimpleNetworkHandler;
import gloridifice.watersource.registry.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("watersource")
public class WaterSource {
    public static final String MODID = "watersource";
    public static final String NETWORK_VERSION = "1.0";
    public WaterSource(){
        new ItemRegistry();
        new EffectRegistry();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        RecipeSerializersRegistry.RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
    public void setup(FMLCommonSetupEvent event)
    {
        CapabilityRegistry.init();
        RecipeRegistry.init();
        SimpleNetworkHandler.init();
    }
}
