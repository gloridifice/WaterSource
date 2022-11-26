package gloridifice.watersource.data;

import gloridifice.watersource.WaterSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
   public static final TagKey<Item> COCONUT_SOIL=
           register("coconut_soil");
    public static final TagKey<Item> EVERLASTING_STRAINERS=
            register("everlasting_strainers");
    public static final TagKey<Item> PALM_TREE_LOGS=
            register("palm_tree_logs");
    public static final TagKey<Item> PURIFICATION_STRAINERS=
            register("purification_strainers");
    public static final TagKey<Item> RAIN_COLLECTORS=
            register("rain_collectors");
    public static final TagKey<Item> SOUL_STRAINERS=
            register("soul_strainers");
    public static final TagKey<Item> STRAINERS=
            register("strainers");
    public static final TagKey<Item> WATER_FILTERS=
            register("water_filters");

    private static TagKey<Item> register(String name)
    {
        return ItemTags.create(new ResourceLocation(WaterSource.MODID, name));
    }

}
