package xyz.koiro.watersource.api.storage

import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemStack
import xyz.koiro.watersource.world.item.FluidContainerItem

const val FLUID_STORAGE_KEY = "WaterSourceFluidStorage"

fun ItemStack.insertFluid(fluid: Fluid, amountGetter: (FluidStorageData) -> Long): Long? {
    var flag: Long? = null
    this.modifyFluidStorage { itemStack, data ->
        val item = this.item as FluidContainerItem
        val amount = amountGetter(data)
        val canInsert = item.canInsert(this, fluid, amount, data)
        if (canInsert) {
            data.insert(amount, fluid, true)
        }
        flag = data.amount
    }
    return flag
}

fun ItemStack.extractFluid(amount: Long): Long? {
    var flag: Long? = null
    this.modifyFluidStorage { itemStack, fluidStorageData ->
        fluidStorageData.extract(amount)
        flag = fluidStorageData.amount
    }
    return flag
}

/** Create new data if fluid storage nbt data not exist or is not valid.
 *  @return `null` if item isn't FluidContainer, fluid storage data if nbt data is valid.
 */
fun ItemStack.getOrCreateFluidStorageData(): FluidStorageData? {
    val item = this.item
    if (item is FluidContainerItem) {
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
fun ItemStack.modifyFluidStorage(action: (ItemStack, FluidStorageData) -> Unit) {
    this.getOrCreateFluidStorageData()?.let {
        action(this, it)
        this.setSubNbt(FLUID_STORAGE_KEY, it.toNbt())
    }
}

fun ItemStack.setFluidStorage(data: FluidStorageData): Boolean {
    val ret = this.getOrCreateFluidStorageData() != null
    if (ret)
        this.setSubNbt(FLUID_STORAGE_KEY, data.toNbt())
    return ret
}
