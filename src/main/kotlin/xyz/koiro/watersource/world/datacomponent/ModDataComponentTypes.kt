package xyz.koiro.watersource.world.datacomponent

import net.minecraft.component.DataComponentType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import xyz.koiro.watersource.WaterSource
import xyz.koiro.watersource.api.storage.FluidStorageData
import java.util.function.UnaryOperator

object ModDataComponentTypes {
    fun active(){}

    val FLUID_STORAGE = register("fluid_storage") {
        it.codec(FluidStorageData.CODEC).packetCodec(FluidStorageData.PACKET_CODEC)
    }

    private fun <T> register(
        id: String,
        builderOperator: UnaryOperator<DataComponentType.Builder<T>>
    ): DataComponentType<T> {
        return Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            WaterSource.identifier(id), (builderOperator.apply(DataComponentType.builder())).build()
        ) as DataComponentType<T>
    }
}