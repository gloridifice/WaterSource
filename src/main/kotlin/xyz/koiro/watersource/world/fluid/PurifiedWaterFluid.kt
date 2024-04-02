package xyz.koiro.watersource.world.fluid

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.FluidBlock
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.item.Item
import net.minecraft.registry.tag.FluidTags
import net.minecraft.state.StateManager
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView
import xyz.koiro.watersource.world.block.ModBlocks
import xyz.koiro.watersource.world.item.ModItems
import kotlin.math.min

abstract class PurifiedWaterFluid : FlowableFluid() {
    override fun getBucketItem(): Item {
        return ModItems.PURIFIED_WATER_BUCKET
    }

    override fun canBeReplacedWith(
        state: FluidState?,
        world: BlockView?,
        pos: BlockPos?,
        fluid: Fluid?,
        direction: Direction?
    ): Boolean {
        return direction == Direction.DOWN && !fluid!!.isIn(FluidTags.WATER)
    }

    override fun getTickRate(world: WorldView?): Int {
        return 5
    }

    override fun getBlastResistance(): Float {
        return 100f
    }

    override fun toBlockState(state: FluidState): BlockState {
        val level: Int = if (state.isStill) 0 else
            8 - min(state.level, 8) + (if (state.get(FALLING)) 8 else 0)
        return ModBlocks.PURIFIED_WATER.defaultState.with(FluidBlock.LEVEL, level)
    }

    override fun getFlowing(): Fluid {
        return ModFluids.FLOWING_PURIFIED_WATER
    }

    override fun getStill(): Fluid {
        return ModFluids.PURIFIED_WATER
    }

    override fun isInfinite(world: World?): Boolean {
        return false
    }

    override fun beforeBreakingBlock(world: WorldAccess?, pos: BlockPos?, state: BlockState?) {
        val blockEntity = if (state!!.hasBlockEntity()) world!!.getBlockEntity(pos) else null
        Block.dropStacks(state, world, pos, blockEntity)
    }

    override fun getFlowSpeed(world: WorldView?): Int {
        return 5
    }

    override fun getLevelDecreasePerBlock(world: WorldView?): Int {
        return 1
    }

    class Flowing : PurifiedWaterFluid() {

        override fun appendProperties(builder: StateManager.Builder<Fluid?, FluidState?>) {
            super.appendProperties(builder)
            builder.add(LEVEL)
        }

        override fun getLevel(state: FluidState?): Int {
            return state?.get(LEVEL) ?: 0
        }

        override fun isStill(state: FluidState?): Boolean {
            return false
        }
    }

    class Still : PurifiedWaterFluid() {
        override fun getLevel(state: FluidState?): Int {
            return 8
        }

        override fun isStill(state: FluidState?): Boolean {
            return true
        }
    }
}