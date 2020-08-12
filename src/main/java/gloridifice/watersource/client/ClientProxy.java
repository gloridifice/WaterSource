package gloridifice.watersource.client;

import gloridifice.watersource.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;


public class ClientProxy {
    public static void init(){
        registerRenderType();
    }
    public static void registerRenderType()
    {
        registerCutoutType(BlockRegistry.BLOCK_WATER_FILTER);
        registerCutoutType(BlockRegistry.BLOCK_COCONUT_TREE_HEAD);
        registerCutoutType(BlockRegistry.BLOCK_COCONUT);
        registerCutoutType(BlockRegistry.BLOCK_COCONUT_TREE_LEAF);
        registerCutoutType(BlockRegistry.BLOCK_COCONUT_SAPLING);
        registerCutoutType(BlockRegistry.BLOCK_PRIMITIVE_STRAINER);
        registerCutoutType(BlockRegistry.BLOCK_NATURAL_COCONUT);
        registerCutoutType(BlockRegistry.BLOCK_COCONUT_TREE_DOOR);
        registerCutoutType(BlockRegistry.BLOCK_COCONUT_TREE_TRAPDOOR);
/*
        RenderTypeLookup.setRenderLayer(FluidRegistry.purifiedWaterFluid.get(),RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(FluidRegistry.purifiedWaterFluidFlowing.get(),RenderType.getTranslucent());
*/
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
