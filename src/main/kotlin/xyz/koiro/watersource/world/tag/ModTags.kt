package xyz.koiro.watersource.world.tag

import net.minecraft.item.Item
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import xyz.koiro.watersource.WaterSource

object ModTags {
    object Item{
        val PURIFICATION_STRAINER = TagKey.of(RegistryKeys.ITEM, WaterSource.identifier("purification_strainer"))!!;
    }

    object Block{

    }

    object Fluid{

    }
}