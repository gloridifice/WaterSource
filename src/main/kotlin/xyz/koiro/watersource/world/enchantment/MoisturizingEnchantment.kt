package xyz.koiro.watersource.world.enchantment

import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.registry.tag.ItemTags
import xyz.koiro.watersource.WSConfig

class MoisturizingEnchantment() :
    Enchantment(
        properties(
            ItemTags.ARMOR_ENCHANTABLE,
            ItemTags.CHEST_ARMOR_ENCHANTABLE,
            1,
            3,
            leveledCost(10, 20),
            leveledCost(60, 20),
            8,
            EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
        )
    ) {

    companion object {
        fun getPlayerMoisturizingLevelSum(player: PlayerEntity): Int{
            var levelSum = 0
            player.armorItems.forEach {
                val enchantments = EnchantmentHelper.getEnchantments(it)
                enchantments.enchantmentsMap.forEach { enchantment ->
                    if (enchantment.key.value() == ModEnchantments.MOISTURIZING)
                        levelSum += enchantment.intValue
                }
            }
            return levelSum
        }

        fun getPlayerMoisturizingRatio(player: PlayerEntity): Float {
            return WSConfig.getMoisturizingRatio(getPlayerMoisturizingLevelSum(player))
        }
    }
}