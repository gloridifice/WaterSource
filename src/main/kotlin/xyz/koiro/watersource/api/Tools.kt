package xyz.koiro.watersource.api

import net.minecraft.entity.effect.StatusEffect
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.entry.RegistryEntry
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

fun RegistryEntry<StatusEffect>.identifier(): Identifier? {
    return if (this.hasKeyAndValue())
        Registries.STATUS_EFFECT.getId(this.value())
    else null
}

fun String.toIdentifier(): Identifier? {
    return Identifier.tryParse(this)
}

fun StatusEffect.entry(): RegistryEntry<StatusEffect>{
    return RegistryEntry.of(this)
}
