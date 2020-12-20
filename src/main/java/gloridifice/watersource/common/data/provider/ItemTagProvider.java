package gloridifice.watersource.common.data.provider;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.data.tag.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeItemTagsProvider;

public class ItemTagProvider extends ForgeItemTagsProvider {
    public ItemTagProvider(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, helper);
    }

    @Override
    public void registerTags() {
        createBuilderIfAbsent(ModTags.Item.COCONUT_LOGS);
        createBuilderIfAbsent(ModTags.Item.STRAINERS);
        createBuilderIfAbsent(ModTags.Item.PURIFICATION_STRAINERS);
        createBuilderIfAbsent(ModTags.Item.SOUL_STRAINERS);
        createBuilderIfAbsent(ModTags.Item.COCONUTS_SOIL);
    }
}
