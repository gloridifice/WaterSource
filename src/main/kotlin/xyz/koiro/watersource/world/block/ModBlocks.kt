package xyz.koiro.watersource.world.block

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.FluidBlock
import net.minecraft.block.MapColor
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.fluid.FlowableFluid
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.Identifier
import xyz.koiro.watersource.AutoGenBlockData
import xyz.koiro.watersource.AutoGenItemData
import xyz.koiro.watersource.WaterSource
import xyz.koiro.watersource.world.fluid.ModFluids
import xyz.koiro.watersource.world.item.ModItems

object ModBlocks {
    @AutoGenBlockData("Purified Water", "净化水", false)
    val PURIFIED_WATER = registerBlock(
        "purified_water",
        FluidBlock(ModFluids.PURIFIED_WATER as FlowableFluid, FabricBlockSettings.copy(Blocks.WATER))
    )

    fun active() {}
    fun registerBlock(registryName: String, block: Block): Block {
        return Registry.register(Registries.BLOCK, Identifier(WaterSource.MODID, registryName), block)
    }

    fun reflectAutoGenDataBlocks(): Iterable<Pair<Block, AutoGenBlockData>> =
        ModBlocks::class.java.getDeclaredFields().filter {
            val block = it.get(null) as? Block
            val isBlock = block != null
            val isAutoGenData = it.getAnnotation(AutoGenBlockData::class.java) != null
            isBlock && isAutoGenData
        }.map {
            val item = it.get(null) as Block
            val autoGenData = it.getAnnotation(AutoGenBlockData::class.java)
            Pair(item, autoGenData)
        }
}