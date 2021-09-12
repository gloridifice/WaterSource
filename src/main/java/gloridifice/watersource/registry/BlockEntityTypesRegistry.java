package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.block.entity.RainCollectorBlockEntity;
import gloridifice.watersource.common.block.entity.StrainerBlockEntity;
import gloridifice.watersource.common.block.entity.WaterFilterDownBlockEntity;
import gloridifice.watersource.common.block.entity.WaterFilterUpBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.List;

public final class BlockEntityTypesRegistry extends RegistryModule {
    public static final BlockEntityType<WaterFilterUpBlockEntity> WATER_FILTER_UP_TILE = (BlockEntityType<WaterFilterUpBlockEntity>) BlockEntityType.Builder.of(WaterFilterUpBlockEntity::new, BlockRegistry.BLOCK_IRON_WATER_FILTER, BlockRegistry.BLOCK_WOODEN_WATER_FILTER).build(null).setRegistryName("iron_water_filter_up");
    public static final BlockEntityType<WaterFilterDownBlockEntity> WATER_FILTER_DOWN_TILE = (BlockEntityType<WaterFilterDownBlockEntity>) BlockEntityType.Builder.of(WaterFilterDownBlockEntity::new, BlockRegistry.BLOCK_IRON_WATER_FILTER, BlockRegistry.BLOCK_WOODEN_WATER_FILTER).build(null).setRegistryName("iron_water_filter_down");
    public static final BlockEntityType<RainCollectorBlockEntity> RAIN_COLLECTOR = (BlockEntityType<RainCollectorBlockEntity>) BlockEntityType.Builder.of(RainCollectorBlockEntity::new, BlockRegistry.BLOCK_STONE_RAIN_COLLECTOR).build(null).setRegistryName("stone_rain_collector");

    public static final BlockEntityType<StrainerBlockEntity> STRAINER_TILE = (BlockEntityType<StrainerBlockEntity>) BlockEntityType.Builder.of(StrainerBlockEntity::new,
                    BlockRegistry.BLOCK_PRIMITIVE_STRAINER, BlockRegistry.BLOCK_PAPER_STRAINER, BlockRegistry.BLOCK_SOUL_STRAINER, BlockRegistry.BLOCK_PAPER_SOUL_STRAINER, BlockRegistry.BLOCK_EVERLASTING_SOUL_STRAINER, BlockRegistry.BLOCK_EVERLASTING_STRAINER)
            .build(null).setRegistryName("strainer");
}
