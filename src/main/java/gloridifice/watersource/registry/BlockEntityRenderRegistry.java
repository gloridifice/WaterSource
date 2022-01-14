package gloridifice.watersource.registry;

import gloridifice.watersource.client.render.tile.RainCollectorTER;
import gloridifice.watersource.client.render.tile.WaterFilterDownTER;
import gloridifice.watersource.client.render.tile.WaterFilterUpTER;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;


public class BlockEntityRenderRegistry {
    static {
        BlockEntityRenderers.register(BlockEntityRegistry.RAIN_COLLECTOR.get(), RainCollectorTER::new);
        BlockEntityRenderers.register(BlockEntityRegistry.WATER_FILTER_UP_TILE.get(), WaterFilterUpTER::new);
        BlockEntityRenderers.register(BlockEntityRegistry.WATER_FILTER_DOWN_TILE.get(), WaterFilterDownTER::new);
    }
    public static void regBlockEntityRender() {

/*        ClientRegistry.bindBlockEntityRenderer(BlockEntityTypesRegistry.WATER_FILTER_UP_TILE, WaterFilterUpTER::new);
        ClientRegistry.bindBlockEntityRenderer(BlockEntityTypesRegistry.WATER_FILTER_DOWN_TILE, WaterFilterDownTER::new);
        ClientRegistry.bindBlockEntityRenderer(BlockEntityTypesRegistry.RAIN_COLLECTOR, RainCollectorTER::new);*/
    }
}
