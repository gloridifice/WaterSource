@file:Suppress("UnstableApiUsage")

package xyz.koiro.watersource.world.block.entity

import net.minecraft.block.BlockState
import net.minecraft.fluid.Fluid
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.listener.ClientPlayPacketListener
import net.minecraft.network.packet.Packet
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.registry.RegistryWrapper
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import xyz.koiro.watersource.WSConfig
import xyz.koiro.watersource.api.storage.FluidStorageData
import xyz.koiro.watersource.api.storage.ItemStorageData
import xyz.koiro.watersource.data.FilterRecipeDataManager
import xyz.koiro.watersource.world.block.FilterBlock
import xyz.koiro.watersource.world.item.Strainer
import kotlin.math.min

class FilterBlockEntity(pos: BlockPos, state: BlockState, var capacity: Long, var isUp: Boolean = false) :
    ContainerBlockEntity(ModBlockEntities.FILTER, pos, state, capacity) {

    var isWorking: Boolean = false;

    class RenderCtx(
        var heightRatio: Float = 0f,
        var targetHeightRatio: Float = 0f,
        var fluidCache: Fluid? = null,
        var strainerRotY: Float = 0f
    )

    val renderCtx = RenderCtx()
    protected var strainerStorage: ItemStorageData = ItemStorageData()
    var filterTicks: Int = 0

    fun getFilterVolumePerSecond(): Long {
        return (cachedState.block as FilterBlock).filterVolumePerSecond
    }

    /** Return the strainer in the up filter. If inserting failed, return null. */
    fun insertStrainerInUp(world: World, insert: ItemStack): ItemStack? {
        val storage = getStrainerStorage(world)
        if (insert.item is Strainer) {
            val ret = storage?.stack
            storage?.stack = insert
            return ret
        }
        return null
    }

    fun extractStrainerInUp(world: World): ItemStack? {
        val storage = getStrainerStorage(world)

        val ret = storage?.stack
        storage?.clear()

        return ret
    }

    fun getUpEntity(world: World): FilterBlockEntity? {
        return if (isUp) this else (world.getBlockEntity(pos.up()) as? FilterBlockEntity)
    }

    fun getUpFluidStorage(world: World): FluidStorageData? {
        return getUpEntity(world)?.fluidStorageData
    }

    fun getDownFluidStorage(world: World): FluidStorageData? {
        return getDownEntity(world)?.fluidStorageData
    }

    fun getDownEntity(world: World): FilterBlockEntity? {
        return if (!isUp) this else (world.getBlockEntity(pos.down()) as? FilterBlockEntity)
    }

    fun getStrainerStorage(world: World): ItemStorageData? {
        return getUpEntity(world)?.strainerStorage
    }

    override fun writeNbt(nbt: NbtCompound?, registryLookup: RegistryWrapper.WrapperLookup?) {
        super.writeNbt(nbt, registryLookup)
        if (nbt == null || registryLookup == null) return
        nbt.putBoolean("isUp", isUp)
        val strainerNbt = strainerStorage.writeNbt(registryLookup)
        nbt.put("strainer", strainerNbt)
        nbt.putBoolean("isWorking", isWorking)
        nbt.putInt("ticks", filterTicks)
    }

    override fun readNbt(nbt: NbtCompound?, registryLookup: RegistryWrapper.WrapperLookup?) {
        super.readNbt(nbt, registryLookup)
        if (nbt == null || registryLookup == null) return
        isUp = nbt.getBoolean("isUp") ?: false
        val strainerNbt = nbt.getCompound("strainer")
        strainerNbt?.let { strainerStorage = ItemStorageData.fromNbt(registryLookup, it) ?: ItemStorageData() }
        isWorking = nbt.getBoolean("isWorking")
        nbt.putInt("ticks", filterTicks)
    }

    override fun toUpdatePacket(): Packet<ClientPlayPacketListener>? {
        return BlockEntityUpdateS2CPacket.create(this)
    }

    fun syncToClient(player: ServerPlayerEntity) {
        player.networkHandler.sendPacket(toUpdatePacket())
    }

    fun syncToClientOfPlayersInRadius(world: World, radius: Float) {
        val players = world.players.filter {
            it.pos.subtract(pos.toCenterPos()).length() < radius
        }.mapNotNull { it as? ServerPlayerEntity }
        players.forEach {
            syncToClient(player = it)
        }
    }

    override fun toInitialChunkDataNbt(registryLookup: RegistryWrapper.WrapperLookup?): NbtCompound? {
        return createNbt(registryLookup)
    }

    var endSynced: Boolean = false

    companion object {
        fun tick(world: World?, pos: BlockPos?, state: BlockState?, entity: FilterBlockEntity?) {
            if (world == null || pos == null || state == null || entity == null) return
            if (world.isClient) return
            if (!entity.isUp) return

            val volumePerSecond = entity.getFilterVolumePerSecond()

            val upFluidStorageData = entity.getUpFluidStorage(world) ?: return
            val downFluidStorageData = entity.getDownFluidStorage(world) ?: return
            val strainerStorage = entity.getStrainerStorage(world) ?: return
            val recipe =
                FilterRecipeDataManager.findRecipe(upFluidStorageData.fluid, strainerStorage.stack)

            val isWorking = recipe != null
                    && (downFluidStorageData.fluid == recipe.outputFluid || downFluidStorageData.isBlank())
                    && downFluidStorageData.amount < downFluidStorageData.capacity
                    && upFluidStorageData.amount > 0
            entity.isWorking = isWorking
            if (recipe != null && isWorking) {
                entity.endSynced = false
                val tickAmount = 20

                // 0.05 * 20 = 1s
                if (entity.filterTicks % tickAmount == 0) {
                    val outFluid = recipe.outputFluid
                    val shouldDamageStrainer =
                        entity.filterTicks % (WSConfig.UNIT_DRINK_VOLUME / volumePerSecond * tickAmount).toInt() == 0

                    if (shouldDamageStrainer) {
                        val strainer = strainerStorage.stack
                        (strainer.item as? Strainer)?.let {
                            strainerStorage.stack = it.getUsedStrainer(strainer, 1)
                        }
                    }

                    val min = min(volumePerSecond, upFluidStorageData.amount)
                    upFluidStorageData.extract(min)
                    downFluidStorageData.insert(min, outFluid, true)
                }
                entity.filterTicks += 1;

                entity.getUpEntity(world)?.syncToClientOfPlayersInRadius(world, 50f)
                entity.getDownEntity(world)?.syncToClientOfPlayersInRadius(world, 50f)

                entity.markAllDirty(world)
            } else {
                entity.filterTicks = 0
                if (!entity.endSynced) {
                    entity.endSynced = true
                    entity.getUpEntity(world)?.syncToClientOfPlayersInRadius(world, 50f)
                    entity.getDownEntity(world)?.syncToClientOfPlayersInRadius(world, 50f)

                    entity.markAllDirty(world)
                }
            }
        }
    }

    fun markAllDirty(world: World) {
        val otherPos = if (isUp) pos.down() else pos.up()
        world.markDirty(pos)
        world.markDirty(otherPos)
        world.updateComparators(pos, cachedState.block)
        world.updateComparators(otherPos, cachedState.block)
    }
}