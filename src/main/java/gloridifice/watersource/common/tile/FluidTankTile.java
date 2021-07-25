package gloridifice.watersource.common.tile;

import gloridifice.watersource.registry.TileEntityTypesRegistry;
import net.minecraft.tileentity.TileEntityType;

public class FluidTankTile extends ModNormalTile{
    public int capability;
    public FluidTankTile(int capability) {
        super(TileEntityTypesRegistry.FLUID_TANK);
        this.capability = capability;
    }

}
