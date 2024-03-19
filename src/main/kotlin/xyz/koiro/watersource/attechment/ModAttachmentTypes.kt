@file:Suppress("UnstableApiUsage")

package xyz.koiro.watersource.attechment

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry
import net.minecraft.util.math.Vec3d
import org.apache.commons.lang3.mutable.MutableInt
import xyz.koiro.watersource.WaterSource
import java.util.Optional

object ModAttachmentTypes {
    val WATER_LEVEL = AttachmentRegistry.createDefaulted(WaterSource.identifier("water_level")) {
        WaterLevelData()
    }!!
    val POSITION_OFFSET =
        AttachmentRegistry.createDefaulted(WaterSource.identifier("last_position")) {
            Optional.empty<PosOffset>()
        }!!
    val WATER_REWARD_HEAL_TICKER =
        AttachmentRegistry.createDefaulted(WaterSource.identifier("water_reward_heal_ticker")) { MutableInt(0) }

    val THIRSTY_ADD_EXHAUSTION_TICKER =
        AttachmentRegistry.createDefaulted(WaterSource.identifier("thirsty_add_exhaustion_ticker")) { MutableInt(0) }

    data class PosOffset(
        val current: Vec3d,
        val last: Vec3d,
    ) {
        val offset: Vec3d = current.subtract(last)
    }
}
