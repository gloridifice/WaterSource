package gloridifice.watersource.client;

import gloridifice.watersource.registry.BlockRegistry;
import gloridifice.watersource.registry.FluidRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;


public class ClientProxy {
    public static void registerRenderType()
    {
        registerCutoutType(BlockRegistry.blockWaterFilter);
        registerCutoutType(BlockRegistry.blockCoconutTreeHead);
        registerCutoutType(BlockRegistry.blockCoconut);
        registerCutoutMippedType(BlockRegistry.blockCoconutTreeLeaf);
        RenderTypeLookup.setRenderLayer(FluidRegistry.purifiedWaterFluid.get(),RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(FluidRegistry.purifiedWaterFluidFlowing.get(),RenderType.getTranslucent());
    }

    private static void registerCutoutType(Block block)
    {
        RenderTypeLookup.setRenderLayer(block, RenderType.getCutout());
    }
    private static void registerTranslucentType(Block block)
    {
        RenderTypeLookup.setRenderLayer(block, RenderType.getTranslucent());
    }
    private static void registerCutoutMippedType(Block block)
    {
        RenderTypeLookup.setRenderLayer(block, RenderType.getCutoutMipped());
    }
}
