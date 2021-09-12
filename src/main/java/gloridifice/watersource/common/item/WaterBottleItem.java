package gloridifice.watersource.common.item;

import gloridifice.watersource.common.capability.WaterLevelCapability;
import gloridifice.watersource.registry.CapabilityRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class WaterBottleItem extends ModNormalItem {
    boolean canDrink = false;
    public WaterBottleItem(String name) {
        super(name, new Properties().stacksTo(16));
    }
    public int getUseDuration(ItemStack stack) {
        return canDrink ? 32 : 0;
    }
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        Player player = livingEntity instanceof Player ? (Player)livingEntity : null;
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, stack);
        }

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        if (player == null || !player.getAbilities().instabuild) {
            if (stack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            if (player != null) {
                player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        level.gameEvent(livingEntity, GameEvent.DRINKING_FINISH, livingEntity.eyeBlockPosition());
        return stack;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemStack) {
        return canDrink ? UseAnim.DRINK : UseAnim.NONE;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (canDrink(player)) return ItemUtils.startUsingInstantly(level, player, interactionHand);
        return InteractionResultHolder.fail(player.getItemInHand(interactionHand));
    }

    public boolean canDrink(Player playerIn){
        canDrink = false;
        playerIn.getCapability(CapabilityRegistry.PLAYER_WATER_LEVEL).ifPresent(data -> {
            canDrink = data.getWaterLevel() < 20;
        });
        return canDrink;
    }}
