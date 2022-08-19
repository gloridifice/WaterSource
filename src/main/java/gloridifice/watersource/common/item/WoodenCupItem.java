package gloridifice.watersource.common.item;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.helper.FluidHelper;
import gloridifice.watersource.registry.FluidRegistry;
import gloridifice.watersource.registry.ItemRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import java.util.ArrayList;
import java.util.List;

public class WoodenCupItem extends DrinkContainerItem {
    public WoodenCupItem(Properties properties, int capacity) {
        super(properties, capacity);
    }
    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
        List<Fluid> fluids = new ArrayList<>();
        fluids.add(FluidRegistry.PURIFIED_WATER.get());
        fluids.add(FluidRegistry.SOUL_WATER.get());
        fluids.add(Fluids.WATER);
        if (this.allowdedIn(tab) && this == ItemRegistry.WOODEN_CUP_DRINK.get()) {
            for (Fluid fluid : fluids) {
                ItemStack itemStack = new ItemStack(ItemRegistry.WOODEN_CUP_DRINK.get());
                items.add(FluidHelper.fillContainer(itemStack, fluid));
            }
            items.add(new ItemStack(ItemRegistry.WOODEN_CUP.get()));
        }
    }
    @Override
    public Component getName(ItemStack stack) {
        IFluidHandlerItem fluidHandlerItem =  stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
        FluidStack fluidStack = fluidHandlerItem.getFluidInTank(0);
        if (fluidStack.isEmpty()) return super.getName(stack);

        Component component = fluidStack.getDisplayName();
        return component.copy().append(new TranslatableComponent("item.watersource.wooden_cup_drink"));
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return new ItemStack(ItemRegistry.WOODEN_CUP.get());
    }

    @Override
    public ItemStack getDrinkItem() {
        return new ItemStack(ItemRegistry.WOODEN_CUP_DRINK.get());
    }
}
