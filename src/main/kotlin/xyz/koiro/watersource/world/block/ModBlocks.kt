package xyz.koiro.watersource.world.block

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import xyz.koiro.watersource.WaterSource

object ModBlocks {


    fun registerBlock(registryName: String, block: Block): Block {
        return Registry.register(Registries.BLOCK, Identifier(WaterSource.MODID, registryName), block)
    }
}