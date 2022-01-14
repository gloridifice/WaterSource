package gloridifice.watersource.registry;

import gloridifice.watersource.client.color.item.FluidBottleColor;
import gloridifice.watersource.client.color.item.WoodenCupColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;

public class ColorRegistry {
    public static final ItemColor CUP_ITEM = new WoodenCupColor();
    public static final ItemColor FLUID_BOTTLE_ITEM = new FluidBottleColor();

    public static void init() {
        Minecraft.getInstance().getItemColors().register(CUP_ITEM, ItemRegistry.WOODEN_CUP_DRINK);
        Minecraft.getInstance().getItemColors().register(FLUID_BOTTLE_ITEM, ItemRegistry.FLUID_BOTTLE);
    }
}
