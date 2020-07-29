package gloridifice.watersource.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import static net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack.FLUID_NBT_KEY;

public class WaterLevelFluidRecipe extends WaterLevelItemRecipe{
    public WaterLevelFluidRecipe(ItemStack itemStack, int waterLevel, int waterSaturationLevel) {
        super(itemStack, waterLevel, waterSaturationLevel);
    }

    @Override
    public boolean conform(ItemStack stack) {
        if (stack.isItemEqual(itemStack) && stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY) != null && itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY) != null)
        return stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).map(data -> {
            return itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).map(data1 -> {
                return data1.getFluidInTank(0).isFluidEqual(data.getFluidInTank(0));
            }).orElse(false);
        }).orElse(false);
        else return false;
    }
}
