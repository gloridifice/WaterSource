package gloridifice.watersource.common.recipe.type;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.item.StrainerBlockItem;
import gloridifice.watersource.common.item.WaterBagItem;
import gloridifice.watersource.helper.FluidHelper;
import gloridifice.watersource.registry.BlockRegistry;
import gloridifice.watersource.registry.FluidRegistry;
import gloridifice.watersource.registry.ItemRegistry;
import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
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
    protected ResourceLocation strainerTag;
    public PurifiedWaterBagRecipe(ResourceLocation idIn) {
        super(idIn);
        this.fluid = FluidRegistry.PURIFIED_WATER.get();
        this.strainerTag = new ResourceLocation(WaterSource.MODID,"purification_strainers");
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        List<ItemStack> list = new ArrayList<>();
        boolean hasStrainer = false, hasPotion = false;
        ItemStack strainer = ItemStack.EMPTY;
        int potion = 0;
        for (int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack itemstack = inv.getStackInSlot(j);
            ITag<Item> tag = ItemTags.getCollection().get(strainerTag);
            if (tag != null && tag.contains(itemstack.getItem())) {
                list.add(itemstack);
                strainer = itemstack.copy();
                hasStrainer = true;
            }
            if (itemstack.getItem() instanceof WaterBagItem && FluidHelper.isItemStackFluidEqual(itemstack, FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemWoodenCupDrink), Fluids.WATER))) {
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
            if (itemstack.getItem() instanceof WaterBagItem && FluidHelper.isItemStackFluidEqual(itemstack, FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemWoodenCupDrink), Fluids.WATER))) {
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
            if (itemstack.getItem() instanceof WaterBagItem && FluidHelper.isItemStackFluidEqual(itemstack, FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemWoodenCupDrink), Fluids.WATER))) {
                n = FluidUtil.getFluidHandler(itemstack).map(data -> data.getFluidInTank(0).getAmount()).orElse(0);
            }
        }
        for (int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack itemstack = inv.getStackInSlot(j);
            ITag<Item> tag = ItemTags.getCollection().get(strainerTag);
            if (!itemstack.isEmpty() && tag != null && tag.contains(itemstack.getItem())) {
                nonnulllist.set(j, StrainerBlockItem.damageItem(itemstack.copy(),n / 250));
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
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(Ingredient.fromStacks(new ItemStack(ItemRegistry.itemLeatherWaterBag)));
        list.add(Ingredient.fromStacks(new ItemStack(BlockRegistry.ITEM_PAPER_STRAINER)));
        list.add(Ingredient.fromStacks(new ItemStack(Items.APPLE)));
        return list;
    }

}
