package xyz.koiro.watersource

import net.minecraft.entity.effect.StatusEffect
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier

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

