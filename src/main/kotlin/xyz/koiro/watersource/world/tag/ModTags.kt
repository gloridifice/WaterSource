package xyz.koiro.watersource.world.tag

import net.minecraft.item.Item
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import xyz.koiro.watersource.WaterSource

object ModTags {
    object Item{
        val PURIFICATION_STRAINER = modTag("purification_strainer")
        val BASICS_INGOT = modTag("basics_ingot")

        private fun modTag(id: String) =
            TagKey.of(RegistryKeys.ITEM, WaterSource.identifier(id))!!
    }

    object Block{

    }

    object Fluid{

    }
}