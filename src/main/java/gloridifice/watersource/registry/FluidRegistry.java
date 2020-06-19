package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class FluidRegistry {
    public static final ResourceLocation STILL_OIL_TEXTURE = new ResourceLocation(WaterSource.MODID,"block/fluid/water_still");
    public static final ResourceLocation FLOWING_OIL_TEXTURE = new ResourceLocation(WaterSource.MODID,"block/fluid/water_flow");

    public static final DeferredRegister<Fluid> FLUIDS = new DeferredRegister<>(ForgeRegistries.FLUIDS, WaterSource.MODID);
    public static RegistryObject<FlowingFluid> purifiedWaterFluid = FLUIDS.register("purified_water_fluid", () -> {
        return new ForgeFlowingFluid.Source(FluidRegistry.PROPERTIES);
    });
    public static RegistryObject<FlowingFluid> purifiedWaterFluidFlowing = FLUIDS.register("purified_water_fluid_flowing", () -> {
        return new ForgeFlowingFluid.Flowing(FluidRegistry.PROPERTIES);
    });
    public static ForgeFlowingFluid.Properties PROPERTIES = new ForgeFlowingFluid.Properties(purifiedWaterFluid, purifiedWaterFluidFlowing, FluidAttributes.builder(STILL_OIL_TEXTURE, FLOWING_OIL_TEXTURE).color(0x3ABDFF).density(10)).bucket(ItemRegistry.itemPurifiedWaterBucket).block(BlockRegistry.blockPurifiedWaterFluid).slopeFindDistance(3).explosionResistance(100F);
    public FluidRegistry(){
        FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
