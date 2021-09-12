/*
package gloridifice.watersource.common.recipe.type;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.item.StrainerBlockItem;
import gloridifice.watersource.registry.ItemRegistry;
import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Level;

import java.util.ArrayList;
import java.util.List;


public class PurifiedWaterBottleRecipe extends CustomRecipe {
    public PurifiedWaterBottleRecipe(ResourceLocation idIn) {
        super(idIn);
    }

    @Override
    public boolean matches(CraftingInventory inv, Level level) {
        ITag<Item> tag = ItemTags.getAllTags().getTag(new ResourceLocation(WaterSource.MODID, "purification_strainers"));
        List<ItemStack> list = new ArrayList<>();
        boolean hasStrainer = false, hasPotion = false;
        for (int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack itemstack = inv.getStackInSlot(j);
            if (tag != null && tag.contains(itemstack.getItem())) {
                list.add(itemstack);
                hasStrainer = true;
            }
            if (itemstack.isItemEqual(new ItemStack(Items.POTION)) && ItemStack.areItemStackTagsEqual(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER), itemstack)) {
                list.add(itemstack);
                hasPotion = true;
            }
        }

        return hasPotion && hasStrainer && list.size() == 2;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        return new ItemStack(ItemRegistry.itemPurifiedWaterBottle);
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        for (int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack itemstack = inv.getStackInSlot(j);
            ITag<Item> tag = ItemTags.getAllTags().getTag(new ResourceLocation(WaterSource.MODID, "purification_strainers"));
            if (!itemstack.isEmpty() && tag != null && tag.contains(itemstack.getItem())) {
                ItemStack i = StrainerBlockItem.hurt(itemstack.copy(), 1);
                nonnulllist.set(j, i);
            }
        }
        return nonnulllist;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.CRAFTING_PURIFIED_WATER_BOTTLE.get();
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(ItemRegistry.itemPurifiedWaterBottle);
    }

*/
/*    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.withSize(2, Ingredient.EMPTY);
        for (Block block : ModTags.Block.PURIFICATION_STRAINER.getAllElements()){
            list.set(0, Ingredient.fromStacks(new ItemStack(block.asItem())));
        }
        list.set(1, Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER)));
        return list;
    }*//*

}
*/
