package xyz.koiro.watersource

import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import xyz.koiro.watersource.attechment.ModAttachmentTypes
import xyz.koiro.watersource.attechment.WaterLevelData

fun Item.identifier(): Identifier {
    return Registries.ITEM.getId(this)
}

fun Fluid.identifier(): Identifier {
    return Registries.FLUID.getId(this)
}


fun Item.simpleStack(): ItemStack {
    return ItemStack(this)
}

fun StatusEffect.identifier(): Identifier? {
    return Registries.STATUS_EFFECT.getId(this)
}

fun PlayerEntity.canExhaustWater(): Boolean {
    return !(this.isCreative || this.isSpectator)
}

fun PlayerEntity.ifInSurvivalAndGetWaterData(action: (data: WaterLevelData) -> Unit) {
    if (this.canExhaustWater()) {
        val data = this.getAttachedOrCreate(ModAttachmentTypes.WATER_LEVEL)
        action(data)
    }
}