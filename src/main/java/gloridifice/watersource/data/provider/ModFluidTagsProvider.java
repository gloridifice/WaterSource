package gloridifice.watersource.data.provider;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.data.ModBlockTags;
import gloridifice.watersource.data.ModFluidTags;
import gloridifice.watersource.registry.FluidRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeRegistryTagsProvider;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.Nullable;

public class ModFluidTagsProvider extends ForgeRegistryTagsProvider<Fluid> {

    public ModFluidTagsProvider(DataGenerator generator, IForgeRegistry<Fluid> forgeRegistry, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, forgeRegistry, WaterSource.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(ModFluidTags.DRINK).add(Fluids.WATER, FluidRegistry.COCONUT_JUICE.get(), FluidRegistry.PURIFIED_WATER.get(), FluidRegistry.SOUL_WATER.get());
    }

    @Override
    public String getName() {
        return "watersource_fluid_tags_provider";
    }
}
