package gloridifice.watersource.common.recipe.type;

import gloridifice.watersource.common.data.tag.ModTags;
import gloridifice.watersource.common.item.StrainerBlockItem;
import gloridifice.watersource.registry.ItemRegistry;
import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.block.Block;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;

import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;


public class PurifiedWaterBottleRecipe extends SpecialRecipe {
    public PurifiedWaterBottleRecipe(ResourceLocation idIn) {
        super(idIn);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        List<ItemStack> list = new ArrayList<>();
        boolean hasStrainer = false,hasPotion = false;
        for(int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack itemstack = inv.getStackInSlot(j);
            if (ModTags.Block.PURIFICATION_STRAINERS.contains(Block.getBlockFromItem(itemstack.getItem()))){
                list.add(itemstack);
                hasStrainer = true;
            }
            if (itemstack.isItemEqual(new ItemStack(Items.POTION)) && ItemStack.areItemStackTagsEqual(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER),itemstack)){
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
        for(int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack itemstack = inv.getStackInSlot(j);
            if (ModTags.Block.PURIFICATION_STRAINERS.contains(Block.getBlockFromItem(itemstack.getItem()))){
                ItemStack i = StrainerBlockItem.damageItem(itemstack.copy(),1);
                nonnulllist.set(j,i);
            }
        }
        return nonnulllist;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.CRAFTING_PURIFIED_WATER_BOTTLE.get();
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(ItemRegistry.itemPurifiedWaterBottle);
    }

/*    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.withSize(2, Ingredient.EMPTY);
        for (Block block : ModTags.Block.PURIFICATION_STRAINER.getAllElements()){
            list.set(0, Ingredient.fromStacks(new ItemStack(block.asItem())));
        }
        list.set(1, Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER)));
        return list;
    }*/
}
