package xyz.koiro.watersource.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.Models
import xyz.koiro.watersource.world.block.ModBlocks
import xyz.koiro.watersource.world.item.ModItems

class ModModelGenerator(output: FabricDataOutput?) : FabricModelProvider(output) {
    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator) {
        ModBlocks.reflectAutoGenDataBlocks().filter { it.second.genItemModel }.forEach {
            blockStateModelGenerator.registerItemModel(it.first)
        }
    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerator) {
        genAutoDataItem(itemModelGenerator)
    }

    private fun genAutoDataItem(itemModelGenerator: ItemModelGenerator) {
        ModItems.reflectAutoGenDataItems().forEach {
            itemModelGenerator.register(it.first, it.second.modelType.model)
        }
    }
}