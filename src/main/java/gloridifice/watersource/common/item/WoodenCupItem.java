package gloridifice.watersource.common.item;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.capability.WaterLevelCapability;
import gloridifice.watersource.helper.FluidHelper;
import gloridifice.watersource.registry.ItemRegistry;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack.FLUID_NBT_KEY;

public class WoodenCupItem extends ItemFluidContainer {
    boolean canDrink = false;
    boolean canFill = false;
    public WoodenCupItem(String name, Properties properties, int capacity) {
        super(properties, capacity);
        this.setRegistryName(name);
    }

    public int getUseDuration(ItemStack stack) {
        return canDrink ? 32 : 0;
    }
    public UseAction getUseAction(ItemStack stack) {
        return canDrink ? UseAction.DRINK : UseAction.NONE;
    }
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        stack.setCount(stack.getCount() - 1);
        if (stack.isEmpty()) {
            return new ItemStack(ItemRegistry.itemWoodenCup);
        } else {
            if (entityLiving instanceof PlayerEntity && !((PlayerEntity)entityLiving).abilities.isCreativeMode) {
                ItemStack itemstack = new ItemStack(ItemRegistry.itemWoodenCup);
                PlayerEntity playerentity = (PlayerEntity)entityLiving;
                if (!playerentity.inventory.addItemStackToInventory(itemstack)) {
                    playerentity.dropItem(itemstack, false);
                }
            }
            return stack;
        }
    }

    public boolean canDrink(PlayerEntity playerIn,ItemStack stack){
        canDrink = false;
        stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(data -> {
            canDrink = !data.getFluidInTank(0).isEmpty() && data.getFluidInTank(0).getAmount() == 250;
        });
        playerIn.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
            canDrink = canDrink  && data.getWaterLevel() < 20;
        });
        return canDrink;
    }

    public SoundEvent getDrinkSound() {
        return SoundEvents.ENTITY_GENERIC_DRINK;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        canFill = false;
        RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
            playerIn.setActiveHand(handIn);
            return canDrink(playerIn,playerIn.getHeldItem(handIn)) ? ActionResult.resultSuccess(playerIn.getHeldItem(handIn)) : ActionResult.resultPass(playerIn.getHeldItem(handIn));
        } else {
            if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
                BlockPos blockpos = ((BlockRayTraceResult)raytraceresult).getPos();
                if (!worldIn.isBlockModifiable(playerIn, blockpos)) {
                    playerIn.setActiveHand(handIn);
                    return canDrink(playerIn,playerIn.getHeldItem(handIn)) ? ActionResult.resultSuccess(playerIn.getHeldItem(handIn)) : ActionResult.resultPass(playerIn.getHeldItem(handIn));
                }
                if (worldIn.getFluidState(blockpos).isTagged(FluidTags.WATER)) {
                    itemstack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(data ->{
                        if (data.getFluidInTank(0).isEmpty() || data.getFluidInTank(0).getFluid() == Fluids.WATER){
                            canFill = true;
                        }
                    });
                    if (canFill){
                        worldIn.playSound(playerIn, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                        return ActionResult.resultSuccess(this.turnCupIntoItem(itemstack, playerIn, FluidHelper.fillContainer(new ItemStack(ItemRegistry.itemWoodenCupDrink),Fluids.WATER)));
                    }
                }
            }
            return ActionResult.resultPass(itemstack);
        }
    }
    protected ItemStack turnCupIntoItem(ItemStack originalStack, PlayerEntity player, ItemStack stack) {
        originalStack.shrink(1);
        player.addStat(Stats.ITEM_USED.get(this));
        if (originalStack.isEmpty()) {
            return stack;
        } else {
            if (!player.inventory.addItemStackToInventory(stack)) {
                player.dropItem(stack, false);
            }
            return originalStack;
        }
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (group == this.group)
        {
            ITag<Fluid> tag = FluidTags.getCollection().get(new ResourceLocation(WaterSource.MODID,"drink"));
            if (tag == null) return;
            for (Fluid fluid : tag.getAllElements())
            {
                ItemStack itemStack = new ItemStack(ItemRegistry.itemWoodenCupDrink);
                items.add(FluidHelper.fillContainer(itemStack,fluid));
            }
            items.add(new ItemStack(ItemRegistry.itemWoodenCup));
        }
    }
    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundNBT nbt)
    {
        return new FluidHandlerItemStack(stack, capacity)
        {
            @Nonnull
            @Override
            @SuppressWarnings("deprecation")
            public ItemStack getContainer()
            {
                return getFluid().isEmpty() ? new ItemStack(ItemRegistry.itemWoodenCup) : this.container;
            }

            @Override
            public boolean isFluidValid(int tank, @Nonnull FluidStack stack)
            {
                for (Fluid fluid : FluidTags.getCollection().get(new ResourceLocation(WaterSource.MODID,"drink")).getAllElements()){
                    if (fluid == stack.getFluid()){
                        return true;
                    }
                }
                return false;
            }
        };
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (stack.getChildTag(FLUID_NBT_KEY) != null)
        {
            FluidUtil.getFluidHandler(stack).ifPresent(f ->{
                StringTextComponent stringTextComponent = new StringTextComponent(f.getFluidInTank(0).getDisplayName().getString());
                tooltip.add(stringTextComponent.appendString(String.format(": %d / %dmB", f.getFluidInTank(0).getAmount(), capacity)).mergeStyle(TextFormatting.GRAY));
                tooltip.add(new TranslationTextComponent("tooltip.watersource.drink_unit").appendString(" : 250mB").mergeStyle(TextFormatting.GRAY));
            });
        }
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return new ItemStack(ItemRegistry.itemWoodenCup);
    }
}
