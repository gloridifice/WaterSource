package xyz.koiro.watersource

import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier

fun Item.identifier(): Identifier {
    return Registries.ITEM.getId(this)
}
fun Fluid.identifier(): Identifier {
    return Registries.FLUID.getId(this)
}
