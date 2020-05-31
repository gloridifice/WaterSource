package gloridifice.watersource.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class SoulWaterBottle extends PurifiedWaterBottleItem{
    public SoulWaterBottle(String name) {
        super(name);
    }
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        //TODO
        return new ItemStack(Items.GLASS_BOTTLE);
    }

}
