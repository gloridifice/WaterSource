package gloridifice.watersource.data.provider;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.data.ModBlockTags;
import gloridifice.watersource.registry.BlockRegistry;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, WaterSource.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(ModBlockTags.COCONUT_SOIL).add(Blocks.SAND, Blocks.RED_SAND);
        this.tag(ModBlockTags.EVERLASTING_STRAINERS).add(BlockRegistry.EVERLASTING_SOUL_STRAINER, BlockRegistry.EVERLASTING_STRAINER);
        this.tag(ModBlockTags.PALM_TREE_LOGS).add(BlockRegistry.PALM_TREE_LOG);
        this.tag(ModBlockTags.PURIFICATION_STRAINERS).add(BlockRegistry.PRIMITIVE_STRAINER, BlockRegistry.PAPER_STRAINER);
        this.tag(ModBlockTags.SOUL_STRAINERS).add(BlockRegistry.SOUL_STRAINER, BlockRegistry.PAPER_SOUL_STRAINER);
        this.tag(ModBlockTags.STRAINERS).addTags(ModBlockTags.SOUL_STRAINERS, ModBlockTags.SOUL_STRAINERS, ModBlockTags.EVERLASTING_STRAINERS);
        this.tag(ModBlockTags.WATER_FILTERS).add(BlockRegistry.WOODEN_WATER_FILTER, BlockRegistry.IRON_WATER_FILTER);
        this.tag(ModBlockTags.RAIN_COLLECTORS).add(BlockRegistry.STONE_RAIN_COLLECTOR);
        this.tag(ModBlockTags.PALM_BLOCKS).add(BlockRegistry.WOODEN_WATER_FILTER, BlockRegistry.BLOCK_COCONUT, BlockRegistry.PALM_TREE_LOG, BlockRegistry.PALM_TREE_BUTTON, BlockRegistry.PALM_TREE_DOOR, BlockRegistry.PALM_TREE_FENCE, BlockRegistry.PALM_TREE_HEAD, BlockRegistry.PALM_TREE_FENCE_GATE, BlockRegistry.PALM_TREE_PLANKS, BlockRegistry.PALM_TREE_PRESSURE_PLATE, BlockRegistry.PALM_TREE_STAIRS, BlockRegistry.PALM_TREE_TRAPDOOR, BlockRegistry.PALM_TREE_SLAB, BlockRegistry.STRIPPED_PALM_TREE_LOG);


        this.tag(BlockTags.MINEABLE_WITH_AXE).addTag(ModBlockTags.PALM_BLOCKS);
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockRegistry.STONE_RAIN_COLLECTOR, BlockRegistry.IRON_WATER_FILTER);
        this.tag(BlockTags.LEAVES).add(BlockRegistry.PALM_TREE_LEAF);
        this.tag(BlockTags.PLANKS).add(BlockRegistry.PALM_TREE_PLANKS);
        this.tag(BlockTags.LOGS_THAT_BURN).add(BlockRegistry.PALM_TREE_LOG);
        this.tag(BlockTags.WOODEN_STAIRS).add(BlockRegistry.PALM_TREE_STAIRS);
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(BlockRegistry.PALM_TREE_TRAPDOOR);
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(BlockRegistry.PALM_TREE_PRESSURE_PLATE);
        this.tag(BlockTags.WOODEN_DOORS).add(BlockRegistry.PALM_TREE_DOOR);
        this.tag(BlockTags.WOODEN_FENCES).add(BlockRegistry.PALM_TREE_FENCE);
        this.tag(BlockTags.FENCE_GATES).add(BlockRegistry.PALM_TREE_FENCE_GATE);
        this.tag(BlockTags.WOODEN_SLABS).add(BlockRegistry.PALM_TREE_SLAB);
    }


    @Override
    public String getName() {
        return "Water Source Block Tags";
    }
}
