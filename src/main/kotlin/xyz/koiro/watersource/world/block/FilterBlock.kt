package xyz.koiro.watersource.world.block

import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.potion.PotionUtil
import net.minecraft.potion.Potions
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldView
import xyz.koiro.watersource.api.storage.getOrCreateFluidStorageData
import xyz.koiro.watersource.api.storage.setFluidStorage
import xyz.koiro.watersource.simpleStack
import xyz.koiro.watersource.world.block.entity.FilterBlockEntity
import xyz.koiro.watersource.world.block.entity.ModBlockEntities
import xyz.koiro.watersource.world.fluid.ModFluids
import xyz.koiro.watersource.world.item.*
import kotlin.math.min

class FilterBlock(val capacity: Long, settings: Settings?) : BlockWithEntity(settings) {
    init {
        defaultState = defaultState.with(IS_UP, false)
    }

    fun isUp(state: BlockState): Boolean {
        return state.get(IS_UP)
    }

    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity?,
        hand: Hand?,
        hit: BlockHitResult?
    ): ActionResult {
        val entity = world.getBlockEntity(pos)
        if (player != null && hand != null && entity is FilterBlockEntity && !world.isClient) {
            val handStack = player.getStackInHand(hand)
            val data = entity.fluidStorageData
            val handItem = handStack.item
            val canModifyStrainer = entity.getUpFluidStorage(world)?.isBlank() ?: return ActionResult.SUCCESS
            val strainerStorage = entity.getStrainerStorage(world) ?: return ActionResult.SUCCESS
            val hasStrainerAndIsUp = !strainerStorage.stack.isEmpty && isUp(state)

            if (handStack.isEmpty) {
                when {
                    player.isSneaking -> {
                        if (!entity.fluidStorageData.isBlank()) {
                            data.clear()
                        } else {
                            if (!strainerStorage.stack.isEmpty && canModifyStrainer) {
                                player.setStackInHand(hand, entity.extractStrainerInUp(world))
                            }
                        }
                    }
                }
            } else {
                when {
                    handItem is Strainer && canModifyStrainer -> {
                        val insert = entity.insertStrainerInUp(world, handStack)
                        player.setStackInHand(hand, insert)
                    }

                    handItem is FluidContainer -> {
                        handStack.getOrCreateFluidStorageData()?.let { containerFluidData ->
                            if (containerFluidData.isBlank()) {
                                val transferResult = data.transferTo(
                                    containerFluidData,
                                    min(containerFluidData.restCapacity(), data.amount),
                                    true
                                )
                                if (transferResult != null) {
                                    player.setStackInHand(hand, handItem.setStorageData(handStack, transferResult.second))
                                }
                            } else {
                                if (isUp(state)) {
                                    val transferResult = containerFluidData.transferTo(
                                        data,
                                        min(data.restCapacity(), containerFluidData.amount),
                                        true
                                    )
                                    if (transferResult != null) {
                                        player.setStackInHand(hand, handItem.setStorageData(handStack, transferResult.first))
                                    }
                                }
                            }
                        }
                    }

                    handItem is EmptyFluidContainerItem -> {
                        val containerStack = handItem.containerStack()
                        if (!data.isBlank()) {
                            containerStack.getOrCreateFluidStorageData()?.let { containerFluidData ->
                                val transferResult = data.transferTo(
                                    containerFluidData,
                                    min(containerFluidData.restCapacity(), data.amount),
                                    true
                                )
                                if (transferResult != null) {
                                    player.setStackInHand(hand, (containerStack.item as FluidContainer).setStorageData(containerStack, transferResult.second))

                                }
                            }
                        }
                    }

                    handItem == Items.WATER_BUCKET && hasStrainerAndIsUp -> {
                        if (data.insert(1000, Fluids.WATER, true)) {
                            player.setStackInHand(hand, ItemStack(Items.BUCKET))
                        }
                    }

                    handItem == Items.BUCKET && data.fluid == Fluids.WATER && data.amount >= 1000 -> {
                        data.extract(1000)
                        player.setStackInHand(hand, ItemStack(Items.WATER_BUCKET))
                    }

                    handItem == Items.GLASS_BOTTLE && (data.fluid == Fluids.WATER || data.fluid == ModFluids.PURIFIED_WATER) && data.amount >= 250 -> {
                        val newStack = when (data.fluid) {
                            Fluids.WATER -> PotionUtil.setPotion(Items.POTION.simpleStack(), Potions.WATER)
                            ModFluids.PURIFIED_WATER -> ModItems.PURIFIED_WATER_BOTTLE.simpleStack()
                            else -> null
                        }
                        newStack?.let { newStack ->
                            handStack.decrement(1)
                            if (handStack.isEmpty)
                                player.setStackInHand(hand, newStack)
                            else
                                player.inventory.insertStack(newStack)
                            data.extract(250)
                        }
                    }

                    handItem == Items.POTION && PotionUtil.getPotion(handStack) == Potions.WATER && hasStrainerAndIsUp -> {
                        if (data.insert(250, Fluids.WATER, true)) {
                            handStack.decrement(1)
                            val newStack = Items.GLASS_BOTTLE.simpleStack()
                            if (handStack.isEmpty)
                                player.setStackInHand(hand, newStack)
                            else
                                player.inventory.insertStack(newStack)
                        }
                    }

                    else -> {}
                }
            }
            entity.syncToClientOfPlayersInRadius(world, 50f)
        }

        return ActionResult.SUCCESS
    }

    override fun getOutlineShape(
        state: BlockState,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape {
        return if (state.get(IS_UP)) UP_SHAPE else DOWN_SHAPE
    }

    override fun onPlaced(
        world: World,
        pos: BlockPos,
        state: BlockState,
        placer: LivingEntity?,
        itemStack: ItemStack
    ) {
        if (!world.isClient) {
            world.setBlockState(pos.up(), defaultState.with(IS_UP, true))
        }
        super.onPlaced(world, pos, state, placer, itemStack)
    }

    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos): Boolean {
        return world.getBlockState(pos.up()).isAir && world.getBlockState(pos).isAir
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        super.appendProperties(builder)
        builder?.add(IS_UP)
    }

    override fun onBreak(world: World?, pos: BlockPos, state: BlockState, player: PlayerEntity?) {
        super.onBreak(world, pos, state, player)
        val isUp = state.get(IS_UP)
        val otherPos = if (isUp) pos.down() else pos.up()
        world?.removeBlock(otherPos, false)
        world?.removeBlockEntity(otherPos)
    }

    override fun isCullingShapeFullCube(state: BlockState?, world: BlockView?, pos: BlockPos?): Boolean {
        return false
    }

    companion object {
        val IS_UP: BooleanProperty = BooleanProperty.of("up")

        val DOWN_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 32.0, 16.0)
        val UP_SHAPE = Block.createCuboidShape(0.0, -16.0, 0.0, 16.0, 16.0, 16.0)
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return FilterBlockEntity(pos, state, isUp = state.get(IS_UP), capacity = capacity)
    }

    override fun getRenderType(state: BlockState?): BlockRenderType {
        return BlockRenderType.MODEL
    }

    override fun <T : BlockEntity?> getTicker(
        world: World?,
        state: BlockState?,
        type: BlockEntityType<T>?
    ): BlockEntityTicker<T>? {
        return checkType(type, ModBlockEntities.FILTER) { world1, pos, state1, be ->
            FilterBlockEntity.tick(world1, pos, state1, be)
        }
    }
}