package xyz.koiro.watersource.world.block.entity

import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import xyz.koiro.watersource.WaterSource
import xyz.koiro.watersource.world.block.FilterBlock
import xyz.koiro.watersource.world.block.ModBlocks

object ModBlockEntities {
    fun active() {}
    val FILTER = regBlockEntity(
        "filter",
        FabricBlockEntityTypeBuilder.create({ pos, state ->
            FilterBlockEntity(pos, state, (state.block as FilterBlock).capacity)
        }, ModBlocks.WOODEN_FILTER, ModBlocks.IRON_FILTER).build()
    )

    fun <T : BlockEntity?> regBlockEntity(id: String, type: BlockEntityType<T>): BlockEntityType<T> {
        return Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            WaterSource.identifier(id),
            type
        )
    }
}