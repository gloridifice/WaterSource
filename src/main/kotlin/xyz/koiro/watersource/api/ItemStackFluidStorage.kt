package xyz.koiro.watersource.api

import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import xyz.koiro.watersource.identifier
import xyz.koiro.watersource.world.item.FluidContainer

data class FluidStorageData(
    val fluid: Fluid,
    val amount: Long,
    val capacity: Long
) {
    fun toNbt(): NbtCompound {
        val ret = NbtCompound()
        ret.putString("Fluid", fluid.identifier().toString())
        ret.putLong("Amount", amount)
        ret.putLong("Capacity", capacity)
        return ret
    }

    fun isBlank(): Boolean{
        return amount == 0L || fluid == Fluids.EMPTY
    }
    companion object {
        fun fromNbt(nbtCompound: NbtCompound?): FluidStorageData? {
            if (nbtCompound == null) return null
            val valid =
                nbtCompound.contains("Fluid") && nbtCompound.contains("Amount") && nbtCompound.contains("Capacity")
            if (valid) {
                val fluid = Registries.FLUID.get(Identifier.tryParse(nbtCompound.getString("Fluid")))
                val amount = nbtCompound.getLong("Amount")
                val capacity = nbtCompound.getLong("Capacity")
                return FluidStorageData(fluid, amount, capacity)
            }
            return null
        }
    }
}

const val FLUID_STORAGE_KEY = "WaterSourceFluidStorage"

//todo test
fun ItemStack.insertFluid(fluid: Fluid, amountGetter: (FluidStorageData) -> Long): Long? {
    var flag: Long? = null
    this.modifyFluidStorage { itemStack, data ->
        val item = this.item as FluidContainer
        val amount = amountGetter(data)
        val canInsert = item.canInsert(this, fluid, amount, data)
        if (canInsert) {
            val newAmount = (data.amount + amount).coerceIn(0L, data.capacity)
            flag = newAmount - data.amount
            data.copy(amount = newAmount, fluid = fluid)
        } else data
    }
    return flag
}

//todo test
fun ItemStack.extractFluid(amount: Long): Long? {
    var flag: Long? = null
    this.modifyFluidStorage { itemStack, fluidStorageData ->
        val newAmount = (fluidStorageData.amount - amount).coerceIn(0L, fluidStorageData.capacity)
        val extracted = fluidStorageData.amount - newAmount
        flag = extracted
        fluidStorageData.copy(amount = newAmount)
    }
    return flag
}

/** Create new data if fluid storage nbt data not exist or is not valid.
 *  @return `null` if item isn't FluidContainer, fluid storage data if nbt data is valid.
 */
fun ItemStack.getOrCreateFluidStorageData(): FluidStorageData? {
    val item = this.item
    if (item is FluidContainer) {
        val data = FluidStorageData.fromNbt(this.getSubNbt(FLUID_STORAGE_KEY))
        return if (data != null) {
            data
        } else {
            // Init
            val ret = FluidStorageData(Fluids.EMPTY, 0, item.capacity)
            this.setSubNbt(FLUID_STORAGE_KEY, ret.toNbt())
            ret
        }
    }
    return null
}

/**
 * Modify fluid storage and apply for nbt if item is `FluidContainer`
 * */
fun ItemStack.modifyFluidStorage(action: (ItemStack, FluidStorageData) -> FluidStorageData) {
    this.getOrCreateFluidStorageData()?.let {
        val modified = action(this, it)
        this.setSubNbt(FLUID_STORAGE_KEY, modified.toNbt())
    }
}