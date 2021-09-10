package gloridifice.watersource.common.recipe.type;

import gloridifice.watersource.registry.FluidRegistry;
import gloridifice.watersource.registry.ItemRegistry;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import static gloridifice.watersource.common.recipe.NormalRecipeTypes.PURIFIED_WATER_COOKING_RECIPE;

public class PurifiedWaterCookingRecipe extends AbstractCookingRecipe {
    //ResourceLocation var1, String var2, Ingredient var3, ItemStack var4, float var5, int var6
    public PurifiedWaterCookingRecipe(ResourceLocation resourceLocation, String group, Ingredient ingredient, ItemStack stack, float v, int i) {
        super(PURIFIED_WATER_COOKING_RECIPE, resourceLocation, group, ingredient, stack, v, i);
    }

    @Override
    public boolean matches(IInventory inventory, World world) {
        ItemStack stack = inventory.getStackInSlot(0);
        IFluidHandlerItem handler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
        if (stack.hasContainerItem() && super.matches(inventory, world)){
            if (handler.getFluidInTank(0).getFluid() == Fluids.WATER) return true;
            else return false;
        }
        return super.matches(inventory, world);
    }

    @Override
    public ItemStack getCraftingResult(IInventory inventory) {
        ItemStack stack = inventory.getStackInSlot(0);
        if (stack.getItem() == Items.POTION && PotionUtils.getPotionFromItem(stack) == Potions.WATER){
            return new ItemStack(ItemRegistry.itemPurifiedWaterBottle);
        }
        IFluidHandlerItem handler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
        if (stack.hasContainerItem()){
            int amount = handler.getFluidInTank(0).getAmount();
            handler.drain(handler.getTankCapacity(0), IFluidHandler.FluidAction.EXECUTE);
            handler.fill(new FluidStack(FluidRegistry.PURIFIED_WATER.get(), amount), IFluidHandler.FluidAction.EXECUTE);
            return stack;
        }
        return super.getCraftingResult(inventory);
    }
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return IRecipeSerializer.SMELTING;
    }

}
