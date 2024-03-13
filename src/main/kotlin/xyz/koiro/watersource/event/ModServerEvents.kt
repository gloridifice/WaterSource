package xyz.koiro.watersource.event

import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.ActionResult

object ModServerEvents {
    val PLAYER_JUMP = EventFactory.createArrayBacked(PlayerJump::class.java) { listeners ->
        PlayerJump {
            for (listener in listeners) {
                val result = listener.interact(it)
                if (result != ActionResult.PASS) return@PlayerJump result
            }
            ActionResult.PASS
        }
    }!!

    val FINISH_USING_ITEM = EventFactory.createArrayBacked(FinishUsingItem::class.java) { listeners ->
        FinishUsingItem { livingEntity: LivingEntity, serverWorld: ServerWorld, itemStack: ItemStack ->
            for (listener in listeners) {
                val result = listener.interact(livingEntity, serverWorld, itemStack)
                if (result != ActionResult.PASS) return@FinishUsingItem result
            }
            ActionResult.PASS
        }
    }!!


    val PLAYER_WRITE_CUSTOM_NBT = EventFactory.createArrayBacked(PlayerWriteCustomNbt::class.java) { listeners ->
        PlayerWriteCustomNbt { player: ServerPlayerEntity, nbt: NbtCompound ->
            for (listener in listeners) {
                val result = listener.interact(player, nbt)
                if (result != ActionResult.PASS) return@PlayerWriteCustomNbt result
            }
            ActionResult.PASS
        }
    }!!

    val PLAYER_READ_CUSTOM_NBT = EventFactory.createArrayBacked(PlayerReadCustomNbt::class.java) { listeners ->
        PlayerReadCustomNbt { player: ServerPlayerEntity, nbt: NbtCompound ->
            for (listener in listeners) {
                val result = listener.interact(player, nbt)
                if (result != ActionResult.PASS) return@PlayerReadCustomNbt result
            }
            ActionResult.PASS
        }
    }!!

    fun interface PlayerWriteCustomNbt {
        fun interact(player: ServerPlayerEntity, nbt: NbtCompound): ActionResult
    }

    fun interface PlayerReadCustomNbt {
        fun interact(player: ServerPlayerEntity, nbt: NbtCompound): ActionResult
    }

    fun interface PlayerJump {
        fun interact(player: PlayerEntity): ActionResult
    }
    fun interface FinishUsingItem {
        fun interact(user: LivingEntity, world: ServerWorld, stack: ItemStack): ActionResult
    }

}
