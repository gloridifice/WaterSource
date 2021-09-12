package gloridifice.watersource.common.block;

import gloridifice.watersource.WaterSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class CoconutSaplingBlock extends SaplingBlock {
    public CoconutSaplingBlock(AbstractTreeGrower treeIn, Properties properties) {
        super(treeIn, properties);
        registerDefaultState(defaultBlockState().setValue(STAGE, 0));
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        Tag<Block> tag = BlockTags.getAllTags().getTag(new ResourceLocation(WaterSource.MODID, "coconut_soil"));
        return tag.getValues().contains(state.getBlock());
    }

    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.BEACH;
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return mayPlaceOn(state, world, pos);
    }
    /*    protected boolean isValidGround(BlockState state, IBlockReader level, BlockPos pos) {
        Block block = state.getValueBlock();
        return BlockTags.getAllTags().getTag(new ResourceLocation(WaterSource.MODID, "coconuts_soil")).contains(block);
    }*/
}
