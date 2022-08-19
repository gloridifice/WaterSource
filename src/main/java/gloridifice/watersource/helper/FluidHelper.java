package gloridifice.watersource.helper;


import gloridifice.watersource.common.item.FluidBottleItem;
import gloridifice.watersource.registry.ItemRegistry;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import static net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack.FLUID_NBT_KEY;

public class FluidHelper {
    public static ItemStack fillContainer(ItemStack itemStack, Fluid fluid, int capacity) {
        ItemStack itemStack1 = itemStack.copy();
        CompoundTag fluidTag = new CompoundTag();
        new FluidStack(fluid, capacity).writeToNBT(fluidTag);
        itemStack1.getOrCreateTag().put(FLUID_NBT_KEY, fluidTag);
        return itemStack1;
    }

    public static ItemStack fillContainer(ItemStack itemStack, Fluid fluid) {
        if (fluid != null && !itemStack.isEmpty() && CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY != null && FluidUtil.getFluidHandler(itemStack) != null) {
            ItemStack itemStack1 = itemStack.copy();
            FluidUtil.getFluidHandler(itemStack1).ifPresent(data -> {
                CompoundTag fluidTag = new CompoundTag();
                new FluidStack(fluid, data.getTankCapacity(0)).writeToNBT(fluidTag);
                itemStack1.getOrCreateTag().put(FLUID_NBT_KEY, fluidTag);
            });
            return itemStack1;
        }
        return itemStack;
    }

    public static boolean isItemStackFluidEqual(ItemStack stack1, ItemStack stack2) {
        if (!stack1.isEmpty() && FluidUtil.getFluidHandler(stack1) != null && !stack2.isEmpty() && FluidUtil.getFluidHandler(stack2) != null) {
            return FluidUtil.getFluidHandler(stack1).map(data1 -> FluidUtil.getFluidHandler(stack2).map(data2 -> data1.getFluidInTank(0).isFluidEqual(data2.getFluidInTank(0))).orElse(false)).orElse(false);
        }
        else return false;
    }

    public static boolean isFluidBottleQualified(ItemStack bottleItem, Fluid equalFluid){
        if (bottleItem.getItem() instanceof FluidBottleItem){
            IFluidHandlerItem fluidHandlerItem = bottleItem.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
            FluidStack fluidStack = fluidHandlerItem.getFluidInTank(0);
            if (fluidStack.getAmount() >= 250 && fluidStack.getFluid() == equalFluid) return true;
        }
        return false;
    }
}
