package xyz.koiro.watersource.world.block.entity

import net.minecraft.block.entity.BlockEntityType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

object ModBlockEntities {

    fun regModBlockEntity(id: String, type: BlockEntityType<*>): BlockEntityType<*> {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, type)
    }


}