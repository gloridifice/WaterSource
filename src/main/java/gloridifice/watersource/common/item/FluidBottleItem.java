package gloridifice.watersource.common.item;

import gloridifice.watersource.helper.FluidHelper;
import gloridifice.watersource.registry.FluidRegistry;
import gloridifice.watersource.registry.ItemRegistry;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import java.util.ArrayList;
import java.util.List;

public class FluidBottleItem extends DrinkContainerItem{
    public FluidBottleItem(Properties properties) {
        super(properties, 250);
    }
    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
        List<Fluid> fluids = new ArrayList<>();
        fluids.add(FluidRegistry.PURIFIED_WATER.get());
        fluids.add(FluidRegistry.SOUL_WATER.get());
        fluids.add(FluidRegistry.COCONUT_JUICE.get());
        if (this.allowdedIn(tab) && this == ItemRegistry.FLUID_BOTTLE.get()) {
            for (Fluid fluid : fluids) {
                ItemStack itemStack = new ItemStack(ItemRegistry.FLUID_BOTTLE.get());
                items.add(FluidHelper.fillContainer(itemStack, fluid));
            }
        }
    }

    @Override
    public Component getName(ItemStack stack) {
        IFluidHandlerItem fluidHandlerItem =  stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
        FluidStack fluidStack = fluidHandlerItem.getFluidInTank(0);
        if (fluidStack.isEmpty()) return super.getName(stack);

        Component component = fluidStack.getDisplayName();
        return component.copy().append(new TranslatableComponent("item.watersource.fluid_bottle"));
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return new ItemStack(Items.GLASS_BOTTLE);
    }

}
