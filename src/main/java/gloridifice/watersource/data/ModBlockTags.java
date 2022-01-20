package gloridifice.watersource.data;

import gloridifice.watersource.WaterSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

public class ModBlockTags {
    
    public static final Tags.IOptionalNamedTag<Block> COCONUT_SOIL = BlockTags.createOptional(new ResourceLocation(WaterSource.MODID,"coconut_soil"));
    public static final Tags.IOptionalNamedTag<Block> EVERLASTING_STRAINERS = BlockTags.createOptional(new ResourceLocation(WaterSource.MODID,"everlasting_strainers"));
    public static final Tags.IOptionalNamedTag<Block> PALM_TREE_LOGS = BlockTags.createOptional(new ResourceLocation(WaterSource.MODID, "palm_tree_logs"));
    public static final Tags.IOptionalNamedTag<Block> PURIFICATION_STRAINERS = BlockTags.createOptional(new ResourceLocation(WaterSource.MODID, "purification_strainers"));
    public static final Tags.IOptionalNamedTag<Block> RAIN_COLLECTORS = BlockTags.createOptional(new ResourceLocation(WaterSource.MODID, "rain_collectors"));
    public static final Tags.IOptionalNamedTag<Block> SOUL_STRAINERS = BlockTags.createOptional(new ResourceLocation(WaterSource.MODID, "soul_strainers"));
    public static final Tags.IOptionalNamedTag<Block> STRAINERS = BlockTags.createOptional(new ResourceLocation(WaterSource.MODID, "strainers"));
    public static final Tags.IOptionalNamedTag<Block> WATER_FILTERS = BlockTags.createOptional(new ResourceLocation(WaterSource.MODID, "water_filters"));
    public static final Tags.IOptionalNamedTag<Block> PALM_BLOCKS = BlockTags.createOptional(new ResourceLocation(WaterSource.MODID, "palm_blocks"));

}
