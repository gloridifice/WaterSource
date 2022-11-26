package gloridifice.watersource.client;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.client.tooltip.WaterLevelTooltip;
import gloridifice.watersource.registry.BlockRegistry;
import gloridifice.watersource.registry.FluidRegistry;
import gloridifice.watersource.registry.ItemRegistry;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.level.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import java.util.function.Function;


public class ClientProxy {
    public static void init() {
        registerRenderType();
        registerTooltipComponent();
        regItemPropertyFunction();
    }

    public static void registerRenderType() {
        registerCutoutType(BlockRegistry.WOODEN_WATER_FILTER.get());
        registerCutoutType(BlockRegistry.IRON_WATER_FILTER.get());
        registerCutoutType(BlockRegistry.PALM_TREE_HEAD.get());
        registerCutoutType(BlockRegistry.BLOCK_COCONUT.get());
        registerCutoutType(BlockRegistry.PALM_TREE_LEAF.get());
        registerCutoutType(BlockRegistry.BLOCK_COCONUT_SAPLING.get());
        registerCutoutType(BlockRegistry.PRIMITIVE_STRAINER.get());
        registerCutoutType(BlockRegistry.BLOCK_NATURAL_COCONUT.get());
        registerCutoutType(BlockRegistry.PALM_TREE_DOOR.get());
        registerCutoutType(BlockRegistry.PALM_TREE_TRAPDOOR.get());
        registerCutoutType(BlockRegistry.STONE_RAIN_COLLECTOR.get());
    }

    public static void registerTooltipComponent() {
        register(WaterLevelTooltip.WaterLevelComponent.class);
    }

    private static void registerCutoutType(Block block) {
        ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout());
    }

    private static void registerTranslucentMovingType(Block block) {
        ItemBlockRenderTypes.setRenderLayer(block, RenderType.translucentMovingBlock());
    }

    private static void registerTranslucentMovingType(Fluid fluid) {
        ItemBlockRenderTypes.setRenderLayer(fluid, RenderType.translucent());
    }

    private static void registerCutoutMippedType(Block block) {
        ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutoutMipped());
    }

    private static <T extends ClientTooltipComponent & TooltipComponent> void register(Class<T> clazz) {
        MinecraftForgeClient.registerTooltipComponentFactory(clazz, Function.identity());
    }

    public static void regItemPropertyFunction() {
        ItemProperties.register(ItemRegistry.FLUID_BOTTLE.get(),
                new ResourceLocation(WaterSource.MODID, "fluid_bottle"),
                (itemStack, level, livingEntity, value) -> {
                    IFluidHandlerItem fluidHandlerItem = itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
                    FluidStack fluidStack = fluidHandlerItem.getFluidInTank(0);
                    if(fluidStack.getFluid() == FluidRegistry.PURIFIED_WATER.get()){
                        return 1.0f;
                    }
                    return 0.0f;
                });
    }
}
