package xyz.koiro.watersource.world.item

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.UseAction
import net.minecraft.world.World
import xyz.koiro.watersource.simpleStack

class DrinkOnceItem(settings: Settings, val emptyItem: Item, useTime: Int = 32) :
    DrinkableContainer(settings, 250L, useTime, 1) {
    override fun finishUsing(stack: ItemStack?, world: World?, user: LivingEntity?): ItemStack {
        return emptyItem.simpleStack()
    }
}