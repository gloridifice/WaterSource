package gloridifice.watersource.common.tile;

import gloridifice.watersource.registry.TileEntityTypesRegistry;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemStackHandler;

public class WaterFilterTile extends TileEntity implements ITickableTileEntity {
    FluidTank upTank = new FluidTank(1000);
    FluidTank downTank = new FluidTank(1000);
    ItemStackHandler strainer = new ItemStackHandler(1);

    private int processTicks = 0;

    public WaterFilterTile() {
        super(TileEntityTypesRegistry.WATER_FILTER_TILE);
    }

    @Override
    public void read(CompoundNBT compound) {
        upTank.readFromNBT(compound.getCompound("upTank"));
        downTank.readFromNBT(compound.getCompound("downTank"));
        strainer.deserializeNBT(compound.getCompound("strainer"));
        processTicks = ((IntNBT)compound.get("processTicks")).getInt();
        super.read(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("upTank",upTank.writeToNBT(compound));
        compound.put("downTank",downTank.writeToNBT(compound));
        compound.put("strainer",strainer.serializeNBT());
        compound.put("processTicks", IntNBT.valueOf(processTicks));
        return compound;
    }

    @Override
    public void tick() {
        processTicks %= 8000;
        processTicks ++;
        if (processTicks % 10 == 0){
            
        }
    }
}
