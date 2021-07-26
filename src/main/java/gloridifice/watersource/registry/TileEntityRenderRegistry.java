package gloridifice.watersource.registry;

import gloridifice.watersource.client.render.tile.RainCollectorTER;
import gloridifice.watersource.client.render.tile.WaterFilterDownTER;
import gloridifice.watersource.client.render.tile.WaterFilterUpTER;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class TileEntityRenderRegistry {
    public static void regTileEntityRender(){
        ClientRegistry.bindTileEntityRenderer(TileEntityTypesRegistry.WATER_FILTER_UP_TILE, WaterFilterUpTER::new);
        ClientRegistry.bindTileEntityRenderer(TileEntityTypesRegistry.WATER_FILTER_DOWN_TILE, WaterFilterDownTER::new);
        ClientRegistry.bindTileEntityRenderer(TileEntityTypesRegistry.RAIN_COLLECTOR, RainCollectorTER::new);
    }
}
