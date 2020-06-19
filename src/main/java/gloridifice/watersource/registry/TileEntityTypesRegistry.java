package gloridifice.watersource.registry;

import gloridifice.watersource.common.tile.WaterFilterTile;
import net.minecraft.tileentity.TileEntityType;

public final class TileEntityTypesRegistry extends RegistryModule{
    public static final TileEntityType<WaterFilterTile> WATER_FILTER_TILE = (TileEntityType<WaterFilterTile>) TileEntityType.Builder.create(WaterFilterTile::new, BlockRegistry.blockWaterFilter).build(null).setRegistryName("water_filter");

}
