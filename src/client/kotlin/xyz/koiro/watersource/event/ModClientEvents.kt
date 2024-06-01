package xyz.koiro.watersource.event

import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.client.gui.DrawContext
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.ActionResult
import xyz.koiro.watersource.event.ModServerEvents.PlayerJump

object ModClientEvents {

    fun interface DrawMouseoverTooltip {
        fun interact(context: DrawContext, x: Int, y: Int, stack: ItemStack): ActionResult
    }
    val DRAW_MOUSEOVER_TOOLTIP = EventFactory.createArrayBacked(DrawMouseoverTooltip::class.java) { listeners ->
        DrawMouseoverTooltip { context, x, y, stack ->
            for (listener in listeners) {
                val result = listener.interact(context, x, y, stack)
                if (result != ActionResult.PASS) return@DrawMouseoverTooltip result
            }
            ActionResult.PASS
        }
    }!!
}