package gloridifice.watersource.common.recipe.type;

import gloridifice.watersource.common.data.tag.ModTags;
import gloridifice.watersource.common.item.StrainerBlockItem;
import gloridifice.watersource.common.item.WoodenCupItem;
import gloridifice.watersource.helper.FluidHelper;
import gloridifice.watersource.registry.FluidRegistry;
import gloridifice.watersource.registry.ItemRegistry;
import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.tags.Tag;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class PurifiedWaterCupRecipe extends SpecialRecipe {
    protected Fluid fluid;
    protected Tag<Block> strainerTag;
    public PurifiedWaterCupRecipe(ResourceLocation idIn) {
        super(idIn);
        this.fluid = FluidRegistry.purifiedWaterFluid.get();
        this.strainerTag = ModTags.Block.PURIFICATION_STRAINER;
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        List<ItemStack> list = new ArrayList<>();
        boolean hasStrainer = false,hasPotion = false;
        for(int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack itemstack = inv.getStackInSlot(j);
            if (strainerTag.contains(Block.getBlockFromItem(itemstack.getItem()))){
                list.add(itemstack);
                hasStrainer = true;
            }
            if (itemstack.getItem() == ItemRegistry.itemWoodenCupDrink && ItemStack.areItemStackTagsEqual(itemstack,FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemWoodenCupDrink), Fluids.WATER))){
                list.add(itemstack);
                hasPotion = true;
            }
        }
        return hasPotion && hasStrainer && list.size() == 2;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        return getRecipeOutput();
    }
    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        for(int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack itemstack = inv.getStackInSlot(j);
            if (strainerTag.contains(Block.getBlockFromItem(itemstack.getItem()))){
                ItemStack i = StrainerBlockItem.damageItem(itemstack.copy(),1);
                nonnulllist.set(j,i);
            }
        }
        return nonnulllist;
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.CRAFTING_PURIFIED_WATER_CUP.get();
    }
    @Override
    public ItemStack getRecipeOutput() {
        return FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemWoodenCupDrink),this.fluid);
    }

}
