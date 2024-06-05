package xyz.koiro.watersource.api.fluidData

import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.Registries
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import xyz.koiro.watersource.datagen.ModLanguages
import xyz.koiro.watersource.identifier

class FluidStorageData(
    var fluid: Fluid,
    amount: Long,
    var capacity: Long
) {
    var amount: Long = amount
        set(value) {
            field = value.coerceIn(0L, capacity)
        }

    fun toNbt(): NbtCompound {
        val ret = NbtCompound()
        ret.putString("Fluid", fluid.identifier().toString())
        ret.putLong("Amount", amount)
        ret.putLong("Capacity", capacity)
        return ret
    }

    fun isBlank(): Boolean {
        return amount == 0L || fluid == Fluids.EMPTY
    }

    fun restCapacity() = this.capacity - this.amount

    fun canInsert(amount: Long, fluid: Fluid, canOverflow: Boolean): Boolean{
        val canInsert = isBlank() || run {
            this.fluid == fluid && (canOverflow || amount <= restCapacity())
        }
        return canInsert
    }

    fun insert(amount: Long, fluid: Fluid, canOverflow: Boolean): Boolean{
        if (!canInsert(amount, fluid, canOverflow)) return false
        this.amount += amount
        this.fluid = fluid
        onModified()
        return true
    }

    fun extract(amount: Long){
        if (amount >= this.amount) {
            fluid = Fluids.EMPTY
            this.amount = 0
        } else {
            this.amount -= amount
        }
    }

    fun transferTo(other: FluidStorageData, amount: Long, canOverflow: Boolean): Boolean{
        if (this.amount < amount) return false
        if (!other.canInsert(amount, other.fluid, canOverflow)) return false
        if (!other.insert(amount, this.fluid, canOverflow)) return false
        this.extract(amount)
        return true
    }

    fun clear(){
        extract(Long.MAX_VALUE)
    }

    fun onModified(){

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

        fun getEmptyText(capacity: Long): MutableText {
            return Text.translatable(ModLanguages.INFO_CAPCITY_KYE).append(Text.of(": $capacity mB"))
        }
    }

    fun getDisplayText(): MutableText {
        val name = this.fluid.defaultState.blockState.block.name
        return if (this.isBlank()) getEmptyText(this.capacity) else
            name.append(Text.of(": ${amount}/${capacity} mB"))
    }
}