package gloridifice.watersource.common.item;

import gloridifice.watersource.registry.BlockRegistry;
import gloridifice.watersource.registry.GroupRegistry;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;

import java.util.Map;
import java.util.Random;

public class StrainerBlockItem extends ModNormalBlockItem {
    public StrainerBlockItem(Block block, int maxDamage) {
        super(block, new Properties().maxDamage(maxDamage).setNoRepair().group(GroupRegistry.waterSourceGroup));
    }


    public static ItemStack damageItem(ItemStack stack,int damage) {
        Map<Enchantment,Integer> list = EnchantmentHelper.getEnchantments(stack);
        Random random = new Random();
        ItemStack i = stack.copy();
        int randInt = 0;
        //todo test
        for (int a = 0; a < damage; a++){
            if (list.get(Enchantments.UNBREAKING) != null){
                randInt = random.nextInt(list.get(Enchantments.UNBREAKING).intValue() + 1);
            }
            if (randInt == 0){
                if (stack.getDamage() + 1 <= stack.getMaxDamage()){
                    i.setDamage(stack.getDamage() + 1);
                }else {
                    return new ItemStack(BlockRegistry.ITEM_DIRTY_STRAINER);
                }
            }
        }
        return i;
    }
}
