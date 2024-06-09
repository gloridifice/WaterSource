package xyz.koiro.watersource.world.enchantment

import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.player.PlayerEntity
import xyz.koiro.watersource.WSConfig
import xyz.koiro.watersource.WaterSource

class MoisturizingEnchantment() :
    Enchantment(Rarity.UNCOMMON, EnchantmentTarget.ARMOR, arrayOf(EquipmentSlot.FEET, EquipmentSlot.HEAD, EquipmentSlot.LEGS, EquipmentSlot.CHEST)) {
    override fun getMaxLevel(): Int {
        return 3
    }
    override fun getMinPower(level: Int): Int {
        return 1
    }

    companion object {
        fun getPlayerMoisturizingLevelSum(player: PlayerEntity): Int{
            var levelSum = 0
            player.armorItems.forEach {
                val enchantments = EnchantmentHelper.fromNbt(it.enchantments)
                enchantments.forEach { enchantment, level ->
                    if (enchantment == ModEnchantments.MOISTURIZING)
                        levelSum += level
                }
            }
            return levelSum
        }

        fun getPlayerMoisturizingRatio(player: PlayerEntity): Float {
            return WSConfig.getMoisturizingRatio(getPlayerMoisturizingLevelSum(player))
        }
    }
}