package gloridifice.watersource.common.data.provider;

import gloridifice.watersource.common.data.tag.ModTags;
import gloridifice.watersource.registry.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ForgeItemTagsProvider;



public class ItemTagProvider extends ForgeItemTagsProvider {
    public ItemTagProvider(DataGenerator gen) {
        super(gen);
    }
    @Override
    public void registerTags() {
        getBuilder(ModTags.Item.COCONUT_LOG);
        getBuilder(ModTags.Item.STRAINER);
        getBuilder(ModTags.Item.PURIFICATION_STRAINER);
        getBuilder(ModTags.Item.SOUL_STRAINER);
        getBuilder(ModTags.Item.COCONUTS_SOIL);
    }
}
