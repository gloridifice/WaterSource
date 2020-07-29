package gloridifice.watersource.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class ThirstFluidRecipe extends ThirstItemRecipe{
    public ThirstFluidRecipe(ItemStack itemStack, int duration, int amplifier, int probability) {
        super(itemStack, duration, amplifier, probability);
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
