package gloridifice.watersource.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidUtil;

public class ThirstFluidRecipe extends ThirstItemRecipe{
    public ThirstFluidRecipe(ItemStack itemStack, int duration, int amplifier, int probability) {
        super(itemStack, duration, amplifier, probability);
    }

    @Override
    public boolean conform(ItemStack stack) {
        if (stack.isItemEqual(itemStack) && FluidUtil.getFluidHandler(itemStack) != null && FluidUtil.getFluidHandler(stack) != null){
            return FluidUtil.getFluidHandler(stack).map(data -> FluidUtil.getFluidHandler(itemStack).map(data1 -> data1.getFluidInTank(0).isFluidEqual(data.getFluidInTank(0))).orElse(false)).orElse(false);
        } else return false;
    }
}
