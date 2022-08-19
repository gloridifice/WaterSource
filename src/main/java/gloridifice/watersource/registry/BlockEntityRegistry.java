package gloridifice.watersource.registry;

import gloridifice.watersource.common.block.entity.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static gloridifice.watersource.WaterSource.MODID;

public final class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> MOD_BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);
    public static final RegistryObject<BlockEntityType<WaterFilterUpBlockEntity>> WATER_FILTER_UP_TILE =
            MOD_BLOCK_ENTITIES.register("iron_water_filter_up", () -> BlockEntityType.Builder.of(WaterFilterUpBlockEntity::new,
                    BlockRegistry.IRON_WATER_FILTER.get(), BlockRegistry.WOODEN_WATER_FILTER.get()).build(null));
    public static final RegistryObject<BlockEntityType<WaterFilterDownBlockEntity>> WATER_FILTER_DOWN_TILE =
            MOD_BLOCK_ENTITIES.register("iron_water_filter_down", () -> BlockEntityType.Builder.of(WaterFilterDownBlockEntity::new,
                    BlockRegistry.IRON_WATER_FILTER.get(), BlockRegistry.WOODEN_WATER_FILTER.get()).build(null));
    public static final RegistryObject<BlockEntityType<RainCollectorBlockEntity>> RAIN_COLLECTOR =
            MOD_BLOCK_ENTITIES.register("stone_rain_collector", () -> BlockEntityType.Builder.of(RainCollectorBlockEntity::new,
                    BlockRegistry.STONE_RAIN_COLLECTOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<StrainerBlockEntity>> STRAINER_TILE =
            MOD_BLOCK_ENTITIES.register("strainer", () -> BlockEntityType.Builder.of(StrainerBlockEntity::new,
                            BlockRegistry.PRIMITIVE_STRAINER.get(), BlockRegistry.PAPER_STRAINER.get(), BlockRegistry.SOUL_STRAINER.get(),
                            BlockRegistry.PAPER_SOUL_STRAINER.get(), BlockRegistry.EVERLASTING_SOUL_STRAINER.get(), BlockRegistry.EVERLASTING_STRAINER.get()
            ).build(null));
    public static final RegistryObject<BlockEntityType<WaterDispenserBlockEntity>> WATER_DISPENSER =
            MOD_BLOCK_ENTITIES.register("water_dispenser.json", () -> BlockEntityType.Builder.of(WaterDispenserBlockEntity::new,
                    BlockRegistry.WATER_DISPENSER.get()).build(null));

}
