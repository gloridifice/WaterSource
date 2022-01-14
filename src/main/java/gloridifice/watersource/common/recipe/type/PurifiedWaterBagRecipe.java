package gloridifice.watersource.common.recipe.type;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.item.StrainerBlockItem;
import gloridifice.watersource.common.item.DurableDrinkContainerItem;
import gloridifice.watersource.helper.FluidHelper;
import gloridifice.watersource.registry.BlockRegistry;
import gloridifice.watersource.registry.FluidRegistry;
import gloridifice.watersource.registry.ItemRegistry;
import gloridifice.watersource.registry.RecipeSerializersRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.ArrayList;
import java.util.List;

public class PurifiedWaterBagRecipe extends CustomRecipe {
    protected Fluid fluid;
    protected ResourceLocation strainerTag;

    public PurifiedWaterBagRecipe(ResourceLocation idIn) {
        super(idIn);
        this.fluid = FluidRegistry.PURIFIED_WATER.get();
        this.strainerTag = new ResourceLocation(WaterSource.MODID, "purification_strainers");
    }

    @Override
    public ItemStack getResultItem() {
        for (int j = 0; j < getIngredients().size(); ++j) {
            Ingredient ingredient = getIngredients().get(j);
            for (ItemStack stack : ingredient.getItems()) {
                if (stack.getItem() instanceof DurableDrinkContainerItem && FluidHelper.isItemStackFluidEqual(stack, FluidHelper.fillContainer(new ItemStack(ItemRegistry.WOODEN_CUP_DRINK), Fluids.WATER))) {
                    ItemStack i = stack.copy();
                    FluidUtil.getFluidHandler(i).ifPresent(data -> {
                        int n = data.getFluidInTank(0).getAmount();
                        data.drain(n, IFluidHandler.FluidAction.EXECUTE);
                        data.fill(new FluidStack(this.fluid, n), IFluidHandler.FluidAction.EXECUTE);
                    });
                    return i;
                }
            }
        }
        return getRecipeOutput();
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer container) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(container.getContainerSize(), ItemStack.EMPTY);
        int n = 0;
        for (int j = 0; j < container.getContainerSize(); ++j) {
            ItemStack itemstack = container.getItem(j);
            if (itemstack.getItem() instanceof DurableDrinkContainerItem && FluidHelper.isItemStackFluidEqual(itemstack, FluidHelper.fillContainer(new ItemStack(ItemRegistry.WOODEN_CUP_DRINK), Fluids.WATER))) {
                n = FluidUtil.getFluidHandler(itemstack).map(data -> data.getFluidInTank(0).getAmount()).orElse(0);
            }
        }
        for (int j = 0; j < container.getContainerSize(); ++j) {
            ItemStack itemstack = container.getItem(j);
            Tag<Item> tag = ItemTags.getAllTags().getTag(strainerTag);
            if (!itemstack.isEmpty() && tag != null && tag.contains(itemstack.getItem())) {
                ItemStack copy = itemstack.copy();
                StrainerBlockItem.hurt(copy, n / 250);
                nonnulllist.set(j, copy);
            }
        }
        return nonnulllist;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;//RecipeSerializersRegistry.CRAFTING_PURIFIED_WATER_BAG.get();
    }


    public ItemStack getRecipeOutput() {
        return FluidHelper.fillContainer(new ItemStack(ItemRegistry.LEATHER_WATER_BAG), this.fluid);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        List<ItemStack> list = new ArrayList<>();
        boolean hasStrainer = false, hasPotion = false;
        ItemStack strainer = ItemStack.EMPTY;
        int potion = 0;
        for (int j = 0; j < container.getContainerSize(); ++j) {
            ItemStack itemstack = container.getItem(j);
            if (isStrainer(strainer)) {
                list.add(itemstack);
                strainer = itemstack.copy();
                hasStrainer = true;
            }
            if (itemstack.getItem() instanceof DurableDrinkContainerItem && FluidHelper.isItemStackFluidEqual(itemstack, FluidHelper.fillContainer(new ItemStack(ItemRegistry.WOODEN_CUP_DRINK), Fluids.WATER))) {
                list.add(itemstack);
                potion = FluidUtil.getFluidHandler(itemstack).map(date -> date.getFluidInTank(0).getAmount()).orElse(0);
                hasPotion = true;
            }
        }
        return hasPotion && hasStrainer && list.size() == 2 && potion != 0 && (strainer.getMaxDamage() - strainer.getDamageValue()) >= potion / 250;
    }

    @Override
    public ItemStack assemble(CraftingContainer container) {
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            ItemStack strainer;
            if (isStrainer(stack)) {
                strainer = stack;
            }

        }
        return new ItemStack(BlockRegistry.ITEM_PAPER_STRAINER);
    }

    public boolean isStrainer(ItemStack stack) {
        Tag<Item> tag = ItemTags.bind(strainerTag.toString());
        return tag.contains(stack.getItem());
    }

    public boolean canCraftInDimensions(int w, int l) {
        return w * l >= 2;
    }


    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(Ingredient.of(new ItemStack(ItemRegistry.LEATHER_WATER_BAG)));
        list.add(Ingredient.of(new ItemStack(BlockRegistry.ITEM_PAPER_STRAINER)));
        list.add(Ingredient.of(new ItemStack(Items.APPLE)));
        return list;
    }

}
