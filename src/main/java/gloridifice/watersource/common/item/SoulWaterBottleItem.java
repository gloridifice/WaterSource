package gloridifice.watersource.common.item;

import gloridifice.watersource.registry.EffectRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SoulWaterBottleItem extends WaterBottleItem {
    public SoulWaterBottleItem(String name) {
        super(name);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        livingEntity.addEffect(new MobEffectInstance(EffectRegistry.ACCOMPANYING_SOUL, 8000, 0));
        return super.finishUsingItem(stack, level, livingEntity);
    }


    @Override
    public boolean canDrink(Player playerIn) {
        canDrink = true;
        return true;
    }
}
