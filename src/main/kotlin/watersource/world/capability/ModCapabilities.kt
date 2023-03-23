package watersource.world.capability

import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.CapabilityToken

object ModCapabilities {
    val PLAYER_WATER : Capability<PlayerWaterCapability> = CapabilityManager.get(object : CapabilityToken<PlayerWaterCapability>(){})
    val LAST_POSITION : Capability<LastPositionCapability> = CapabilityManager.get(object : CapabilityToken<LastPositionCapability>(){})
}
