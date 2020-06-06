package gloridifice.watersource.common.data.tag;

import gloridifice.watersource.WaterSource;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class ModTags {
    public static class Item{
        public final static Tag<net.minecraft.item.Item> STRAINER = new ItemTags.Wrapper(new ResourceLocation(WaterSource.MODID,"strainer"));
        public final static Tag<net.minecraft.item.Item> PURIFICATION_STRAINER = new ItemTags.Wrapper(new ResourceLocation(WaterSource.MODID,"purification_strainer"));
        public final static Tag<net.minecraft.item.Item> SOUL_STRAINER = new ItemTags.Wrapper(new ResourceLocation(WaterSource.MODID,"soul_strainer"));
    }
}
