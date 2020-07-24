package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.item.PurifiedWaterBottleItem;
import gloridifice.watersource.common.item.SoulWaterBottle;
import gloridifice.watersource.common.item.StrainerBlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ItemRegistry extends RegistryModule{
    public static final DeferredRegister<Item> FLUID_ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, WaterSource.MODID);
    public static List<StrainerBlockItem> strainerItems = new ArrayList<>();

    public final static Item itemPrimitiveStrainer = new StrainerBlockItem("primitive_strainer",25);
    public final static Item itemPaperStrainer = new StrainerBlockItem("paper_strainer",16);
    public final static Item itemSoulStrainer = new StrainerBlockItem("soul_strainer",25);
    public final static Item itemPaperSoulStrainer = new StrainerBlockItem("paper_soul_strainer",16);
    public final static Item itemPurifiedWaterBottle = new PurifiedWaterBottleItem("purified_water_bottle");
    public final static Item itemSoulWaterBottle = new SoulWaterBottle("soul_water_bottle");

    //Fluids
    public static RegistryObject<Item> itemPurifiedWaterBucket = FLUID_ITEMS.register("purified_water_bucket", () -> {
        return new BucketItem(FluidRegistry.purifiedWaterFluid, new Item.Properties().group(GroupRegistry.waterSourceGroup));
    });

    public ItemRegistry(){
        super();
        for (Field field : getClass().getFields())
        {
            try
            {
                Object o = field.get(null);
                if (o instanceof StrainerBlockItem)
                {
                    strainerItems.add((StrainerBlockItem) o);
                }
            }
            catch (Exception ignored)
            {
            }
        }
        ItemRegistry.FLUID_ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
