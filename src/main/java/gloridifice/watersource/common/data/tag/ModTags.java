package gloridifice.watersource.common.data.tag;

import gloridifice.watersource.WaterSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.util.ResourceLocation;

public class ModTags {
    public static class Item{
        public final static INamedTag<net.minecraft.item.Item> COCONUT_LOGS = ItemTags.createOptional(new ResourceLocation(WaterSource.MODID,"coconut_tree_logs"));
        public final static INamedTag<net.minecraft.item.Item> STRAINERS = ItemTags.createOptional(new ResourceLocation(WaterSource.MODID,"strainers"));
        public final static INamedTag<net.minecraft.item.Item> PURIFICATION_STRAINERS = ItemTags.createOptional(new ResourceLocation(WaterSource.MODID,"purification_strainers"));
        public final static INamedTag<net.minecraft.item.Item> SOUL_STRAINERS = ItemTags.createOptional(new ResourceLocation(WaterSource.MODID,"soul_strainers"));
        public final static INamedTag<net.minecraft.item.Item> COCONUTS_SOIL = ItemTags.createOptional(new ResourceLocation(WaterSource.MODID,"coconuts_soil"));

    }
    public static class Block{
        public final static INamedTag<net.minecraft.block.Block> COCONUT_LOGS = BlockTags.createOptional(new ResourceLocation(WaterSource.MODID,"coconut_tree_logs"));
        public final static INamedTag<net.minecraft.block.Block> STRAINERS = BlockTags.createOptional(new ResourceLocation(WaterSource.MODID,"strainers"));
        public final static INamedTag<net.minecraft.block.Block> PURIFICATION_STRAINERS = BlockTags.createOptional(new ResourceLocation(WaterSource.MODID,"purification_strainers"));
        public final static INamedTag<net.minecraft.block.Block> SOUL_STRAINERS = BlockTags.createOptional(new ResourceLocation(WaterSource.MODID,"soul_strainers"));
        public final static INamedTag<net.minecraft.block.Block> COCONUTS_SOIL = BlockTags.createOptional(new ResourceLocation(WaterSource.MODID,"coconuts_soil"));
    }
    public static class Fluid{
        public final static INamedTag<net.minecraft.fluid.Fluid> DRINKS = FluidTags.createOptional(new ResourceLocation(WaterSource.MODID,"drink"));
    }
}
