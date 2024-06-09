package xyz.koiro.watersource.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import xyz.koiro.watersource.BlockLootTableType
import xyz.koiro.watersource.world.block.ModBlocks

class ModBlockLootTableGenerator(dataOutput: FabricDataOutput?) : FabricBlockLootTableProvider(dataOutput) {
    override fun generate() {
        ModBlocks.reflectAutoGenDataBlocks().forEach {
            val data = it.second
            val block = it.first
            when (data.lootTable) {
                BlockLootTableType.DontGen -> {}
                BlockLootTableType.SingleBlockItem -> {
                    addDrop(block)
                }
            }
        }
    }
}