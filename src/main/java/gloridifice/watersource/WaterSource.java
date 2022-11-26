package gloridifice.watersource;

import gloridifice.watersource.client.ClientProxy;
import gloridifice.watersource.common.CommonProxy;
import gloridifice.watersource.common.network.SimpleNetworkHandler;
import gloridifice.watersource.registry.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod("watersource")
public class WaterSource {
    public static final String MODID = "watersource";
    public static final String NETWORK_VERSION = "1.0";
    private static final Logger LOGGER = LogManager.getLogger();

    public WaterSource() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigRegistry.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigRegistry.COMMON_CONFIG);
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BlockRegistry.FLUID_BLOCKS.register(modEventBus);
        ItemRegistry.FLUID_ITEMS.register(modEventBus);
        FluidRegistry.FLUIDS.register(modEventBus);
        EffectRegistry.MOB_EFFECTS.register(modEventBus);
        EnchantmentRegistry.ENCHANTMENTS.register(modEventBus);
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(CapabilityRegistry::registryCapability);

        RecipeSerializersRegistry.RECIPE_SERIALIZERS.register(modEventBus);
        BlockEntityRegistry.BLOCK_ENTITY_REGISTER.register(modEventBus);
        RecipeTypesRegistry.RECIPE_TYPES.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void setup(final FMLCommonSetupEvent event) {
        SimpleNetworkHandler.init();
        CommonProxy.init();
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        ClientProxy.init();
        BlockEntityRenderRegistry.regBlockEntityRender();
        ColorRegistry.init();

    }

}
