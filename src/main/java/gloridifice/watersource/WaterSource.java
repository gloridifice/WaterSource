package gloridifice.watersource;

import gloridifice.watersource.client.ClientRegistry;
import gloridifice.watersource.common.CommonProxy;
import gloridifice.watersource.common.network.SimpleNetworkHandler;
import gloridifice.watersource.data.provider.ModBlockTagsProvider;
import gloridifice.watersource.data.provider.ModFluidTagsProvider;
import gloridifice.watersource.data.provider.ModItemTagsProvider;
import gloridifice.watersource.registry.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod("watersource")
public class WaterSource {
    public static final String MODID = "watersource";

    public static final String NETWORK_VERSION = "1.0";
    private static final Logger LOGGER = LogManager.getLogger();

    public WaterSource() {

        BlockRegistry.MOD_BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockRegistry.MOD_BLOCKITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ItemRegistry.MOD_ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FluidRegistry.MOD_FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        WorldGenRegistry.MOD_FEATURES.register(FMLJavaModLoadingContext.get().getModEventBus());
        MobEffectRegistry.MOD_MOB_EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        RegistryModule.MOD_SOUND_EVENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        RegistryModule.MOD_PARTICLE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        RegistryModule.MOD_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockEntityRegistry.MOD_BLOCK_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        EnchantmentRegistry.MOD_ENCHANTMENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        RecipeSerializersRegistry.MOD_RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        RecipeTypesRegistry.MOD_RECIPE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(CapabilityRegistry::registryCapability);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigRegistry.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigRegistry.COMMON_CONFIG);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        SimpleNetworkHandler.init();
        CommonProxy.init();
        event.enqueueWork(WorldGenRegistry::RegistryConfiguredFeatures);
    }
    private void clientSetup(final FMLClientSetupEvent event)
    {
        ClientRegistry.init();
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
    }

    private void processIMC(final InterModProcessEvent event)
    {

    }

}
