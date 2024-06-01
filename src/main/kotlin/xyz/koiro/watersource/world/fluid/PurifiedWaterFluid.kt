package xyz.koiro.watersource.world.fluid

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.item.Item
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView
import xyz.koiro.watersource.world.block.ModBlocks
import xyz.koiro.watersource.world.item.ModItems


abstract class PurifiedWaterFluid : FlowableFluid() {
    override fun getBucketItem(): Item {
        return ModItems.PURIFIED_WATER_BUCKET
    }

    override fun toBlockState(fluidState: FluidState?): BlockState {
        // getBlockStateLevel converts the LEVEL_1_8 of the fluid state to the LEVEL_15 the fluid block uses
        return ModBlocks.PURIFIED_WATER.defaultState.with(Properties.LEVEL_15, getBlockStateLevel(fluidState))
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

    /**
     * @return whether the given fluid an instance of this fluid
     */
    override fun matchesType(fluid: Fluid): Boolean {
        return fluid === still || fluid === flowing
    }

    /**
     * Perform actions when the fluid flows into a replaceable block. Water drops
     * the block's loot table. Lava plays the "block.lava.extinguish" sound.
     */
    override fun beforeBreakingBlock(world: WorldAccess, pos: BlockPos?, state: BlockState) {
        val blockEntity = if (state.hasBlockEntity()) world.getBlockEntity(pos) else null
        Block.dropStacks(state, world, pos, blockEntity)
    }

    /**
     * Lava returns true if it's FluidState is above a certain height and the
     * Fluid is Water.
     *
     * @return whether the given Fluid can flow into this FluidState
     */
    override fun canBeReplacedWith(
        fluidState: FluidState?,
        blockView: BlockView?,
        blockPos: BlockPos?,
        fluid: Fluid?,
        direction: Direction?
    ): Boolean {
        return false
    }

    /**
     * Possibly related to the distance checks for flowing into nearby holes?
     * Water returns 4. Lava returns 2 in the Overworld and 4 in the Nether.
     */
    override fun getFlowSpeed(worldView: WorldView?): Int {
        return 4
    }

    override fun getLevelDecreasePerBlock(worldView: WorldView?): Int {
        return 1
    }

    override fun getTickRate(worldView: WorldView?): Int {
        return 5
    }

    override fun getBlastResistance(): Float {
        return 100.0f
    }

    class Flowing : PurifiedWaterFluid() {
        override fun appendProperties(builder: StateManager.Builder<Fluid?, FluidState?>) {
            super.appendProperties(builder)
            builder.add(LEVEL)
        }

        override fun getLevel(state: FluidState): Int =
            state.get(LEVEL) ?: 0

        override fun isStill(state: FluidState?): Boolean = false
    }

    class Still : PurifiedWaterFluid() {
        override fun getLevel(state: FluidState?): Int = 8
        override fun isStill(state: FluidState?): Boolean = true
    }
}