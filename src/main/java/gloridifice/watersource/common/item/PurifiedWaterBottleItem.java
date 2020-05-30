package gloridifice.watersource.common.item;

import gloridifice.watersource.common.capability.WaterLevelCapability;
import gloridifice.watersource.registry.CapabilityRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class PurifiedWaterBottleItem extends ModNormalItem{
    boolean canDrink = false;
    public PurifiedWaterBottleItem(String name) {
        super(name,new Properties().maxStackSize(8));
    }
    public int getUseDuration(ItemStack stack) {
        return canDrink ? 40 : 0;
    }
    public UseAction getUseAction(ItemStack stack) {
        return canDrink ? UseAction.DRINK : UseAction.NONE;
    }
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        stack.setCount(stack.getCount() - 1);
        if (stack.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        } else {
            if (entityLiving instanceof PlayerEntity && !((PlayerEntity)entityLiving).abilities.isCreativeMode) {
                ItemStack itemstack = new ItemStack(Items.GLASS_BOTTLE);
                PlayerEntity playerentity = (PlayerEntity)entityLiving;
                if (!playerentity.inventory.addItemStackToInventory(itemstack)) {
                    playerentity.dropItem(itemstack, false);
                }
            }
            return stack;
        }
    }

    public boolean canDrink(PlayerEntity playerIn){
        canDrink = false;
        playerIn.getCapability(WaterLevelCapability.PLAYER_WATER_LEVEL).ifPresent(data -> {
            canDrink = data.getWaterLevel() < 20;
        });
        return canDrink;
    }
    public SoundEvent getDrinkSound() {
        return SoundEvents.ENTITY_GENERIC_DRINK;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        playerIn.setActiveHand(handIn);
        return canDrink(playerIn) ? ActionResult.resultSuccess(playerIn.getHeldItem(handIn)) : ActionResult.resultPass(playerIn.getHeldItem(handIn));
    }
}
