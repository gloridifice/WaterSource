package gloridifice.watersource.data;

import gloridifice.watersource.WaterSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {
    
    public static final TagKey<Block> COCONUT_SOIL =
            register("coconut_soil");
    public static final TagKey<Block> EVERLASTING_STRAINERS =
            register("everlasting_strainers");
    public static final TagKey<Block> PALM_TREE_LOGS =
            register("palm_tree_logs");
    public static final TagKey<Block> PURIFICATION_STRAINERS =
            register("purification_strainers");
    public static final TagKey<Block> RAIN_COLLECTORS =
            register("rain_collectors");
    public static final TagKey<Block> SOUL_STRAINERS =
            register( "soul_strainers");
    public static final TagKey<Block> STRAINERS =
            register("strainers");
    public static final TagKey<Block> WATER_FILTERS =
            register("water_filters");
    public static final TagKey<Block> PALM_BLOCKS =
            register("palm_blocks");

    private static TagKey<Block> register(String name)
    {
        return BlockTags.create(new ResourceLocation(WaterSource.MODID, name));
    }
}
