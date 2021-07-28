package gloridifice.watersource.client;

import gloridifice.watersource.registry.BlockRegistry;
import gloridifice.watersource.registry.FluidRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.fluid.Fluid;


public class ClientProxy {
    public static void init(){
        registerRenderType();
    }
    public static void registerRenderType()
    {
        registerCutoutType(BlockRegistry.BLOCK_WOODEN_WATER_FILTER);
        registerCutoutType(BlockRegistry.BLOCK_IRON_WATER_FILTER);
        registerCutoutType(BlockRegistry.BLOCK_COCONUT_TREE_HEAD);
        registerCutoutType(BlockRegistry.BLOCK_COCONUT);
        registerCutoutType(BlockRegistry.BLOCK_COCONUT_TREE_LEAF);
        registerCutoutType(BlockRegistry.BLOCK_COCONUT_SAPLING);
        registerCutoutType(BlockRegistry.BLOCK_PRIMITIVE_STRAINER);
        registerCutoutType(BlockRegistry.BLOCK_NATURAL_COCONUT);
        registerCutoutType(BlockRegistry.BLOCK_COCONUT_TREE_DOOR);
        registerCutoutType(BlockRegistry.BLOCK_COCONUT_TREE_TRAPDOOR);
        registerCutoutType(BlockRegistry.BLOCK_STONE_RAIN_COLLECTOR);

/*        registerTranslucentMovingType(FluidRegistry.SOUL_WATER.get());
        registerTranslucentMovingType(FluidRegistry.SOUL_WATER_FLOWING.get());
        registerTranslucentMovingType(FluidRegistry.PURIFIED_WATER.get());
        registerTranslucentMovingType(FluidRegistry.PURIFIED_WATER_FLOWING.get());
        registerTranslucentMovingType(FluidRegistry.COCONUT_MILK.get());
        registerTranslucentMovingType(FluidRegistry.COCONUT_MILK_FLOWING.get());*/
/*
        RenderTypeLookup.setRenderLayer(FluidRegistry.purifiedWaterFluid.get(),RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(FluidRegistry.purifiedWaterFluidFlowing.get(),RenderType.getTranslucent());
*/
    }

    private static void registerCutoutType(Block block)
    {
        RenderTypeLookup.setRenderLayer(block, RenderType.getCutout());
    }
    private static void registerTranslucentMovingType(Block block)
    {
        RenderTypeLookup.setRenderLayer(block, RenderType.getTranslucentMovingBlock());
    }
    private static void registerTranslucentMovingType(Fluid fluid)
    {
        RenderTypeLookup.setRenderLayer(fluid, RenderType.getTranslucent());
    }
    private static void registerCutoutMippedType(Block block)
    {
        RenderTypeLookup.setRenderLayer(block, RenderType.getCutoutMipped());
    }
}
