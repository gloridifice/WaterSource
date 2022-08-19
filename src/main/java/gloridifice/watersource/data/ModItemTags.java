package gloridifice.watersource.data;

import gloridifice.watersource.WaterSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;


public class ModItemTags {
    public static final TagKey<Item> COCONUT_SOIL = ItemTags.create(new ResourceLocation(WaterSource.MODID,"coconut_soil"));
    public static final TagKey<Item> EVERLASTING_STRAINERS = ItemTags.create(new ResourceLocation(WaterSource.MODID,"everlasting_strainers"));
    public static final TagKey<Item> PALM_TREE_LOGS = ItemTags.create(new ResourceLocation(WaterSource.MODID, "palm_tree_logs"));
    public static final TagKey<Item> PURIFICATION_STRAINERS = ItemTags.create(new ResourceLocation(WaterSource.MODID, "purification_strainers"));
    public static final TagKey<Item> RAIN_COLLECTORS = ItemTags.create(new ResourceLocation(WaterSource.MODID, "rain_collectors"));
    public static final TagKey<Item> SOUL_STRAINERS = ItemTags.create(new ResourceLocation(WaterSource.MODID, "soul_strainers"));
    public static final TagKey<Item> STRAINERS = ItemTags.create(new ResourceLocation(WaterSource.MODID, "strainers"));
    public static final TagKey<Item> WATER_FILTERS = ItemTags.create(new ResourceLocation(WaterSource.MODID, "water_filters"));
    public static final TagKey<Item> PALM_ITEMS = ItemTags.create(new ResourceLocation(WaterSource.MODID, "palm_items"));
    public static final TagKey<Item> DRINKABLE_CONTAINERS = ItemTags.create(new ResourceLocation(WaterSource.MODID, "drinkable_containers"));

}
