package xyz.koiro.watersource.world.item

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import xyz.koiro.watersource.WSConfig
import kotlin.math.ceil

class Strainer(settings: Settings?) : Item(settings) {
    fun calCostDamage(strainerStack: ItemStack, volume: Long): Int {
        return ceil(volume.toDouble() / WSConfig.UNIT_DRINK_VOLUME.toDouble()).toInt()
    }

    fun useStrainer(strainerStack: ItemStack, volume: Long): ItemStack {
        val dmg = calCostDamage(strainerStack, volume)
        val remainedStrainer = if (strainerStack.damage + dmg >= strainerStack.maxDamage) {
            ItemStack(ModItems.WASTE_STRAINER)
        } else {
            val strainerCopy = strainerStack.copy()
            strainerCopy.damage += dmg
            strainerCopy
        }
        return remainedStrainer
    }
}