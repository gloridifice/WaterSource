package watersource.world.capability

import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.server.level.ServerPlayer
import net.minecraft.util.Mth.floor
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.common.capabilities.AutoRegisterCapability
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.network.PacketDistributor
import watersource.world.network.ModNetWorkHandler
import watersource.world.network.message.PlayerWaterMessage
import watersource.world.recipe.WaterLevelRecipeUtil
import kotlin.math.max
import kotlin.math.min

@AutoRegisterCapability
class PlayerWaterCapability(var maxWaterLevel: Float) {
    var waterLevel: Float = 20f
    var saturationLevel: Float = 5f
    var exhaustionLevel: Float = 0f
        set(value) {
            val maxVal = 40f
            val count = floor(value / maxVal)
            reduceWater(count)
            val mod = value - count * maxVal
            field = mod
        }

    private fun reduceWater(count: Int) {
        var i = saturationLevel - count
        saturationLevel = max(i, 0f)
        if (i < 0) waterLevel = max(0f, i + waterLevel)
    }

    fun updateToClient(player: ServerPlayer){
        ModNetWorkHandler.INSTANCE.send(PacketDistributor.PLAYER.with({player}),PlayerWaterMessage(this.writeToNBT()))
    }

    companion object {
        const val WATER_LEVEL_KEY = "waterLevel"
        const val Saturation_Level_Key = "saturationLevel"
        const val Exhaustion_Level_Key = "exhaustionLevel"
        fun Player.drink(itemStack: ItemStack) {
            this.waterCap().ifPresent {
                val recipe = WaterLevelRecipeUtil.getRecipe(this.level, itemStack)
                if (recipe != null) {
                    it.waterLevel = min(recipe.water + it.waterLevel, it.maxWaterLevel)
                    it.saturationLevel = min(recipe.water * recipe.saturationModifier * 2 + it.saturationLevel, it.waterLevel)
                }
                if (this is ServerPlayer) {
                    it.updateToClient(this)
                }
            }
        }

        fun Player.consumeWater(exhaustion: Float) {
            this.waterCap().ifPresent {
                it.exhaustionLevel += exhaustion
            }
        }
        fun Player.waterCap(): LazyOptional<PlayerWaterCapability> {
            return this.getCapability(ModCapabilities.PLAYER_WATER)
        }
    }

    fun writeToNBT(): Tag {
        val tag = CompoundTag()
        tag.putDouble(WATER_LEVEL_KEY, waterLevel.toDouble())
        tag.putDouble(Saturation_Level_Key, saturationLevel.toDouble())
        tag.putDouble(Exhaustion_Level_Key, exhaustionLevel.toDouble())
        return tag
    }

    fun readFromNBT(tag: Tag?) {
        if (tag is CompoundTag) {
            waterLevel = tag.getDouble(WATER_LEVEL_KEY).toFloat()
            saturationLevel = tag.getDouble(Saturation_Level_Key).toFloat()
            exhaustionLevel = tag.getDouble(Exhaustion_Level_Key).toFloat()
        }

    }

    class Provider : ICapabilitySerializable<Tag> {
        val instance: PlayerWaterCapability = PlayerWaterCapability(20f)
        val handler: LazyOptional<PlayerWaterCapability> = LazyOptional.of({ instance })
        override fun <T : Any?> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
            return ModCapabilities.PLAYER_WATER.orEmpty(cap, handler)
        }

        override fun serializeNBT(): Tag = instance.writeToNBT()

        override fun deserializeNBT(nbt: Tag?) {
            instance.readFromNBT(nbt)
        }
    }
}