package gloridifice.watersource.common.data.tag;

import gloridifice.watersource.WaterSource;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class ModTags {
    public static class Item{
        public final static Tag<net.minecraft.item.Item> COCONUT_LOGS = new ItemTags.Wrapper(new ResourceLocation(WaterSource.MODID,"coconut_tree_logs"));
        public final static Tag<net.minecraft.item.Item> STRAINERS = new ItemTags.Wrapper(new ResourceLocation(WaterSource.MODID,"strainers"));
        public final static Tag<net.minecraft.item.Item> PURIFICATION_STRAINERS = new ItemTags.Wrapper(new ResourceLocation(WaterSource.MODID,"purification_strainers"));
        public final static Tag<net.minecraft.item.Item> SOUL_STRAINERS = new ItemTags.Wrapper(new ResourceLocation(WaterSource.MODID,"soul_strainers"));
        public final static Tag<net.minecraft.item.Item> COCONUTS_SOIL = new ItemTags.Wrapper(new ResourceLocation(WaterSource.MODID,"coconuts_soil"));

    }
    public static class Block{
        public final static Tag<net.minecraft.block.Block> COCONUT_LOGS = new BlockTags.Wrapper(new ResourceLocation(WaterSource.MODID,"coconut_tree_logs"));
        public final static Tag<net.minecraft.block.Block> STRAINERS = new BlockTags.Wrapper(new ResourceLocation(WaterSource.MODID,"strainers"));
        public final static Tag<net.minecraft.block.Block> PURIFICATION_STRAINERS = new BlockTags.Wrapper(new ResourceLocation(WaterSource.MODID,"purification_strainers"));
        public final static Tag<net.minecraft.block.Block> SOUL_STRAINERS = new BlockTags.Wrapper(new ResourceLocation(WaterSource.MODID,"soul_strainers"));
        public final static Tag<net.minecraft.block.Block> COCONUTS_SOIL = new BlockTags.Wrapper(new ResourceLocation(WaterSource.MODID,"coconuts_soil"));
    }
    public static class Fluid{
        public final static Tag<net.minecraft.fluid.Fluid> DRINKS = new FluidTags.Wrapper(new ResourceLocation(WaterSource.MODID,"drink"));
    }
}
