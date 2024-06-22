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
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
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
import xyz.koiro.watersource.api.setStackInHandOrInsertIntoInventory
import xyz.koiro.watersource.api.storage.getOrCreateFluidStorageData
import xyz.koiro.watersource.simpleStack
import xyz.koiro.watersource.world.block.entity.FilterBlockEntity
import xyz.koiro.watersource.world.block.entity.ModBlockEntities
import xyz.koiro.watersource.world.fluid.ModFluids
import xyz.koiro.watersource.world.item.*
import kotlin.math.min

open class FilterBlock(val capacity: Long, val filterVolumePerSecond: Long, settings: Settings?) :
    BlockWithEntity(settings) {
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
        if (player != null && hand != null && entity is FilterBlockEntity) {
            val handStack = player.getStackInHand(hand)
            val data = entity.fluidStorageData
            val handItem = handStack.item
            val canModifyStrainer = entity.getUpFluidStorage(world)?.isBlank() ?: return ActionResult.SUCCESS
            val strainerStorage = entity.getStrainerStorage(world) ?: return ActionResult.SUCCESS
            val hasStrainerAndIsUp = !strainerStorage.stack.isEmpty && isUp(state)

            fun playInsertSound() {
                world.playSoundAtBlockCenter(pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.PLAYERS, 1f, 1f, true)
            }

            fun playExtractSound() {
                world.playSoundAtBlockCenter(pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.PLAYERS, 1f, 1f, true)
            }

            if (handStack.isEmpty) {
                when {
                    player.isSneaking -> {
                        if (!entity.fluidStorageData.isBlank()) {
                            data.clear()
                            playExtractSound()
                        } else {
                            if (!strainerStorage.stack.isEmpty && canModifyStrainer) {
                                player.setStackInHand(hand, entity.extractStrainerInUp(world))
                                world.playSoundAtBlockCenter(
                                    pos,
                                    SoundEvents.ENTITY_ITEM_PICKUP,
                                    SoundCategory.PLAYERS,
                                    1f,
                                    1f,
                                    true
                                )
                            }
                        }
                    }
                }
            } else {
                when {
                    handItem is Strainer -> {
                        val inner = entity.insertStrainerInUp(world, handStack.copy())
                        inner?.let {
                            handStack.decrement(1)
                            player.setStackInHandOrInsertIntoInventory(hand, it)
                            world.playSoundAtBlockCenter(
                                pos,
                                SoundEvents.BLOCK_WOOL_PLACE,
                                SoundCategory.PLAYERS,
                                1f,
                                1f,
                                true
                            )
                        }
                    }

                    handItem is FluidContainerItem -> {
                        handStack.getOrCreateFluidStorageData()?.let { containerFluidData ->
                            fun transferFilterToContainer() {
                                val transferResult = data.transferTo(
                                    containerFluidData,
                                    min(containerFluidData.restCapacity(), data.amount),
                                    true
                                )
                                if (transferResult != null) {
                                    val stack = handItem.setStorageData(handStack.copy(), transferResult.second)
                                    handStack.decrement(1)
                                    player.setStackInHandOrInsertIntoInventory(hand, stack)
                                    (stack.item as? FluidContainerItem)?.onFluidDataChanged(stack, player, hand)
                                    playExtractSound()
                                }
                            }

                            fun transferContainerToFilter() {
                                val transferResult = containerFluidData.transferTo(
                                    data,
                                    min(data.restCapacity(), containerFluidData.amount),
                                    true
                                )
                                if (transferResult != null) {
                                    val stack = handItem.setStorageData(handStack.copy(), transferResult.first)
                                    handStack.decrement(1)
                                    player.setStackInHandOrInsertIntoInventory(hand, stack)
                                    (stack.item as? FluidContainerItem)?.onFluidDataChanged(stack, player, hand)
                                    playInsertSound()
                                }
                            }

                            if (containerFluidData.isBlank()) {
                                transferFilterToContainer()
                            } else {
                                if (isUp(state)) {
                                    transferContainerToFilter()
                                } else {
                                    transferFilterToContainer()
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
                                    val stack = (containerStack.item as? FluidContainerItem)?.setStorageData(
                                        containerStack,
                                        transferResult.second
                                    )
                                    if (stack != null) {
                                        (stack.item as? FluidContainerItem)?.onFluidDataChanged(stack, player, hand)
                                        handStack.decrement(1)
                                        player.setStackInHandOrInsertIntoInventory(hand, stack)
                                        playExtractSound()
                                    }
                                }
                            }
                        }
                    }

                    handItem == Items.WATER_BUCKET && hasStrainerAndIsUp -> {
                        if (data.insert(1000, Fluids.WATER, true)) {
                            player.setStackInHand(hand, ItemStack(Items.BUCKET))
                            playInsertSound()
                        }
                    }

                    handItem == Items.BUCKET && data.fluid == Fluids.WATER && data.amount >= 1000 -> {
                        data.extract(1000)
                        player.setStackInHand(hand, ItemStack(Items.WATER_BUCKET))
                        playExtractSound()
                    }

                    handItem == Items.GLASS_BOTTLE && (data.fluid == Fluids.WATER || data.fluid == ModFluids.PURIFIED_WATER) && data.amount >= 250 -> {
                        val newStack = when (data.fluid) {
                            Fluids.WATER -> PotionUtil.setPotion(Items.POTION.simpleStack(), Potions.WATER)
                            ModFluids.PURIFIED_WATER -> ModItems.PURIFIED_WATER_BOTTLE.simpleStack()
                            else -> null
                        }
                        newStack?.let { newStack ->
                            handStack.decrement(1)
                            player.inventory.insertStack(newStack)
                            data.extract(250)
                            playExtractSound()
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
                            playInsertSound()
                        }
                    }

                    else -> {}
                }
            }
            entity.syncToClientOfPlayersInRadius(world, 50f)
            entity.markAllDirty(world)
        }

        return ActionResult.SUCCESS
    }

    override fun getOutlineShape(
        state: BlockState,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape {
        return if (isUp(state)) UP_SHAPE else DOWN_SHAPE
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

    override fun hasComparatorOutput(state: BlockState?): Boolean {
        return true
    }

    override fun getComparatorOutput(state: BlockState, world: World, pos: BlockPos): Int {
        val entity = world.getBlockEntity(pos) as? FilterBlockEntity ?: return 0
        val strainer = entity.getStrainerStorage(world) ?: return 0
        val downFluidData = entity.getDownFluidStorage(world) ?: return 0
        var ret = 0
        if (strainer.stack.item is Strainer) {
            ret += 5
        }
        if (!downFluidData.isBlank()) ret += 2
        if (downFluidData.isFull()) ret += 4

        return ret
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