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
        public final static Tag<net.minecraft.item.Item> COCONUT_LOG = new ItemTags.Wrapper(new ResourceLocation(WaterSource.MODID,"coconut_log"));
        public final static Tag<net.minecraft.item.Item> STRAINER = new ItemTags.Wrapper(new ResourceLocation(WaterSource.MODID,"strainer"));
        public final static Tag<net.minecraft.item.Item> PURIFICATION_STRAINER = new ItemTags.Wrapper(new ResourceLocation(WaterSource.MODID,"purification_strainer"));
        public final static Tag<net.minecraft.item.Item> SOUL_STRAINER = new ItemTags.Wrapper(new ResourceLocation(WaterSource.MODID,"soul_strainer"));
        public final static Tag<net.minecraft.item.Item> COCONUTS_SOIL = new ItemTags.Wrapper(new ResourceLocation(WaterSource.MODID,"coconuts_soil"));

    }
    public static class Block{
        public final static Tag<net.minecraft.block.Block> COCONUT_LOG = new BlockTags.Wrapper(new ResourceLocation(WaterSource.MODID,"coconut_log"));
        public final static Tag<net.minecraft.block.Block> STRAINER = new BlockTags.Wrapper(new ResourceLocation(WaterSource.MODID,"strainer"));
        public final static Tag<net.minecraft.block.Block> PURIFICATION_STRAINER = new BlockTags.Wrapper(new ResourceLocation(WaterSource.MODID,"purification_strainer"));
        public final static Tag<net.minecraft.block.Block> SOUL_STRAINER = new BlockTags.Wrapper(new ResourceLocation(WaterSource.MODID,"soul_strainer"));
        public final static Tag<net.minecraft.block.Block> COCONUTS_SOIL = new BlockTags.Wrapper(new ResourceLocation(WaterSource.MODID,"coconuts_soil"));
    }
    public static class Fluid{
        public final static Tag<net.minecraft.fluid.Fluid> COCONUT_LOG = new FluidTags.Wrapper(new ResourceLocation(WaterSource.MODID,"drink"));
    }
}
