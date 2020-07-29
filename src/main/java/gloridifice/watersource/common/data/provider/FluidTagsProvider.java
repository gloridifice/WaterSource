package gloridifice.watersource.common.data.provider;

import gloridifice.watersource.common.data.tag.ModTags;
import net.minecraft.data.DataGenerator;

public class FluidTagsProvider extends net.minecraft.data.FluidTagsProvider {
    public FluidTagsProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }
    @Override
    protected void registerTags() {
        getBuilder(ModTags.Fluid.COCONUT_LOG);
    }
}
