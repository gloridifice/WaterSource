package watersource.world.capability

import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraftforge.common.util.FakePlayer
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import watersource.WaterSource
import watersource.world.capability.PlayerWaterCapability.Companion.waterCap

@Mod.EventBusSubscriber(modid = WaterSource.ID)
object CapabilityEventHandler{
    const val WATER_LEVEL_CAPABILITY_NAME = "player_water_level"
    const val LAST_POSITION_CAPABILITY_NAME = "last_position"
    @SubscribeEvent
    fun addCapability(event: AttachCapabilitiesEvent<Entity>){
        if (event.`object` is Player && event.`object` !is FakePlayer){
            event.addCapability(ResourceLocation(WaterSource.ID, WATER_LEVEL_CAPABILITY_NAME), PlayerWaterCapability.Provider())
            event.addCapability(ResourceLocation(WaterSource.ID, LAST_POSITION_CAPABILITY_NAME), LastPositionCapability.Provider())
        }
    }
    @SubscribeEvent
    fun onPlayerClone(event: PlayerEvent.Clone){
        //Todo 添加死亡是否保留水分的配置
        val player = event.entity
        if(player is Player && player !is FakePlayer){
            val now = player.waterCap()
            val original = event.original.waterCap()
            if (now.isPresent && original.isPresent){
                now.orElse(null).readFromNBT(original.orElse(null).writeToNBT())
            }
        }
    }
    @SubscribeEvent
    fun onPlayerLogin(event: PlayerEvent.PlayerLoggedInEvent){
        val player = event.entity
        if (player is ServerPlayer && player !is FakePlayer){
            player.waterCap().ifPresent {
                it.updateToClient(player)
            }
        }
    }
}