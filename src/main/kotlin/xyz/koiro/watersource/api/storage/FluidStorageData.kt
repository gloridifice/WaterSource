package xyz.koiro.watersource.api.storage

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.registry.Registries
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import xyz.koiro.watersource.datagen.ModLanguages
import xyz.koiro.watersource.api.identifier

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

    fun isFull() : Boolean {
        return amount == capacity
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

    fun transferTo(other: FluidStorageData, amount: Long, canOverflow: Boolean): Pair<FluidStorageData, FluidStorageData>?{
        if (this.amount < amount) return null
        if (!other.canInsert(amount, other.fluid, canOverflow)) return null
        if (!other.insert(amount, this.fluid, canOverflow)) return null
        this.extract(amount)
        return Pair(this, other)
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
            return Text.translatable(ModLanguages.INFO_CAPACITY_KYE).append(Text.of(": $capacity mB"))
        }


        val CODEC: Codec<FluidStorageData> = RecordCodecBuilder.mapCodec { instance ->
            val a = instance.group(
                Codec.LONG.fieldOf("amount").forGetter<FluidStorageData> {
                    it.amount
                },
                Codec.LONG.fieldOf("capacity").forGetter<FluidStorageData> {
                    it.capacity
                },
                Registries.FLUID.codec.fieldOf("fluid").forGetter<FluidStorageData> {
                    it.fluid
                }
            )
            return@mapCodec a.apply(instance) { amount, capacity, fluid ->
                FluidStorageData(fluid, amount, capacity)
            }
        }.codec()

        val PACKET_CODEC = PacketCodec.ofStatic(::write, ::read)
        fun write(buf: RegistryByteBuf, it: FluidStorageData) {
            buf.writeLong(it.amount)
            buf.writeLong(it.capacity)
            buf.writeIdentifier(it.fluid.identifier())
        }
        fun read(buf: RegistryByteBuf): FluidStorageData {
            val amount = buf.readLong()
            val capacity = buf.readLong()
            val id =buf.readIdentifier()
            val fluid = Registries.FLUID.get(id)
            return FluidStorageData(fluid, amount, capacity)
        }
    }

    fun getDisplayText(): MutableText {
        val name = this.fluid.defaultState.blockState.block.name
        return if (this.isBlank()) getEmptyText(this.capacity) else
            name.append(Text.of(": ${amount}/${capacity} mB"))
    }
}