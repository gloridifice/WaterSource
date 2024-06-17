package xyz.koiro.watersource.api.storage

import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtOps
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import java.util.Optional
import kotlin.jvm.optionals.getOrNull

class ItemStorageData(var stack: ItemStack = ItemStack.EMPTY) {
    companion object {
        val CODEC = RecordCodecBuilder.create { instance ->
            instance.group(
                ItemStack.CODEC.optionalFieldOf("stack")
                    .forGetter<ItemStorageData> { Optional.ofNullable(if (it.stack.isEmpty) null else it.stack) }
            ).apply(instance) {
                ItemStorageData(it.orElse(ItemStack.EMPTY))
            }
        }

        fun fromNbt(lookup: WrapperLookup, nbtCompound: NbtCompound): ItemStorageData? {
            val result = CODEC.parse(lookup.getOps(NbtOps.INSTANCE), nbtCompound)
            return result.result().getOrNull()
        }
    }

    fun clear() {
        stack = ItemStack.EMPTY
    }

    fun writeNbt(lookup: WrapperLookup, nbtCompound: NbtCompound) {
        CODEC.encode(this, lookup.getOps(NbtOps.INSTANCE), nbtCompound).getOrThrow()
    }
}