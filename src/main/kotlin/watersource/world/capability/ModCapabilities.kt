package watersource.world.capability

import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.CapabilityToken


val PLAYER_WATER : Capability<PlayerWaterCapability> = CapabilityManager.get(object : CapabilityToken<PlayerWaterCapability>(){})