package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.item.*;
import gloridifice.watersource.common.item.food.ModFoods;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemRegistry extends RegistryModule {
    public static final DeferredRegister<Item> FLUID_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WaterSource.MODID);

    public final static Item FLUID_BOTTLE = new FluidBottleItem("fluid_bottle", new Item.Properties().stacksTo(16));
    public final static Item WOODEN_CUP = new WoodenCupItem("wooden_cup", new Item.Properties(), 250) {
        @Override
        public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundTag nbt) {
            return super.initCapabilities(new ItemStack(WOODEN_CUP_DRINK), nbt);
        }
    };
    public final static Item HALF_COCONUT = new ModNormalItem("half_coconut");
    public final static Item WOODEN_CUP_DRINK = new WoodenCupItem("wooden_cup_drink", new Item.Properties().stacksTo(1).tab(CreativeModeTabRegistry.WATER_SOURCE_TAB), 250);
    public final static Item LEATHER_WATER_BAG = new DurableDrinkContainerItem("leather_water_bag", new Item.Properties().stacksTo(1).setNoRepair().tab(CreativeModeTabRegistry.WATER_SOURCE_TAB), 1500);
    public final static Item IRON_BOTTLE = new DurableDrinkContainerItem("iron_bottle", new Item.Properties().stacksTo(1).setNoRepair().tab(CreativeModeTabRegistry.WATER_SOURCE_TAB), 1500);

    //FOOD
    public final static Item COCONUT_MILK_BOTTLE = new CoconutMilkBottleItem("coconut_juice_bottle", ModFoods.COCONUT_MILK);
    public final static Item COCONUT_PIECE = new ModFoodItem("coconut_piece", ModFoods.COCONUT_PIECES);
    public final static Item COCONUT_CHICKEN = new ModFoodItem("coconut_chicken", ModFoods.COCONUT_CHICKEN);
    //Fluids
    public static RegistryObject<Item> itemPurifiedWaterBucket = FLUID_ITEMS.register("purified_water_bucket", () -> {
        return new BucketItem(FluidRegistry.PURIFIED_WATER, new Item.Properties().tab(CreativeModeTabRegistry.WATER_SOURCE_TAB));
    });
    public static RegistryObject<Item> itemSoulWaterBucket = FLUID_ITEMS.register("soul_water_bucket", () -> {
        return new BucketItem(FluidRegistry.SOUL_WATER, new Item.Properties().tab(CreativeModeTabRegistry.WATER_SOURCE_TAB));
    });
    public static RegistryObject<Item> ITEM_COCONUT_MILK_BUCKET = FLUID_ITEMS.register("coconut_milk_bucket", () -> {
        return new BucketItem(FluidRegistry.COCONUT_JUICE, new Item.Properties().tab(CreativeModeTabRegistry.WATER_SOURCE_TAB));
    });
}
