package gloridifice.watersource.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.function.Supplier;

public class WaterFilterMessage implements INormalMessage {
    int x, y, z;
    FluidStack fluid;
    ItemStack strainer;

    public WaterFilterMessage(FluidStack stack, int x, int y, int z, ItemStack strainer) {
        this.fluid = stack;
        this.strainer = strainer;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public WaterFilterMessage(PacketBuffer buf) {
        fluid = buf.readFluidStack();
        strainer = buf.readItemStack();
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeFluidStack(fluid);
        buf.writeItemStack(strainer);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public void process(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            @SuppressWarnings("resource")
            TileEntity tileEntity = Minecraft.getInstance().player.getEntityWorld().getTileEntity(new BlockPos(x, y, z));
            tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent(data -> {
                data.drain(data.getTankCapacity(0), IFluidHandler.FluidAction.EXECUTE);
                data.fill(fluid, IFluidHandler.FluidAction.EXECUTE);
            });
            if (!strainer.isEmpty()) {
                tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(data -> {
                    data.extractItem(0, 64, false);
                    data.insertItem(0, strainer, false);
                });
            }
        });
    }
}
