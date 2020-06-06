package gloridifice.watersource.common.item;

import gloridifice.watersource.registry.EffectRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class SoulWaterBottle extends PurifiedWaterBottleItem{
    public SoulWaterBottle(String name) {
        super(name);
    }
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        entityLiving.addPotionEffect(new EffectInstance(EffectRegistry.ACCOMPANYING_SOUL,8000,0));
        return new ItemStack(Items.GLASS_BOTTLE);
    }

    @Override
    public boolean canDrink(PlayerEntity playerIn) {
        canDrink = true;
        return true;
    }
}
