package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.client.render.blockentity.RainCollectorTER;
import gloridifice.watersource.client.render.blockentity.WaterDispenserTER;
import gloridifice.watersource.client.render.blockentity.WaterFilterDownTER;
import gloridifice.watersource.client.render.blockentity.WaterFilterUpTER;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = WaterSource.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockEntityRenderRegistry {
    @SubscribeEvent
    public static void registryBER(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(BlockEntityRegistry.RAIN_COLLECTOR.get(), RainCollectorTER::new);
        event.registerBlockEntityRenderer(BlockEntityRegistry.WATER_FILTER_UP_TILE.get(), WaterFilterUpTER::new);
        event.registerBlockEntityRenderer(BlockEntityRegistry.WATER_FILTER_DOWN_TILE.get(), WaterFilterDownTER::new);
        event.registerBlockEntityRenderer(BlockEntityRegistry.WATER_DISPENSER.get(), WaterDispenserTER::new);
    }
}
