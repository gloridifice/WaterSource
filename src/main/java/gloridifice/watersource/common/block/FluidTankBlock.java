package gloridifice.watersource.common.block;

import gloridifice.watersource.registry.TileEntityTypesRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class FluidTankBlock extends Block {
    public FluidTankBlock(String name, Properties properties) {
        super(properties);
        this.setRegistryName(name);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityTypesRegistry.FLUID_TANK.create();
    }
}
