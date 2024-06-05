@file:Suppress("UnstableApiUsage")

package xyz.koiro.watersource.render.blockentity

import net.fabricmc.fabric.impl.client.rendering.BlockEntityRendererRegistryImpl
import xyz.koiro.watersource.world.block.entity.ModBlockEntities

object ModBlockEntityRenderers {
    fun initialize() {
        BlockEntityRendererRegistryImpl.register(ModBlockEntities.FILTER) { ctx ->
            FilterBERenderer()
        }
    }
}