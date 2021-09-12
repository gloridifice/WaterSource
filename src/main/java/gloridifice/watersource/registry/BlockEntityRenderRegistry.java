package gloridifice.watersource.registry;

import gloridifice.watersource.client.render.tile.RainCollectorTER;
import gloridifice.watersource.client.render.tile.WaterFilterDownTER;
import gloridifice.watersource.client.render.tile.WaterFilterUpTER;
import gloridifice.watersource.common.block.entity.RainCollectorBlockEntity;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

public class BlockEntityRenderRegistry {
    static {
        BlockEntityRenderers.register(BlockEntityTypesRegistry.RAIN_COLLECTOR, RainCollectorTER::new);
        BlockEntityRenderers.register(BlockEntityTypesRegistry .WATER_FILTER_UP_TILE, WaterFilterUpTER::new);
        BlockEntityRenderers.register(BlockEntityTypesRegistry.WATER_FILTER_DOWN_TILE, WaterFilterDownTER::new);
    }
    public static void regBlockEntityRender() {

/*        ClientRegistry.bindBlockEntityRenderer(BlockEntityTypesRegistry.WATER_FILTER_UP_TILE, WaterFilterUpTER::new);
        ClientRegistry.bindBlockEntityRenderer(BlockEntityTypesRegistry.WATER_FILTER_DOWN_TILE, WaterFilterDownTER::new);
        ClientRegistry.bindBlockEntityRenderer(BlockEntityTypesRegistry.RAIN_COLLECTOR, RainCollectorTER::new);*/
    }
}
