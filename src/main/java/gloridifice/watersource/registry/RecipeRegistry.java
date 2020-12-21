package gloridifice.watersource.registry;

//import enemeez.simplefarming.init.ModItems;
import gloridifice.watersource.common.item.StrainerBlockItem;
import gloridifice.watersource.common.recipe.*;
import gloridifice.watersource.helper.FluidHelper;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraftforge.fml.ModList;
//import org.jwaresoftware.mcmods.vfp.common.VfpObj;
//import roito.afterthedrizzle.common.item.ItemsRegistry;

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
        WaterLevelRecipeManager.add(new WaterLevelFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemWoodenCupDrink), FluidRegistry.PURIFIED_WATER.get()),6,8));
        WaterLevelRecipeManager.add(new WaterLevelFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemWoodenCupDrink), FluidRegistry.SOUL_WATER.get()),4,4));
        WaterLevelRecipeManager.add(new WaterLevelFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemWoodenCupDrink), FluidRegistry.COCONUT_MILK.get()),5,7));
        WaterLevelRecipeManager.add(new WaterLevelFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemLeatherWaterBag), Fluids.WATER),4,2));
        WaterLevelRecipeManager.add(new WaterLevelFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemLeatherWaterBag), FluidRegistry.PURIFIED_WATER.get()),6,8));
        WaterLevelRecipeManager.add(new WaterLevelFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemLeatherWaterBag), FluidRegistry.SOUL_WATER.get()),4,4));
        WaterLevelRecipeManager.add(new WaterLevelFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemLeatherWaterBag), FluidRegistry.COCONUT_MILK.get()),5,7));
        WaterLevelRecipeManager.add(new ItemStack(ItemRegistry.itemCoconutPiece),2,3);

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
//            WaterLevelRecipeManager.add(new ItemStack(ItemsRegistry.PORCELAIN_CUP_DRINK), 4, 3);
//            WaterLevelRecipeManager.add(new ItemStack(ItemsRegistry.BOTTLE_DRINK), 7, 6);
        }
        if (ModList.get().isLoaded("simplefarming")) {
/*            WaterLevelRecipeManager.add(new ItemStack(ModItems.pear), 2, 1);
            WaterLevelRecipeManager.add(new ItemStack(ModItems.grapes), 2, 1);
            WaterLevelRecipeManager.add(new ItemStack(ModItems.strawberries), 2, 1);
            WaterLevelRecipeManager.add(new ItemStack(ModItems.tiswin), 2, 1);
            WaterLevelRecipeManager.add(new ItemStack(ModItems.tomato), 2, 0);
            WaterLevelRecipeManager.add(new ItemStack(ModItems.blueberries), 2, 0);
            WaterLevelRecipeManager.add(new ItemStack(ModItems.raspberries), 2, 0);
            WaterLevelRecipeManager.add(new ItemStack(ModItems.vodka), 1, 0);
            WaterLevelRecipeManager.add(new ItemStack(ModItems.cherries), 1, 1);
            WaterLevelRecipeManager.add(new ItemStack(ModItems.sake), 1, 0);
            WaterLevelRecipeManager.add(new ItemStack(ModItems.wine), 1, 0);
            WaterLevelRecipeManager.add(new ItemStack(ModItems.cauim), 1, 0);
            WaterLevelRecipeManager.add(new ItemStack(ModItems.whiskey), 1, 0);
            WaterLevelRecipeManager.add(new ItemStack(ModItems.beer), 1, 0);
            WaterLevelRecipeManager.add(new ItemStack(ModItems.cider), 1, 0);*/
        }
        if (ModList.get().isLoaded("vanillafoodpantry")){
/*            WaterLevelRecipeManager.add(new ItemStack(VfpObj.Mixed_Berries_obj),4,3);
            WaterLevelRecipeManager.add(new ItemStack(VfpObj.Tonic_Drink_obj),2,2);
            WaterLevelRecipeManager.add(new ItemStack(VfpObj.Jungle_JuJu_Juice_obj),2,2);
            WaterLevelRecipeManager.add(new WaterLevelFluidRecipe(VfpObj.Water_Bottle_obj,2,2));*/

        }
    }

    public static void addThirstItem() {
        ThirstRecipeManager.add(new ThirstNbtRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER), 2000, 0, 75));
        ThirstRecipeManager.add(new ThirstFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemWoodenCupDrink), Fluids.WATER), 2000,0,75));
        ThirstRecipeManager.add(new ThirstFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemLeatherWaterBag), Fluids.WATER), 2000,0,75));
    }

    public static void addWaterFilterRecipes() {
        WaterFilterRecipeManager.add((StrainerBlockItem) BlockRegistry.ITEM_PRIMITIVE_STRAINER, Fluids.WATER, FluidRegistry.PURIFIED_WATER.get());
        WaterFilterRecipeManager.add((StrainerBlockItem) BlockRegistry.ITEM_SOUL_STRAINER, Fluids.WATER, FluidRegistry.SOUL_WATER.get());
        WaterFilterRecipeManager.add((StrainerBlockItem) BlockRegistry.ITEM_PAPER_STRAINER, Fluids.WATER, FluidRegistry.PURIFIED_WATER.get());
        WaterFilterRecipeManager.add((StrainerBlockItem) BlockRegistry.ITEM_PAPER_SOUL_STRAINER, Fluids.WATER, FluidRegistry.SOUL_WATER.get());
        /*        for (Item item : ModTags.Item.SOUL_STRAINER.getAllElements()){
            WaterFilterRecipeManager.add(item, Fluids.WATER, FluidRegistry.purifiedWaterFluid.get());
        }*/
    }
}
