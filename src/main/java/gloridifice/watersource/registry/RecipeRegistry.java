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
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;


public class RecipeRegistry {

    public static void init() {
        addWaterLevel();
        //addThirstItem();
        addWaterFilterRecipes();
    }
    public static void addWaterLevel() {
        WaterLevelRecipeManager.add(new ItemStack(ItemRegistry.itemSoulWaterBottle), 4, 3);
        WaterLevelRecipeManager.add(new ItemStack(ItemRegistry.itemPurifiedWaterBottle), 5, 6);
        WaterLevelRecipeManager.add(new ItemStack(ItemRegistry.itemCoconutJuiceBottle), 4, 6);
        WaterLevelRecipeManager.add(new WaterLevelFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemWoodenCupDrink), Fluids.WATER),4,2));
        WaterLevelRecipeManager.add(new WaterLevelFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemWoodenCupDrink), FluidRegistry.PURIFIED_WATER.get()),5,6));
        WaterLevelRecipeManager.add(new WaterLevelFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemWoodenCupDrink), FluidRegistry.SOUL_WATER.get()),4,3));
        WaterLevelRecipeManager.add(new WaterLevelFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemWoodenCupDrink), FluidRegistry.COCONUT_MILK.get()),4,6));
        WaterLevelRecipeManager.add(new WaterLevelFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemLeatherWaterBag), Fluids.WATER),4,2));
        WaterLevelRecipeManager.add(new WaterLevelFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemLeatherWaterBag), FluidRegistry.PURIFIED_WATER.get()),5,6));
        WaterLevelRecipeManager.add(new WaterLevelFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemLeatherWaterBag), FluidRegistry.SOUL_WATER.get()),4,3));
        WaterLevelRecipeManager.add(new WaterLevelFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemLeatherWaterBag), FluidRegistry.COCONUT_MILK.get()),4,6));
        WaterLevelRecipeManager.add(new ItemStack(ItemRegistry.itemCoconutPiece),2,2);
        WaterLevelRecipeManager.add(new ItemStack(Items.POTION), 4, 2);

        WaterLevelRecipeManager.add(new ItemStack(Items.APPLE), 2, 0);
        WaterLevelRecipeManager.add(new ItemStack(Items.SWEET_BERRIES), 1, 0);
        WaterLevelRecipeManager.add(new ItemStack(Items.GOLDEN_APPLE), 2, 6);
        WaterLevelRecipeManager.add(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE), 2, 16);
        WaterLevelRecipeManager.add(new ItemStack(Items.MELON_SLICE), 1, 0);

        if (ModList.get().isLoaded("afterthedrizzle")) {
            addWaterLevelByName("afterthedrizzle","porcelain_cup_drink",4, 3);
            addWaterLevelByName("afterthedrizzle","bottle_drink",7, 6);
        }

        if (ModList.get().isLoaded("simplefarming")) {
            addWaterLevelByName("simplefarming","pear",2,0);
            addWaterLevelByName("simplefarming","grapes",2,0);
            addWaterLevelByName("simplefarming","strawberries",2,0);
            addWaterLevelByName("simplefarming","tiswin",2,0);
            addWaterLevelByName("simplefarming","tomato",2,0);
            addWaterLevelByName("simplefarming","blueberries",2,0);
            addWaterLevelByName("simplefarming","raspberries",2,0);
            addWaterLevelByName("simplefarming","plum",1,0);
            addWaterLevelByName("simplefarming","vodka",2,0);
            addWaterLevelByName("simplefarming","cherries",1,0);
            addWaterLevelByName("simplefarming","sake",2,0);
            addWaterLevelByName("simplefarming","wine",2,0);
            addWaterLevelByName("simplefarming","cauim",2,0);
            addWaterLevelByName("simplefarming","whiskey",2,0);
            addWaterLevelByName("simplefarming","cider",2,0);
            addWaterLevelByName("simplefarming","mead",2,0);
            addWaterLevelByName("simplefarming","beer",2,0);
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
            addWaterLevelByName("vanillafoodpantry","mixed_berries",3,3);
            addWaterLevelByName("vanillafoodpantry","bottle_tulip_tonic",5,6);
            addWaterLevelByName("vanillafoodpantry","teadrink_sprucetips_tea",4,2);

            addWaterLevelByName("vanillafoodpantry","teadrink_milky_dandtea",3,2);

            addWaterLevelByName("vanillafoodpantry","juice_melon",4,2);
            addWaterLevelByName("vanillafoodpantry","juice_carrot",3,1);
            addWaterLevelByName("vanillafoodpantry","juice_apple",4,2);
            addWaterLevelByName("vanillafoodpantry","juice_raftugli",3,1);
            addWaterLevelByName("vanillafoodpantry","juice_berries",3,1);

            addWaterLevelByName("vanillafoodpantry","bottle_rose_water",4,1);
            addWaterLevelByName("vanillafoodpantry","bucket_potable_water",6,3);
            addWaterLevelByName("vanillafoodpantry","teadrink_milky_bushtea",3,0);
            addWaterLevelByName("vanillafoodpantry","bottle_swamp_water",2,0);
            addWaterLevelByName("vanillafoodpantry","milkdrink_apple",2,0);
            addWaterLevelByName("vanillafoodpantry","milkdrink_klingon",2,0);
            addWaterLevelByName("vanillafoodpantry","milkdrink_berry_mix",2,0);
            addWaterLevelByName("vanillafoodpantry","milkdrink_pumpkin",2,0);
            addWaterLevelByName("vanillafoodpantry","tonicdrink",2,0);
            addWaterLevelByName("vanillafoodpantry","teadrink_bushtea",3,0);
            addWaterLevelByName("vanillafoodpantry","bottle_water",3,0);
            addWaterLevelByName("vanillafoodpantry","jungle_juice_jar",4,0);

            addWaterLevelByName("vanillafoodpantry","snocone_berry",1,0);
            addWaterLevelByName("vanillafoodpantry","snocone_chocolate",1,0);
            addWaterLevelByName("vanillafoodpantry","snocone",1,0);
            addWaterLevelByName("vanillafoodpantry","snocone_chorusfruit",1,0);
            addWaterLevelByName("vanillafoodpantry","teadrink_dandtea",3,0);
            addWaterLevelByName("vanillafoodpantry","fizzydrink_milk",2,0);
            addWaterLevelByName("vanillafoodpantry","fizzydrink_dandy",3,0);
            addWaterLevelByName("vanillafoodpantry","fizzydrink_apple",3,0);
            addWaterLevelByName("vanillafoodpantry","fizzydrink_coco",3,0);
            addWaterLevelByName("vanillafoodpantry","fizzydrink_syrup",3,0);
            addWaterLevelByName("vanillafoodpantry","fizzydrink_melon",3,1);
            addWaterLevelByName("vanillafoodpantry","fizzydrink_klingon",3,0);
            addWaterLevelByName("vanillafoodpantry","fizzydrink_ink",3,0);
            addWaterLevelByName("vanillafoodpantry","fizzydrink_berry_mix",3,0);
            addWaterLevelByName("vanillafoodpantry","bottle_fizzy_water",3,0);

            addWaterLevelByName("vanillafoodpantry","honey_glazed_melon_bites",3,0);

/*            WaterLevelRecipeManager.add(new ItemStack(VfpObj.Mixed_Berries_obj),4,3);
            WaterLevelRecipeManager.add(new ItemStack(VfpObj.Tonic_Drink_obj),2,2);
            WaterLevelRecipeManager.add(new ItemStack(VfpObj.Jungle_JuJu_Juice_obj),2,2);
            WaterLevelRecipeManager.add(new WaterLevelFluidRecipe(VfpObj.Water_Bottle_obj,2,2));*/

        }
    }

/*    public static void addThirstItem() {
        ThirstRecipeManager.add(new ThirstNbtRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER), 2000, 0, 75));
        ThirstRecipeManager.add(new ThirstFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemWoodenCupDrink), Fluids.WATER), 2000,0,75));
        ThirstRecipeManager.add(new ThirstFluidRecipe(FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemLeatherWaterBag), Fluids.WATER), 2000,0,75));
        if (ModList.get().isLoaded("vanillafoodpantry")){
            addThirstByName("vanillafoodpantry","bottle_water",1800,0,75);
            addThirstByName("vanillafoodpantry","bottle_swamp_water",2200,0,75);
        }
    }*/

    public static void addWaterFilterRecipes() {
        WaterFilterRecipeManager.add((StrainerBlockItem) BlockRegistry.ITEM_PRIMITIVE_STRAINER, Fluids.WATER, FluidRegistry.PURIFIED_WATER.get());
        WaterFilterRecipeManager.add((StrainerBlockItem) BlockRegistry.ITEM_SOUL_STRAINER, Fluids.WATER, FluidRegistry.SOUL_WATER.get());
        WaterFilterRecipeManager.add((StrainerBlockItem) BlockRegistry.ITEM_PAPER_STRAINER, Fluids.WATER, FluidRegistry.PURIFIED_WATER.get());
        WaterFilterRecipeManager.add((StrainerBlockItem) BlockRegistry.ITEM_PAPER_SOUL_STRAINER, Fluids.WATER, FluidRegistry.SOUL_WATER.get());
        /*        for (Item item : ModTags.Item.SOUL_STRAINER.getAllElements()){
            WaterFilterRecipeManager.add(item, Fluids.WATER, FluidRegistry.purifiedWaterFluid.get());
        }*/
    }
    private static void addWaterLevelByName(String modid, String name, int waterLevel, int saturation){
        WaterLevelRecipeManager.add(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid,name))),waterLevel,saturation);
    }
/*    private static void addThirstByName(String modid, String name, int duration, int amplifier,int probability){
        ThirstRecipeManager.add(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid,name))),duration,amplifier,probability);
    }*/
}
