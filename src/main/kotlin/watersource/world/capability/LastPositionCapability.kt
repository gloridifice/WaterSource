package watersource.world.capability

import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.world.phys.Vec3
import net.minecraftforge.common.capabilities.AutoRegisterCapability
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.common.util.LazyOptional

@AutoRegisterCapability
class LastPositionCapability {
    var lastPosition: Vec3 = Vec3(.0, .0, .0)
    var lastOnGround: Boolean = false


    companion object{
        const val LAST_X_KEY = "lastX"
        const val LAST_Y_KEY = "lastY"
        const val LAST_Z_KEY = "lastZ"
        const val LAST_ON_GROUND_KEY ="lastOnGround"
    }
    fun writeToNBT(): Tag{
        val tag = CompoundTag()
        tag.putDouble(LAST_X_KEY, lastPosition.x)
        tag.putDouble(LAST_Y_KEY, lastPosition.y)
        tag.putDouble(LAST_Z_KEY, lastPosition.z)
        tag.putBoolean(LAST_ON_GROUND_KEY, lastOnGround)
        return tag
    }
    fun readFromNBT(nbt: Tag?){
        if (nbt is CompoundTag) {
            val x = nbt.getDouble(LAST_X_KEY)
            val y = nbt.getDouble(LAST_Y_KEY)
            val z = nbt.getDouble(LAST_Z_KEY)
            lastOnGround = nbt.getBoolean(LAST_ON_GROUND_KEY)
            lastPosition = Vec3(x,y,x)
        }
    }
    class Provider : ICapabilitySerializable<Tag> {
        val instance: LastPositionCapability = LastPositionCapability()
        val handler: LazyOptional<LastPositionCapability> = LazyOptional.of({ instance })
        override fun <T : Any?> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
            return ModCapabilities.LAST_POSITION.orEmpty(cap, handler)
        }

        override fun serializeNBT(): Tag = instance.writeToNBT()
        override fun deserializeNBT(nbt: Tag?) { instance.readFromNBT(nbt) }

    }
}