package xyz.koiro.watersource.api

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand

fun PlayerEntity.setStackInHandOrInsertIntoInventory(hand: Hand, itemStack: ItemStack){
    if (this.getStackInHand(hand).isEmpty) {
        setStackInHand(hand, itemStack)
    } else {
        inventory.insertStack(itemStack)
    }
}