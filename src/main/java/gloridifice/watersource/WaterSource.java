package gloridifice.watersource;

import gloridifice.watersource.client.ClientProxy;
import gloridifice.watersource.common.CommonProxy;
import gloridifice.watersource.common.network.SimpleNetworkHandler;
import gloridifice.watersource.registry.*;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;



@Mod("watersource")
public class WaterSource {
    public static final String MODID = "watersource";
    public static final String NETWORK_VERSION = "1.0";
    public WaterSource(){
        new BlockRegistry();
        new ItemRegistry();
        new FluidRegistry();
        new EffectRegistry();
        new TileEntityTypesRegistry();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverSetupEvent);
        RecipeSerializersRegistry.RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigRegistry.CLIENT_CONFIG);
        BlockRegistry.FLUID_BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ItemRegistry.FLUID_ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
    public void setup(FMLCommonSetupEvent event)
    {
        CapabilityRegistry.init();
        SimpleNetworkHandler.init();
        CommonProxy.init();
        RegisterManager.clearAll();
    }
    public void clientSetup(FMLClientSetupEvent event){
        ClientProxy.init();
        TileEntityRenderRegistry.regTileEntityRender();
        ColorRegistry.init();
    }
    public void serverSetupEvent(FMLLoadCompleteEvent event){
        RecipeRegistry.init();
    }
}
