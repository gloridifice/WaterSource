package gloridifice.watersource.registry;

import gloridifice.watersource.common.item.PurifiedWaterBottleItem;
import gloridifice.watersource.common.item.SoulWaterBottle;
import gloridifice.watersource.common.item.StrainerItem;
import net.minecraft.item.Item;

public class ItemRegistry extends RegistryModule{
    public final static Item itemPrimitiveStrainer = new StrainerItem("primitive_strainer",40);
    public final static Item itemPaperStrainer = new StrainerItem("paper_strainer",25);
    public final static Item itemSoulStrainer = new StrainerItem("soul_strainer",60);

    public final static Item itemPurifiedWaterBottle = new PurifiedWaterBottleItem("purified_water_bottle");
    public final static Item itemSoulWaterBottle = new SoulWaterBottle("soul_water_bottle");
}
