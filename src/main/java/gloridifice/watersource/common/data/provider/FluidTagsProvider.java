package gloridifice.watersource.common.data.provider;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.data.tag.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FluidTagsProvider extends net.minecraft.data.FluidTagsProvider {
    public FluidTagsProvider(DataGenerator generatorIn, ExistingFileHelper helper) {
        super(generatorIn, WaterSource.MODID, helper);
    }
    @Override
    protected void registerTags() {
        createBuilderIfAbsent(ModTags.Fluid.DRINKS);
    }
}
