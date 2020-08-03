package gloridifice.watersource.helper;


import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import static net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack.FLUID_NBT_KEY;

public class FluidHelper {
    public static ItemStack fillContainer(ItemStack itemStack, Fluid fluid, int capacity) {
        ItemStack itemStack1 = itemStack.copy();
        CompoundNBT fluidTag = new CompoundNBT();
        new FluidStack(fluid, capacity).writeToNBT(fluidTag);
        itemStack1.getOrCreateTag().put(FLUID_NBT_KEY, fluidTag);
        return itemStack1;
    }
    public static ItemStack fillContainer(ItemStack itemStack, Fluid fluid) {
        ItemStack itemStack1 = itemStack.copy();
        if (!itemStack1.isEmpty() && itemStack1 != null && FluidUtil.getFluidHandler(itemStack1) != null){
            FluidUtil.getFluidHandler(itemStack1).ifPresent(data -> {
                CompoundNBT fluidTag = new CompoundNBT();
                new FluidStack(fluid, data.getTankCapacity(0)).writeToNBT(fluidTag);
                itemStack1.getOrCreateTag().put(FLUID_NBT_KEY, fluidTag);
            });
        }
        return itemStack1;
    }
    public static boolean isItemStackFluidEqual(ItemStack stack1,ItemStack stack2){
        if (!stack1.isEmpty() && FluidUtil.getFluidHandler(stack1) != null && !stack2.isEmpty() && FluidUtil.getFluidHandler(stack2) != null){
            return FluidUtil.getFluidHandler(stack1).map(data1 -> FluidUtil.getFluidHandler(stack2).map(data2 -> data1.getFluidInTank(0).isFluidEqual(data2.getFluidInTank(0))).orElse(false)).orElse(false);
        }else return false;
    }
}
