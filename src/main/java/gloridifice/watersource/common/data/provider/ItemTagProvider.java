package gloridifice.watersource.common.data.provider;

import gloridifice.watersource.common.data.tag.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ForgeItemTagsProvider;



public class ItemTagProvider extends ForgeItemTagsProvider {
    public ItemTagProvider(DataGenerator gen) {
        super(gen);
    }
    @Override
    public void registerTags() {
        getBuilder(ModTags.Item.COCONUT_LOGS);
        getBuilder(ModTags.Item.STRAINERS);
        getBuilder(ModTags.Item.PURIFICATION_STRAINERS);
        getBuilder(ModTags.Item.SOUL_STRAINERS);
        getBuilder(ModTags.Item.COCONUTS_SOIL);
    }
}
