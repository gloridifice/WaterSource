package gloridifice.watersource.common.data.provider;

import gloridifice.watersource.common.data.tag.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;

public class BlockTagProvider extends ForgeBlockTagsProvider {
    
    public BlockTagProvider(DataGenerator gen, ExistingFileHelper fileHelper) {
        super(gen, fileHelper);
    }

    @Override
    public void registerTags() {
        createBuilderIfAbsent(ModTags.Block.COCONUT_LOGS);
        createBuilderIfAbsent(ModTags.Block.COCONUT_LOGS);
        createBuilderIfAbsent(ModTags.Block.STRAINERS);
        createBuilderIfAbsent(ModTags.Block.PURIFICATION_STRAINERS);
        createBuilderIfAbsent(ModTags.Block.SOUL_STRAINERS);
        createBuilderIfAbsent(ModTags.Block.COCONUTS_SOIL);
    }
}
