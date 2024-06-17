package xyz.koiro.watersource.world.item

import net.minecraft.client.item.TooltipType
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.world.World
import xyz.koiro.watersource.WSConfig
import kotlin.math.ceil

class Strainer(settings: Settings?) : Item(settings) {
    fun calCostDamage(strainerStack: ItemStack, volume: Long): Int {
        return ceil(volume.toDouble() / WSConfig.UNIT_DRINK_VOLUME.toDouble()).toInt()
    }

    fun getUsedStrainer(strainerStack: ItemStack, cost: Int): ItemStack {
        val remainedStrainer = if (strainerStack.damage + cost >= strainerStack.maxDamage) {
            ItemStack(ModItems.WASTE_STRAINER)
        } else {
            val strainerCopy = strainerStack.copy()
            strainerCopy.damage += cost
            strainerCopy
        }
        return remainedStrainer
    }

    override fun appendTooltip(
        stack: ItemStack?,
        context: TooltipContext?,
        tooltip: MutableList<Text>?,
        type: TooltipType?
    ) {
        super.appendTooltip(stack, context, tooltip, type)
        if (stack != null && tooltip != null)
        tooltip.add(
            Text.of("${stack.maxDamage - stack.damage}/${stack.maxDamage}").copy()
                .styled { it.withColor(Formatting.GRAY) })
    }
}