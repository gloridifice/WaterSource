package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.item.*;
import gloridifice.watersource.common.item.food.ModFoods;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.NbtIo;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemRegistry extends RegistryModule {
    public static final DeferredRegister<Item> FLUID_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WaterSource.MODID);

    public final static Item itemPurifiedWaterBottle = new WaterBottleItem("purified_water_bottle");
    public final static Item itemCoconutJuiceBottle = new WaterBottleItem("coconut_milk_bottle");
    public final static Item itemSoulWaterBottle = new SoulWaterBottleItem("soul_water_bottle");
    public final static Item itemWoodenCup = new WoodenCupItem("wooden_cup", new Item.Properties().tab(null), 250) {
        @Override
        public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundTag nbt) {
            return super.initCapabilities(new ItemStack(itemWoodenCupDrink), nbt);
        }
    };
    public final static Item HALF_COCONUT = new ModNormalItem("half_coconut");
    public final static Item COCONUT_PIECE = new ModFoodItem("coconut_piece", ModFoods.COCONUT_PIECES);
    public final static Item COCONUT_CHICKEN = new ModFoodItem("coconut_chicken", ModFoods.COCONUT_CHICKEN);
    public final static Item itemWoodenCupDrink = new WoodenCupItem("wooden_cup_drink", new Item.Properties().stacksTo(1).tab(CreativeModeTabRegistry.WATER_SOURCE_TAB), 250);
    public final static Item itemLeatherWaterBag = new WaterBagItem("leather_water_bag", new Item.Properties().stacksTo(1).setNoRepair().tab(CreativeModeTabRegistry.WATER_SOURCE_TAB), 1500);
    //Fluids
    public static RegistryObject<Item> itemPurifiedWaterBucket = FLUID_ITEMS.register("purified_water_bucket", () -> {
        return new BucketItem(FluidRegistry.PURIFIED_WATER, new Item.Properties().tab(CreativeModeTabRegistry.WATER_SOURCE_TAB));
    });
    public static RegistryObject<Item> itemSoulWaterBucket = FLUID_ITEMS.register("soul_water_bucket", () -> {
        return new BucketItem(FluidRegistry.SOUL_WATER, new Item.Properties().tab(CreativeModeTabRegistry.WATER_SOURCE_TAB));
    });
    public static RegistryObject<Item> ITEM_COCONUT_MILK_BUCKET = FLUID_ITEMS.register("coconut_milk_bucket", () -> {
        return new BucketItem(FluidRegistry.COCONUT_MILK, new Item.Properties().tab(CreativeModeTabRegistry.WATER_SOURCE_TAB));
    });
}
