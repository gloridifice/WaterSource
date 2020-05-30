package gloridifice.watersource.common.recipe.type;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.item.StrainerItem;
import gloridifice.watersource.registry.ItemRegistry;
import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipe;

import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;


public class StrainerUsingRecipe extends SpecialRecipe {

    public StrainerUsingRecipe(ResourceLocation idIn) {
        super(idIn);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        List<ItemStack> list = new ArrayList<>();
        boolean hasStrainer = false,hasPotion = false;
        for(int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack itemstack = inv.getStackInSlot(j);
            if (itemstack.getItem() instanceof StrainerItem){
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
        return true;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        for(int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack itemstack = inv.getStackInSlot(j);
            if (itemstack.getItem() instanceof StrainerItem){
                ItemStack i = itemstack.copy();
                if(i.getDamage() + 1 < i.getMaxDamage()){
                    i.setDamage(i.getDamage() + 1);
                }else i =ItemStack.EMPTY;
                nonnulllist.set(j,i);
            }
        }
        return nonnulllist;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.STRAINER_USING_CRAFTING.get();
    }
}
