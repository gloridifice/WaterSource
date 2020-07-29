package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.item.WoodenCupItem;
import gloridifice.watersource.common.item.WaterBagItem;
import gloridifice.watersource.common.item.WaterBottleItem;
import gloridifice.watersource.common.item.SoulWaterBottleItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemRegistry extends RegistryModule {
    public static final DeferredRegister<Item> FLUID_ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, WaterSource.MODID);

    public final static Item itemPurifiedWaterBottle = new WaterBottleItem("purified_water_bottle");
    public final static Item itemCoconutJuiceBottle = new WaterBottleItem("coconut_juice_bottle");
    public final static Item itemSoulWaterBottle = new SoulWaterBottleItem("soul_water_bottle");
    public final static Item itemWoodenCup = new WoodenCupItem("wooden_cup",new Item.Properties().group(null), 250) {
        @Override
        public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundNBT nbt) {
            return super.initCapabilities(new ItemStack(itemWoodenCupDrink), nbt);
        }
    };
    public final static Item itemWoodenCupDrink = new WoodenCupItem("wooden_cup_drink",new Item.Properties().maxStackSize(1).group(GroupRegistry.waterSourceGroup), 250);
    public final static Item itemFeatherWaterBag = new WaterBagItem("leather_water_bag",1500,250);
    //Fluids
    public static RegistryObject<Item> itemPurifiedWaterBucket = FLUID_ITEMS.register("purified_water_bucket", () -> {
        return new BucketItem(FluidRegistry.purifiedWaterFluid, new Item.Properties().group(GroupRegistry.waterSourceGroup));
    });
    public static RegistryObject<Item> itemSoulWaterBucket = FLUID_ITEMS.register("soul_water_bucket", () -> {
        return new BucketItem(FluidRegistry.soulWaterFluid, new Item.Properties().group(GroupRegistry.waterSourceGroup));
    });
    public static RegistryObject<Item> itemCoconutJuiceBucket = FLUID_ITEMS.register("coconut_juice_bucket", () -> {
        return new BucketItem(FluidRegistry.coconutJuiceFluid, new Item.Properties().group(GroupRegistry.waterSourceGroup));
    });
    public ItemRegistry() {
        super();
        ItemRegistry.FLUID_ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
