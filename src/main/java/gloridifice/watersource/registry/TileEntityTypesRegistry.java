package gloridifice.watersource.registry;

import gloridifice.watersource.common.tile.WaterFilterDownTile;
import gloridifice.watersource.common.tile.WaterFilterUpTile;
import net.minecraft.tileentity.TileEntityType;

public final class TileEntityTypesRegistry extends RegistryModule{
    public static final TileEntityType<WaterFilterUpTile> WATER_FILTER_UP_TILE = (TileEntityType<WaterFilterUpTile>) TileEntityType.Builder.create(WaterFilterUpTile::new, BlockRegistry.blockWaterFilter).build(null).setRegistryName("water_filter_up");
    public static final TileEntityType<WaterFilterDownTile> WATER_FILTER_DOWN_TILE = (TileEntityType<WaterFilterDownTile>) TileEntityType.Builder.create(WaterFilterDownTile::new, BlockRegistry.blockWaterFilter).build(null).setRegistryName("water_filter_down");

}
