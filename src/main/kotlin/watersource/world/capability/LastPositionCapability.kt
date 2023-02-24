package watersource.world.capability

import net.minecraft.world.phys.Vec3
import net.minecraftforge.common.capabilities.AutoRegisterCapability

@AutoRegisterCapability
class LastPositionCapability {
    var lastPosition: Vec3 = Vec3(.0,.0,.0)
    var lastOnGround: Boolean = false


}