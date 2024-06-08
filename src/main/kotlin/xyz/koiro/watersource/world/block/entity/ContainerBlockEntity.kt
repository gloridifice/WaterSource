package xyz.koiro.watersource.world.block.entity

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.fluid.Fluids
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.math.BlockPos
import xyz.koiro.watersource.api.storage.FluidStorageData

open class ContainerBlockEntity(type: BlockEntityType<*>?, pos: BlockPos?, state: BlockState?, capacity: Long) :
    BlockEntity(type, pos, state) {
    var fluidStorageData: FluidStorageData = FluidStorageData(Fluids.EMPTY, 0, capacity)

    override fun writeNbt(nbt: NbtCompound?) {
        super.writeNbt(nbt)
        nbt?.put("FluidStorageData", fluidStorageData.toNbt())
    }

    override fun readNbt(nbt: NbtCompound?) {
        super.readNbt(nbt)
        nbt?.getCompound("FluidStorageData")?.let { nbtCompound ->
            FluidStorageData.fromNbt(nbtCompound)?.let {
                fluidStorageData = it
            }
        }
    }
}