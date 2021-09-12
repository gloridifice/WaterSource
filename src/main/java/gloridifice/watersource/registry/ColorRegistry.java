package gloridifice.watersource.registry;

import gloridifice.watersource.client.color.item.CupItemColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;

public class ColorRegistry {
    public static final ItemColor CUP_ITEM = new CupItemColor();

    public static void init() {
        Minecraft.getInstance().getItemColors().register(CUP_ITEM, ItemRegistry.itemWoodenCupDrink);
    }
}
