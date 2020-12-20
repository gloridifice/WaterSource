package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.item.*;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemRegistry extends RegistryModule {
    public static final DeferredRegister<Item> FLUID_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WaterSource.MODID);

    public final static Item itemPurifiedWaterBottle = new WaterBottleItem("purified_water_bottle");
    public final static Item itemCoconutJuiceBottle = new WaterBottleItem("coconut_milk_bottle");
    public final static Item itemSoulWaterBottle = new SoulWaterBottleItem("soul_water_bottle");
    public final static Item itemWoodenCup = new WoodenCupItem("wooden_cup",new Item.Properties().group(null), 250) {
        @Override
        public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundNBT nbt) {
            return super.initCapabilities(new ItemStack(itemWoodenCupDrink), nbt);
        }
    };
    public final static Item itemHalfCoconut = new ModNormalItem("half_coconut");
    public final static Item itemCoconutPiece = new ModFoodItem("coconut_piece",new Food.Builder().hunger(2).fastToEat().saturation(3).build());
    public final static Item itemWoodenCupDrink = new WoodenCupItem("wooden_cup_drink",new Item.Properties().maxStackSize(1).group(GroupRegistry.waterSourceGroup), 250);
    public final static Item itemLeatherWaterBag = new WaterBagItem("leather_water_bag",1500,250);
    //Fluids
    public static RegistryObject<Item> itemPurifiedWaterBucket = FLUID_ITEMS.register("purified_water_bucket", () -> {
        return new BucketItem(FluidRegistry.PURIFIED_WATER, new Item.Properties().group(GroupRegistry.waterSourceGroup));
    });
    public static RegistryObject<Item> itemSoulWaterBucket = FLUID_ITEMS.register("soul_water_bucket", () -> {
        return new BucketItem(FluidRegistry.SOUL_WATER, new Item.Properties().group(GroupRegistry.waterSourceGroup));
    });
    public static RegistryObject<Item> ITEM_COCONUT_MILK_BUCKET = FLUID_ITEMS.register("coconut_milk_bucket", () -> {
        return new BucketItem(FluidRegistry.COCONUT_MILK, new Item.Properties().group(GroupRegistry.waterSourceGroup));
    });
}
