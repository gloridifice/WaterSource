package gloridifice.watersource.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import static net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack.FLUID_NBT_KEY;

public class WaterLevelFluidRecipe extends WaterLevelItemRecipe{
    public WaterLevelFluidRecipe(ItemStack itemStack, int waterLevel, int waterSaturationLevel) {
        super(itemStack, waterLevel, waterSaturationLevel);
    }

    @Override
    public boolean conform(ItemStack stack) {
        if (stack.isItemEqual(itemStack) && FluidUtil.getFluidHandler(itemStack) != null && FluidUtil.getFluidHandler(stack) != null)
            return FluidUtil.getFluidHandler(stack).map(data -> FluidUtil.getFluidHandler(itemStack).map(data1 -> data1.getFluidInTank(0).isFluidEqual(data.getFluidInTank(0))).orElse(false)).orElse(false);
        else return false;
    }
}
