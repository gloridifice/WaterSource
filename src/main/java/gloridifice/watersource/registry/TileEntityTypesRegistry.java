package gloridifice.watersource.registry;

import gloridifice.watersource.common.tile.RainCollectorTile;
import gloridifice.watersource.common.tile.StrainerTile;
import gloridifice.watersource.common.tile.WaterFilterDownTile;
import gloridifice.watersource.common.tile.WaterFilterUpTile;
import net.minecraft.tileentity.TileEntityType;

public final class TileEntityTypesRegistry extends RegistryModule{
    public static final TileEntityType<WaterFilterUpTile> WATER_FILTER_UP_TILE = (TileEntityType<WaterFilterUpTile>) TileEntityType.Builder.create(() -> new WaterFilterUpTile(3000),BlockRegistry.BLOCK_IRON_WATER_FILTER, BlockRegistry.BLOCK_WOODEN_WATER_FILTER).build(null).setRegistryName("iron_water_filter_up");
    public static final TileEntityType<WaterFilterDownTile> WATER_FILTER_DOWN_TILE = (TileEntityType<WaterFilterDownTile>) TileEntityType.Builder.create(() -> new WaterFilterDownTile(3000), BlockRegistry.BLOCK_IRON_WATER_FILTER, BlockRegistry.BLOCK_WOODEN_WATER_FILTER).build(null).setRegistryName("iron_water_filter_down");
    public static final TileEntityType<RainCollectorTile> RAIN_COLLECTOR = (TileEntityType<RainCollectorTile>) TileEntityType.Builder.create(() -> new RainCollectorTile(5000), BlockRegistry.BLOCK_STONE_RAIN_COLLECTOR).build(null).setRegistryName("stone_rain_collector");

    public static final TileEntityType<StrainerTile> STRAINER_TILE = (TileEntityType<StrainerTile>) TileEntityType.Builder.create(StrainerTile::new, BlockRegistry.BLOCK_PRIMITIVE_STRAINER).build(null).setRegistryName("strainer");
}
