package gloridifice.watersource.common.item;

import gloridifice.watersource.registry.BlockRegistry;
import gloridifice.watersource.registry.CreativeModeTabRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.Random;

public class StrainerBlockItem extends ModNormalBlockItem {
    public StrainerBlockItem(Block block, int maxDamage) {
        super(block, new Properties().durability(maxDamage).setNoRepair().tab(CreativeModeTabRegistry.WATER_SOURCE_TAB));
    }

    public StrainerBlockItem(Block block) {
        super(block, new Properties().tab(CreativeModeTabRegistry.WATER_SOURCE_TAB));
    }

    public static ItemStack hurt(ItemStack stack, int damage) {
        if (!stack.isDamageableItem()) return stack;
        Map<Enchantment, Integer> list = EnchantmentHelper.getEnchantments(stack);
        Random random = new Random();
        ItemStack i = stack.copy();
        int randInt = 0;
        for (int a = 0; a < damage; a++) {
            if (list.containsKey(Enchantments.UNBREAKING)) {
                randInt = random.nextInt(list.get(Enchantments.UNBREAKING) + 1);
            }
            if (randInt == 0) {
                if (stack.getDamageValue() + 1 > stack.getMaxDamage()) return new ItemStack(BlockRegistry.ITEM_DIRTY_STRAINER);
            }
        }
        return i;
    }
}
