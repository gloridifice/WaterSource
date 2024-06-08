package xyz.koiro.watersource.api.storage

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound

class ItemStorageData(var stack: ItemStack = ItemStack.EMPTY) {
    fun clear(){
        stack = ItemStack.EMPTY
    }

    fun writeNbt(nbtCompound: NbtCompound){
        stack.writeNbt(nbtCompound)
    }

    fun readNbt(nbtCompound: NbtCompound){
        stack = ItemStack.fromNbt(nbtCompound)
    }
}