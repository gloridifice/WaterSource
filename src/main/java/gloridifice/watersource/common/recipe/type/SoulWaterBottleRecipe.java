/*
package gloridifice.watersource.common.recipe.type;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.item.StrainerBlockItem;
import gloridifice.watersource.registry.ItemRegistry;
import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Level;

import java.util.ArrayList;
import java.util.List;

public class SoulWaterBottleRecipe extends CustomRecipe {
    public SoulWaterBottleRecipe(ResourceLocation idIn) {
        super(idIn);
    }

    @Override
    public boolean matches(CraftingInventory inv, Level level) {
        ITag<Block> tag = BlockTags.getAllTags().getTag(new ResourceLocation(WaterSource.MODID, "soul_strainers"));
        List<ItemStack> list = new ArrayList<>();
        boolean hasStrainer = false, hasPotion = false;
        for (int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack itemstack = inv.getStackInSlot(j);
            if (!itemstack.isEmpty()) {
                if (tag != null && tag.contains(Block.getBlockFromItem(itemstack.getItem()))) {
                    list.add(itemstack);
                    hasStrainer = true;
                }
                if (itemstack.isItemEqual(new ItemStack(Items.POTION)) && ItemStack.areItemStackTagsEqual(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER), itemstack)) {
                    list.add(itemstack);
                    hasPotion = true;
                }
            }
        }
        return hasPotion && hasStrainer && list.size() == 2;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        return new ItemStack(ItemRegistry.itemSoulWaterBottle);
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        for (int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack itemstack = inv.getStackInSlot(j);
            ITag<Item> tag = ItemTags.getAllTags().getTag(new ResourceLocation(WaterSource.MODID, "soul_strainers"));
            if (!itemstack.isEmpty() && tag != null && tag.contains(itemstack.getItem())) {
                ItemStack i = StrainerBlockItem.hurt(itemstack.copy(), 1);
                nonnulllist.set(j, i);
            }
        }
        return nonnulllist;
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.CRAFTING_SOUL_WATER_BOTTLE.get();
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(ItemRegistry.itemSoulWaterBottle);
    }

}
*/
