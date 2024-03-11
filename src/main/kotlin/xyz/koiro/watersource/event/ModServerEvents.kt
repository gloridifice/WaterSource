package xyz.koiro.watersource.event

import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.ActionResult

object ModServerEvents {
    public val PLAYER_JUMP = EventFactory.createArrayBacked(PlayerJump::class.java) { listeners ->
        PlayerJump {
            for (listener in listeners) {
                val result = listener.interact(it)
                if (result != ActionResult.PASS) return@PlayerJump result
            }
            ActionResult.PASS
        }
    }!!

    public val FINISH_USING_ITEM = EventFactory.createArrayBacked(FinishUsingItem::class.java) { listeners ->
        FinishUsingItem { livingEntity: LivingEntity, serverWorld: ServerWorld, itemStack: ItemStack ->
            for (listener in listeners) {
                val result = listener.interact(livingEntity, serverWorld, itemStack)
                if (result != ActionResult.PASS) return@FinishUsingItem result
            }
            ActionResult.PASS
        }
    }!!


    fun interface PlayerJump {
        fun interact(player: PlayerEntity): ActionResult
    }
    fun interface FinishUsingItem {
        fun interact(user: LivingEntity, world: ServerWorld, stack: ItemStack): ActionResult
    }

}
