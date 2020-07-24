package gloridifice.watersource.common.data.provider;

import gloridifice.watersource.common.data.tag.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;

public class BlockTagProvider extends ForgeBlockTagsProvider {
    public BlockTagProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    public void registerTags() {
        getBuilder(ModTags.Block.COCONUT_LOG);
    }
}
