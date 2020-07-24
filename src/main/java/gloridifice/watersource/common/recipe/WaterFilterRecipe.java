package gloridifice.watersource.common.recipe;

import gloridifice.watersource.common.item.StrainerBlockItem;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;

public class WaterFilterRecipe {
    protected final Item StrainerItem;
    protected final Fluid InputFluid,OutputFluid;

    public WaterFilterRecipe(StrainerBlockItem strainerItem, Fluid inputFluid, Fluid outputFluid) {
        StrainerItem = strainerItem;
        InputFluid = inputFluid;
        OutputFluid = outputFluid;
    }

    public Fluid getInputFluid() {
        return InputFluid;
    }

    public Fluid getOutputFluid() {
        return OutputFluid;
    }

    public Item getStrainerItem() {
        return StrainerItem;
    }
}
