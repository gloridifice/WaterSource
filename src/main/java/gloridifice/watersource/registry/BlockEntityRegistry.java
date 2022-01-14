package gloridifice.watersource.registry;

import com.mojang.datafixers.types.Type;
import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.block.entity.RainCollectorBlockEntity;
import gloridifice.watersource.common.block.entity.StrainerBlockEntity;
import gloridifice.watersource.common.block.entity.WaterFilterDownBlockEntity;
import gloridifice.watersource.common.block.entity.WaterFilterUpBlockEntity;
import net.minecraft.Util;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

import static gloridifice.watersource.WaterSource.MODID;

public final class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);
    public static final RegistryObject<BlockEntityType<WaterFilterUpBlockEntity>> WATER_FILTER_UP_TILE =
            BLOCK_ENTITY_REGISTER.register("iron_water_filter_up", () -> BlockEntityType.Builder.of(WaterFilterUpBlockEntity::new,
                    BlockRegistry.IRON_WATER_FILTER, BlockRegistry.WOODEN_WATER_FILTER).build(null));
    public static final RegistryObject<BlockEntityType<WaterFilterDownBlockEntity>> WATER_FILTER_DOWN_TILE =
            BLOCK_ENTITY_REGISTER.register("iron_water_filter_down", () -> BlockEntityType.Builder.of(WaterFilterDownBlockEntity::new,
                    BlockRegistry.IRON_WATER_FILTER, BlockRegistry.WOODEN_WATER_FILTER).build(null));
    public static final RegistryObject<BlockEntityType<RainCollectorBlockEntity>> RAIN_COLLECTOR =
            BLOCK_ENTITY_REGISTER.register("stone_rain_collector", () -> BlockEntityType.Builder.of(RainCollectorBlockEntity::new,
                    BlockRegistry.STONE_RAIN_COLLECTOR).build(null));
    public static final RegistryObject<BlockEntityType<StrainerBlockEntity>> STRAINER_TILE =
            BLOCK_ENTITY_REGISTER.register("strainer", () -> BlockEntityType.Builder.of(StrainerBlockEntity::new,
                            BlockRegistry.PRIMITIVE_STRAINER, BlockRegistry.PAPER_STRAINER, BlockRegistry.SOUL_STRAINER,
                            BlockRegistry.PAPER_SOUL_STRAINER, BlockRegistry.EVERLASTING_SOUL_STRAINER, BlockRegistry.EVERLASTING_STRAINER
            ).build(null));

}
