package gloridifice.watersource.common.recipe.type;

import gloridifice.watersource.common.data.tag.ModTags;
import gloridifice.watersource.common.item.StrainerBlockItem;
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
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.ArrayList;
import java.util.List;

public class PurifiedWaterBagRecipe extends SpecialRecipe {
    protected Fluid fluid;
    protected INamedTag<Block> strainerTag;
    public PurifiedWaterBagRecipe(ResourceLocation idIn) {
        super(idIn);
        this.fluid = FluidRegistry.PURIFIED_WATER.get();
        this.strainerTag = ModTags.Block.PURIFICATION_STRAINERS;
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        List<ItemStack> list = new ArrayList<>();
        boolean hasStrainer = false, hasPotion = false;
        ItemStack strainer = ItemStack.EMPTY;
        int potion = 0;
        for (int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack itemstack = inv.getStackInSlot(j);
            if (this.strainerTag.contains(Block.getBlockFromItem(itemstack.getItem()))) {
                list.add(itemstack);
                strainer = itemstack.copy();
                hasStrainer = true;
            }
            if (itemstack.getItem() == ItemRegistry.itemLeatherWaterBag && FluidHelper.isItemStackFluidEqual(itemstack, FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemWoodenCupDrink), Fluids.WATER))) {
                list.add(itemstack);
                potion = FluidUtil.getFluidHandler(itemstack).map(date -> date.getFluidInTank(0).getAmount()).orElse(0);
                hasPotion = true;
            }
        }
        return hasPotion && hasStrainer && list.size() == 2 && potion != 0 && (strainer.getMaxDamage() - strainer.getDamage()) >= potion / 250;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        for (int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack itemstack = inv.getStackInSlot(j);
            if (itemstack.getItem() == ItemRegistry.itemLeatherWaterBag && FluidHelper.isItemStackFluidEqual(itemstack, FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemWoodenCupDrink), Fluids.WATER))) {
                ItemStack i = itemstack.copy();
                FluidUtil.getFluidHandler(i).ifPresent(data -> {
                    int n = data.getFluidInTank(0).getAmount();
                    data.drain(n, IFluidHandler.FluidAction.EXECUTE);
                    data.fill(new FluidStack(this.fluid, n), IFluidHandler.FluidAction.EXECUTE);
                });
                return i;
            }
        }
        return getRecipeOutput();
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        int n = 0;
        for (int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack itemstack = inv.getStackInSlot(j);
            if (itemstack.getItem() == ItemRegistry.itemLeatherWaterBag && FluidHelper.isItemStackFluidEqual(itemstack, FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemWoodenCupDrink), Fluids.WATER))) {
                ItemStack u = itemstack.copy();
                n = FluidUtil.getFluidHandler(u).map(data -> data.getFluidInTank(0).getAmount()).orElse(0);
            }
        }
        for (int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack itemstack = inv.getStackInSlot(j);
            if (this.strainerTag.contains(Block.getBlockFromItem(itemstack.getItem()))) {
                ItemStack i = StrainerBlockItem.damageItem(itemstack.copy(),n / 250);
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
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.CRAFTING_PURIFIED_WATER_BAG.get();
    }

    @Override
    public ItemStack getRecipeOutput() {
        return FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemLeatherWaterBag), this.fluid);
    }
}
