package gloridifice.watersource;

import gloridifice.watersource.client.ClientProxy;
import gloridifice.watersource.common.CommonProxy;
import gloridifice.watersource.common.network.SimpleNetworkHandler;
import gloridifice.watersource.registry.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod("watersource")
public class WaterSource {
    public static final String MODID = "watersource";
    public static final String NETWORK_VERSION = "1.0";
    private static final Logger LOGGER = LogManager.getLogger();

    public WaterSource() {
        new BlockRegistry();
        new ItemRegistry();
        new FluidRegistry();
        new EffectRegistry();
        new EnchantmentRegistry();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(CapabilityRegistry::registryCapability);

        RecipeSerializersRegistry.RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockEntityRegistry.BLOCK_ENTITY_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockRegistry.FLUID_BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ItemRegistry.FLUID_ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigRegistry.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigRegistry.COMMON_CONFIG);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public void setup(final FMLCommonSetupEvent event) {
        SimpleNetworkHandler.init();
        CommonProxy.init();
        RegisterManager.clearAll();
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        ClientProxy.init();
        BlockEntityRenderRegistry.regBlockEntityRender();
        ColorRegistry.init();

    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        //InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
    }
}
