package gloridifice.watersource.common.item;

import gloridifice.watersource.registry.ItemRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class CoconutMilkBottleItem extends ModFoodItem{
    public CoconutMilkBottleItem( FoodProperties food) {
        super( new Properties().stacksTo(16), food);
    }


    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (!level.isClientSide) livingEntity.curePotionEffects(stack); // FORGE - move up so stack.shrink does not turn stack into air
        if (livingEntity instanceof Player player && !((Player)livingEntity).getAbilities().instabuild) {
            stack.shrink(1);
            if (!stack.isEmpty()){
                if (!player.getInventory().add(getContainerItem(stack))) {
                    player.drop(getContainerItem(stack), false);
                }
            }
        }

        return stack.isEmpty() ? new ItemStack(Items.GLASS_BOTTLE) : stack;
    }

    public int getUseDuration(ItemStack p_42933_) {
        return 32;
    }

    public UseAnim getUseAnimation(ItemStack p_42931_) {
        return UseAnim.DRINK;
    }

    @Override
    public SoundEvent getEatingSound() {
        return super.getDrinkingSound();
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return new ItemStack(Items.GLASS_BOTTLE);
    }
}
