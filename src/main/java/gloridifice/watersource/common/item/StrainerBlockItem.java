package gloridifice.watersource.common.item;

import gloridifice.watersource.common.block.entity.StrainerBlockEntity;
import gloridifice.watersource.registry.BlockRegistry;
import gloridifice.watersource.registry.CreativeModeTabRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Random;

public class StrainerBlockItem extends ModNormalBlockItem {
    public StrainerBlockItem(Block block, int maxDamage) {
        super(block, new Properties().durability(maxDamage).setNoRepair().tab(CreativeModeTabRegistry.WATER_SOURCE_TAB));
    }

    public StrainerBlockItem(Block block) {
        super(block, new Properties().tab(CreativeModeTabRegistry.WATER_SOURCE_TAB));
    }

    @Override
    protected boolean canPlace(BlockPlaceContext p_40611_, BlockState p_40612_) {
        return false;
    }

    public static ItemStack hurt(ItemStack stack, int damage) {
        if (!stack.isDamageableItem()) return stack;
        ItemStack copy = stack.copy();
        Map<Enchantment, Integer> list = EnchantmentHelper.getEnchantments(copy);
        Random random = new Random();
        int randInt = 0;
        for (int a = 0; a < damage; a++) {
            if (list.containsKey(Enchantments.UNBREAKING)) {
                randInt = random.nextInt(list.get(Enchantments.UNBREAKING) + 1);
            }
            if (randInt == 0) {
                if (copy.getDamageValue() + 1 >= copy.getMaxDamage())
                {
                    copy = new ItemStack(BlockRegistry.ITEM_DIRTY_STRAINER.get());
                }
                else copy.setDamageValue(copy.getDamageValue() + 1);
            }
        }
        return copy;
    }
}
