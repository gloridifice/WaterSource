package xyz.koiro.watersource.world.block.entity

import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleStackStorage
import net.minecraft.block.BlockState
import net.minecraft.fluid.Fluid
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.math.BlockPos

class FilterBlockEntity(pos: BlockPos?, state: BlockState?, var capacity: Long, var isUp: Boolean = false) :
    ContainerBlockEntity(ModBlockEntities.FILTER, pos, state, capacity) {

    class RenderCtx(
        var heightRatio: Float = 0f,
        var targetHeightRatio: Float = 0f,
        var fluidCache: Fluid? = null,
    )
    val renderCtx = RenderCtx()
    var strainer: ItemStack = ItemStack.EMPTY

    override fun writeNbt(nbt: NbtCompound?) {
        super.writeNbt(nbt)
        if (nbt == null) return
        nbt.putBoolean("isUp", isUp)
        val strainerNbt = NbtCompound()
        strainer.writeNbt(strainerNbt)
        nbt.put("strainer", strainerNbt)
    }

    override fun readNbt(nbt: NbtCompound?) {
        super.readNbt(nbt)
        if (nbt == null) return
        isUp = nbt.getBoolean("isUp") ?: false
        val strainerNbt = nbt.getCompound("strainer")
        strainerNbt?.let { strainer = ItemStack.fromNbt(it) }
    }
}