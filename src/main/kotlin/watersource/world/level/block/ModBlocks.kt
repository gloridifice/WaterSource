package watersource.world.level.block

import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.registerObject
import watersource.WaterSource
import watersource.world.level.item.ModCreativeModeTabs
import watersource.world.level.item.ModItems

object ModBlocks {
    val REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, WaterSource.ID)

    val WATER_FILTER_BLOCK by REGISTRY.registerObject("water_filter") { Block(BlockBehaviour.Properties.of(Material.WOOD).lightLevel { 13 }) }
    val WATER_FILTER_ITEM by ModItems.REGISTRY.registerObject("water_filter") { BlockItem(WATER_FILTER_BLOCK, Item.Properties().tab(ModCreativeModeTabs.TAB_WATERSOURCE)) }
}