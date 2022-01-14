package gloridifice.watersource.common.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class MoisturizingEnchantment extends Enchantment {
    public MoisturizingEnchantment(Rarity rarity, EquipmentSlot[] equipmentSlots) {
        super(rarity, EnchantmentCategory.ARMOR, equipmentSlots);
    }

    public int getMinCost(int p_45284_) {
        return 1;
    }

    public int getMaxCost(int p_45288_) {
        return this.getMinCost(p_45288_) + 40;
    }

    public int getMaxLevel() {
        return 1;
    }

}
