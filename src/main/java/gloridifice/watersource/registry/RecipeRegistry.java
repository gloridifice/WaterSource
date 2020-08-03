package gloridifice.watersource.registry;

import gloridifice.watersource.common.item.StrainerBlockItem;
import gloridifice.watersource.common.recipe.*;
import gloridifice.watersource.helper.FluidHelper;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraftforge.fml.ModList;
import roito.afterthedrizzle.common.item.ItemsRegistry;

public class RecipeRegistry {

    public static void init() {
        addWaterLevel();
        addThirstItem();
        addWaterFilterRecipes();
    }
    public static void addWaterLevel() {
        WaterLevelRecipeManager.add(new ItemStack(ItemRegistry.itemSoulWaterBottle), 4, 4);
        WaterLevelRecipeManager.add(new ItemStack(ItemRegistry.itemPurifiedWaterBottle), 6, 8);
        WaterLevelRecipeManager.add(new ItemStack(ItemRegistry.itemCoconutJuiceBottle), 5, 7);
        WaterLevelRecipeManager.add(new WaterLevelFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemWoodenCupDrink), Fluids.WATER),4,2));
        WaterLevelRecipeManager.add(new WaterLevelFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemWoodenCupDrink), FluidRegistry.purifiedWaterFluid.get()),6,8));
        WaterLevelRecipeManager.add(new WaterLevelFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemWoodenCupDrink), FluidRegistry.soulWaterFluid.get()),4,4));
        WaterLevelRecipeManager.add(new WaterLevelFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemWoodenCupDrink), FluidRegistry.coconutJuiceFluid.get()),5,7));
        WaterLevelRecipeManager.add(new WaterLevelFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemFeatherWaterBag), Fluids.WATER),4,2));
        WaterLevelRecipeManager.add(new WaterLevelFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemFeatherWaterBag), FluidRegistry.purifiedWaterFluid.get()),6,8));
        WaterLevelRecipeManager.add(new WaterLevelFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemFeatherWaterBag), FluidRegistry.soulWaterFluid.get()),4,4));
        WaterLevelRecipeManager.add(new WaterLevelFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemFeatherWaterBag), FluidRegistry.coconutJuiceFluid.get()),5,7));

        WaterLevelRecipeManager.add(new ItemStack(Items.APPLE), 2, 1);
        WaterLevelRecipeManager.add(new ItemStack(Items.SWEET_BERRIES), 1, 1);
        WaterLevelRecipeManager.add(new ItemStack(Items.POTION), 4, 2);
        WaterLevelRecipeManager.add(new ItemStack(Items.GOLDEN_APPLE), 2, 6);
        WaterLevelRecipeManager.add(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE), 2, 6);
        WaterLevelRecipeManager.add(new ItemStack(Items.CARROT), 1, 0);
        WaterLevelRecipeManager.add(new ItemStack(Items.MELON_SLICE), 3, 1);
        WaterLevelRecipeManager.add(new ItemStack(Items.RABBIT_STEW), 3, 1);
        WaterLevelRecipeManager.add(new ItemStack(Items.MUSHROOM_STEW), 2, 1);
        WaterLevelRecipeManager.add(new ItemStack(Items.MILK_BUCKET), 1, 0);
        if (ModList.get().isLoaded("afterthedrizzle")) {
            WaterLevelRecipeManager.add(new ItemStack(ItemsRegistry.PORCELAIN_CUP_DRINK), 4, 3);
            WaterLevelRecipeManager.add(new ItemStack(ItemsRegistry.BOTTLE_DRINK), 7, 6);
        }
    }

    public static void addThirstItem() {
        ThirstRecipeManager.add(new ThirstNbtRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER), 2000, 0, 75));
        ThirstRecipeManager.add(new ThirstFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemWoodenCupDrink), Fluids.WATER), 2000,0,75));
        ThirstRecipeManager.add(new ThirstFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemFeatherWaterBag), Fluids.WATER), 2000,0,75));
    }

    public static void addWaterFilterRecipes() {
        WaterFilterRecipeManager.add((StrainerBlockItem) BlockRegistry.itemPrimitiveStrainer, Fluids.WATER, FluidRegistry.purifiedWaterFluid.get());
        WaterFilterRecipeManager.add((StrainerBlockItem) BlockRegistry.itemSoulStrainer, Fluids.WATER, FluidRegistry.soulWaterFluid.get());
        WaterFilterRecipeManager.add((StrainerBlockItem) BlockRegistry.itemPaperStrainer, Fluids.WATER, FluidRegistry.purifiedWaterFluid.get());
        WaterFilterRecipeManager.add((StrainerBlockItem) BlockRegistry.itemPaperSoulStrainer, Fluids.WATER, FluidRegistry.soulWaterFluid.get());
        /*        for (Item item : ModTags.Item.SOUL_STRAINER.getAllElements()){
            WaterFilterRecipeManager.add(item, Fluids.WATER, FluidRegistry.purifiedWaterFluid.get());
        }*/
    }
}
