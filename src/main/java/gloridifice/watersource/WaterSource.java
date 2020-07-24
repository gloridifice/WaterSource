package gloridifice.watersource;

import gloridifice.watersource.client.ClientProxy;
import gloridifice.watersource.common.network.SimpleNetworkHandler;
import gloridifice.watersource.registry.*;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;



@Mod("watersource")
public class WaterSource {
    public static final String MODID = "watersource";
    public static final String NETWORK_VERSION = "1.0";
    public WaterSource(){
        new ItemRegistry();
        new BlockRegistry();
        new EffectRegistry();
        new FluidRegistry();
        new TileEntityTypesRegistry();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        RecipeSerializersRegistry.RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigRegistry.CLIENT_CONFIG);
    }
    public void setup(FMLCommonSetupEvent event)
    {
        CapabilityRegistry.init();
        RecipeRegistry.init();
        SimpleNetworkHandler.init();
    }
    public void clientSetup(FMLClientSetupEvent event){
        ClientProxy.registerRenderType();
        TileEntityRenderRegistry.regTileEntityRender();
    }
}
