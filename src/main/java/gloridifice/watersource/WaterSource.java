package gloridifice.watersource;

import gloridifice.watersource.client.ClientProxy;
import gloridifice.watersource.common.block.tree.CoconutTree;
import gloridifice.watersource.common.network.SimpleNetworkHandler;
import gloridifice.watersource.registry.*;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

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
        // FIXME this is the wrong place for such registrations
        // FIXME getFlowerFeatures might be the wrong category
        ForgeRegistries.BIOMES.getValue(Biomes.BEACH.getLocation()).getGenerationSettings().getFlowerFeatures().add(FeatureRegistry.COCONUT_TREE.withConfiguration(CoconutTree.COCONUT_TREE_CONFIG).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.5F, 1))));
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
