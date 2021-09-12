package gloridifice.watersource.common.item;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.capability.WaterLevelCapability;
import gloridifice.watersource.helper.FluidHelper;
import gloridifice.watersource.registry.CapabilityRegistry;
import gloridifice.watersource.registry.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack.FLUID_NBT_KEY;

public class DrinkContainerItem extends ItemFluidContainer {
    boolean canDrink = false;

    public DrinkContainerItem(String name, Properties properties, int capacity) {
        super(properties, capacity);
        this.setRegistryName(name);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);
        IFluidHandler handler = itemInHand.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);

        if (handler.getFluidInTank(0).getAmount() >= this.capacity && canDrink(player,itemInHand)) return ItemUtils.startUsingInstantly(level, player, hand);
        HitResult hitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (hitresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemInHand);
        } else {
            if (hitresult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockpos = ((BlockHitResult) hitresult).getBlockPos();
                if (!level.mayInteract(player, blockpos)) {
                    return InteractionResultHolder.pass(itemInHand);
                }
                //右键水方块接水
                if (level.getFluidState(blockpos).is(FluidTags.WATER) && handler.getFluidInTank(0).getFluid() == Fluids.WATER) {
                    level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
                    level.gameEvent(player, GameEvent.FLUID_PICKUP, blockpos);
                    return InteractionResultHolder.sidedSuccess(FluidHelper.fillContainer(getContainerItem(new ItemStack(this)), Fluids.WATER), level.isClientSide());
                }
            }

            return InteractionResultHolder.pass(itemInHand);
        }
    }

    public int getUseDuration(ItemStack stack) {
        return canDrink ? 32 : 0;
    }

    public UseAnim getUseAnimation(ItemStack p_42997_) {
        return UseAnim.DRINK;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        IFluidHandler handler = itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
        if (handler.drain(250, IFluidHandler.FluidAction.SIMULATE).isEmpty()) {
            return getContainerItem(itemStack);
        } else {
            handler.drain(250, IFluidHandler.FluidAction.EXECUTE);
/*            if (livingEntity instanceof Player && !((Player) livingEntity).isCreative()) {
                ItemStack itemstack = new ItemStack(ItemRegistry.itemWoodenCup);
                Player playerentity = (Player) livingEntity;
                if (!playerentity.getInventory().add(itemstack)) {
                    playerentity.drop(itemstack, false);
                }
            }*/
            return itemStack;
        }
    }

    public boolean canDrink(Player playerIn, ItemStack stack) {
        canDrink = false;
        stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(data -> {
            canDrink = !data.getFluidInTank(0).isEmpty() && data.getFluidInTank(0).getAmount() == 250;
        });
        playerIn.getCapability(CapabilityRegistry.PLAYER_WATER_LEVEL).ifPresent(data -> {
            canDrink = canDrink && data.getWaterLevel() < 20;
        });
        return canDrink;
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
        if (tab == this.getCreativeTabs()) {
            Tag<Fluid> tag = FluidTags.getAllTags().getTag(new ResourceLocation(WaterSource.MODID, "drink"));
            if (tag == null) return;
            for (Fluid fluid : tag.getValues()) {
                ItemStack itemStack = new ItemStack(ItemRegistry.itemWoodenCupDrink);
                items.add(FluidHelper.fillContainer(itemStack, fluid));
            }
            items.add(new ItemStack(ItemRegistry.itemWoodenCup));
        }
    }

    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundTag nbt) {
        return new FluidHandlerItemStack(stack, capacity) {
            @Nonnull
            @Override
            @SuppressWarnings("deprecation")
            public ItemStack getContainer() {
                return getFluid().isEmpty() ? new ItemStack(ItemRegistry.itemWoodenCup) : this.container;
            }

            @Override
            public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
                for (Fluid fluid : FluidTags.getAllTags().getTag(new ResourceLocation(WaterSource.MODID, "drink")).getValues()) {
                    if (fluid == stack.getFluid()) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, components, tooltipFlag);
        if (itemStack.getTagElement(FLUID_NBT_KEY) != null) {
            FluidUtil.getFluidHandler(itemStack).ifPresent(f -> {
                TextComponent textComponent = new TextComponent(f.getFluidInTank(0).getDisplayName().getString());
                components.add(textComponent.append(String.format(": %d / %dmB", f.getFluidInTank(0).getAmount(), capacity)).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
                components.add(new TranslatableComponent("tooltip.watersource.drink_unit").append(" : 250mB").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
            });
        }
    }
    @Override
    public boolean hasCraftingRemainingItem() {
        return true;
    }
}
