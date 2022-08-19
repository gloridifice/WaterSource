package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class FluidRegistry {
    public static final ResourceLocation STILL_FLUID_TEXTURE = new ResourceLocation(WaterSource.MODID, "block/fluid/water_still");
    public static final ResourceLocation FLOWING_FLUID_TEXTURE = new ResourceLocation(WaterSource.MODID, "block/fluid/water_flow");

    public static final DeferredRegister<Fluid> MOD_FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, WaterSource.MODID);
    public static final RegistryObject<FlowingFluid> PURIFIED_WATER = MOD_FLUIDS.register("purified_water", () -> {
        return new ForgeFlowingFluid.Source(FluidRegistry.PURIFIED_WATER_PROPERTIES);
    });
    public static final RegistryObject<FlowingFluid> PURIFIED_WATER_FLOWING = MOD_FLUIDS.register("purified_water_flowing", () -> {
        return new ForgeFlowingFluid.Flowing(FluidRegistry.PURIFIED_WATER_PROPERTIES);
    });
    public static final RegistryObject<FlowingFluid> SOUL_WATER = MOD_FLUIDS.register("soul_water", () -> {
        return new ForgeFlowingFluid.Source(FluidRegistry.SOUL_WATER_PROPERTIES);
    });
    public static final RegistryObject<FlowingFluid> SOUL_WATER_FLOWING = MOD_FLUIDS.register("soul_water_flowing", () -> {
        return new ForgeFlowingFluid.Flowing(FluidRegistry.SOUL_WATER_PROPERTIES);
    });
    public static final RegistryObject<FlowingFluid> COCONUT_JUICE = MOD_FLUIDS.register("coconut_juice", () -> {
        return new ForgeFlowingFluid.Source(FluidRegistry.COCONUT_MILK_PROPERTIES);
    });
    public static final RegistryObject<FlowingFluid> COCONUT_JUICE_FLOWING = MOD_FLUIDS.register("coconut_juice_flowing", () -> {
        return new ForgeFlowingFluid.Flowing(FluidRegistry.COCONUT_MILK_PROPERTIES);
    });

    public static final ForgeFlowingFluid.Properties PURIFIED_WATER_PROPERTIES = new ForgeFlowingFluid.Properties(PURIFIED_WATER, PURIFIED_WATER_FLOWING, FluidAttributes.builder(STILL_FLUID_TEXTURE, FLOWING_FLUID_TEXTURE)
            .color(0xCC3ABDFF).viscosity(1000)).bucket(ItemRegistry.itemPurifiedWaterBucket).block(BlockRegistry.BLOCK_PURIFIED_WATER_FLUID)
            .slopeFindDistance(3).explosionResistance(100F);
    public static final ForgeFlowingFluid.Properties SOUL_WATER_PROPERTIES = new ForgeFlowingFluid.Properties(SOUL_WATER, SOUL_WATER_FLOWING, FluidAttributes.builder(STILL_FLUID_TEXTURE, FLOWING_FLUID_TEXTURE)
            .color(0xCC725F45).viscosity(1000))
            .bucket(ItemRegistry.itemSoulWaterBucket).block(BlockRegistry.BLOCK_SOUL_WATER_FLUID)
            .slopeFindDistance(3).explosionResistance(100F);
    public static final ForgeFlowingFluid.Properties COCONUT_MILK_PROPERTIES = new ForgeFlowingFluid.Properties(COCONUT_JUICE, COCONUT_JUICE_FLOWING, FluidAttributes.builder(STILL_FLUID_TEXTURE, FLOWING_FLUID_TEXTURE)
            .color(0xCCEAE8E1).viscosity(1000))
            .bucket(ItemRegistry.ITEM_COCONUT_MILK_BUCKET).block(BlockRegistry.BLOCK_COCONUT_JUICE_FLUID)
            .slopeFindDistance(3).explosionResistance(100F);
}
