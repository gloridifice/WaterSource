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
    public static RegistryObject<FlowingFluid> purifiedWaterFluid = FLUIDS.register("purified_water_fluid", () -> { return new ForgeFlowingFluid.Source(FluidRegistry.PURIFIED_WATER_PROPERTIES); });
    public static RegistryObject<FlowingFluid> purifiedWaterFluidFlowing = FLUIDS.register("purified_water_fluid_flowing", () -> { return new ForgeFlowingFluid.Flowing(FluidRegistry.PURIFIED_WATER_PROPERTIES); });
    public static RegistryObject<FlowingFluid> soulWaterFluid = FLUIDS.register("soul_water_fluid", () -> { return new ForgeFlowingFluid.Source(FluidRegistry.SOUL_WATER_PROPERTIES); });
    public static RegistryObject<FlowingFluid> soulWaterFluidFlowing = FLUIDS.register("soul_water_fluid_flowing", () -> { return new ForgeFlowingFluid.Flowing(FluidRegistry.SOUL_WATER_PROPERTIES); });
    public static RegistryObject<FlowingFluid> coconutJuiceFluid = FLUIDS.register("coconut_juice_fluid", () -> { return new ForgeFlowingFluid.Source(FluidRegistry.COCONUT_JUICE_PROPERTIES); });
    public static RegistryObject<FlowingFluid> coconutJuiceFluidFlowing = FLUIDS.register("coconut_juice_fluid_flowing", () -> { return new ForgeFlowingFluid.Flowing(FluidRegistry.COCONUT_JUICE_PROPERTIES); });

    public static ForgeFlowingFluid.Properties PURIFIED_WATER_PROPERTIES = new ForgeFlowingFluid.Properties(purifiedWaterFluid, purifiedWaterFluidFlowing, FluidAttributes.builder(STILL_OIL_TEXTURE, FLOWING_OIL_TEXTURE).color(0x3ABDFF).density(10)).bucket(ItemRegistry.itemPurifiedWaterBucket).block(BlockRegistry.blockPurifiedWaterFluid).slopeFindDistance(3).explosionResistance(100F);
    public static ForgeFlowingFluid.Properties SOUL_WATER_PROPERTIES = new ForgeFlowingFluid.Properties(soulWaterFluid, soulWaterFluidFlowing, FluidAttributes.builder(STILL_OIL_TEXTURE, FLOWING_OIL_TEXTURE).color(0x876D5F).density(6)).bucket(ItemRegistry.itemSoulWaterBucket).block(BlockRegistry.blockSoulWaterFluid).slopeFindDistance(3).explosionResistance(100F);
    public static ForgeFlowingFluid.Properties COCONUT_JUICE_PROPERTIES = new ForgeFlowingFluid.Properties(coconutJuiceFluid, coconutJuiceFluidFlowing, FluidAttributes.builder(STILL_OIL_TEXTURE, FLOWING_OIL_TEXTURE).color(0xEAE8E1).density(8)).bucket(ItemRegistry.itemCoconutJuiceBucket).block(BlockRegistry.blockCoconutJuiceFluid).slopeFindDistance(3).explosionResistance(100F);

    public FluidRegistry(){
        FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
