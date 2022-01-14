package gloridifice.watersource.common.recipe.type;

import gloridifice.watersource.common.item.StrainerBlockItem;
import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class StrainerFilterRecipe extends CustomRecipe {
    public Tag<Item> strainerTag;
    public Fluid inPutFluid;
    public Fluid outPutFluid;
    public Item containerItem;

    public StrainerFilterRecipe(ResourceLocation name, String strainerTag, Fluid inPutFluid, Fluid outPutFluid, Item containerItem) {
        super(name);
        this.strainerTag = ItemTags.bind(strainerTag);
        this.inPutFluid = inPutFluid;
        this.outPutFluid = outPutFluid;
        this.containerItem = containerItem;
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        int matches = 0;
        ItemStack containerItem = ItemStack.EMPTY;
        ItemStack strainer = ItemStack.EMPTY;
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (stack.is(this.containerItem)) {
                containerItem = stack.copy();
                matches++;
            }
            if (stack.is(this.strainerTag)) {
                strainer = stack.copy();
                matches++;
            }
        }
        boolean f = !strainer.isEmpty() && !containerItem.isEmpty() && matches == 2;
        if (f) {
            int consume;
            int possess;
            Fluid fluid;
            if (!containerItem.is(Items.POTION)) {
                IFluidHandlerItem fluidHandlerItem = containerItem.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
                consume = fluidHandlerItem.getFluidInTank(0).getAmount() / 250;
                fluid = fluidHandlerItem.getFluidInTank(0).getFluid();
            } else {
                consume = 1;
                if (PotionUtils.getPotion(containerItem) == Potions.WATER){
                    fluid = Fluids.WATER;
                } else return false;
            }
            possess = strainer.getMaxDamage() - strainer.getDamageValue();
            boolean a = possess >= consume && fluid.isSame(this.inPutFluid);
            return a;
        }
        return false;
    }

    @Override
    public ItemStack assemble(CraftingContainer container) {
        ItemStack containerItem = ItemStack.EMPTY;
        ItemStack strainer = ItemStack.EMPTY;
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (stack.is(this.containerItem)) {
                containerItem = stack.copy();

            }
            if (stack.is(this.strainerTag)) {
                strainer = stack.copy();

            }
        }
        boolean f = !strainer.isEmpty() && !containerItem.isEmpty();
        if (f) {
            IFluidHandlerItem fluidHandlerItem = containerItem.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);

            ItemStack result = containerItem.copy();
            IFluidHandlerItem resultFluidHandlerItem = result.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
            resultFluidHandlerItem.drain(resultFluidHandlerItem.getTankCapacity(0), IFluidHandler.FluidAction.EXECUTE);
            resultFluidHandlerItem.fill(new FluidStack(outPutFluid, fluidHandlerItem.getFluidInTank(0).getAmount()), IFluidHandler.FluidAction.EXECUTE);

            return result;
        }
        return new ItemStack(this.containerItem);
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer container) {
        NonNullList<ItemStack> nonnulllist = super.getRemainingItems(container);
        int strainerPos = 0;
        ItemStack containerItem = ItemStack.EMPTY;
        ItemStack strainer = ItemStack.EMPTY;
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (stack.is(this.containerItem)) {
                containerItem = stack.copy();
            }
            if (stack.is(this.strainerTag)) {
                strainer = stack.copy();
                strainerPos = i;
            }
        }
        boolean f = !strainer.isEmpty() && !containerItem.isEmpty();
        if (f) {
            ItemStack strainer1 = strainer.copy();
            IFluidHandlerItem fluidHandlerItem = containerItem.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
            int consume = fluidHandlerItem.getFluidInTank(0).getAmount() / 250;
            nonnulllist.set(strainerPos, StrainerBlockItem.hurt(strainer1, consume));
        }
        return nonnulllist;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.STRAINER_FILTER_RECIPE_SERIALIZER.get();
    }
}
