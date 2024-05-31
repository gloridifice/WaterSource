package xyz.koiro.watersource.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.FluidTagProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.FluidTags
import xyz.koiro.watersource.world.fluid.ModFluids
import xyz.koiro.watersource.world.item.ModItems
import xyz.koiro.watersource.world.tag.ModTags
import java.util.concurrent.CompletableFuture

class ModFluidTagGenerator(
    output: FabricDataOutput?,
    completableFuture: CompletableFuture<RegistryWrapper.WrapperLookup>?
) : FluidTagProvider(output, completableFuture) {
    override fun configure(arg: RegistryWrapper.WrapperLookup?) {
        getOrCreateTagBuilder(FluidTags.WATER)
            .add(ModFluids.PURIFIED_WATER)
            .add(ModFluids.FLOWING_PURIFIED_WATER)
    }
}