package gloridifice.watersource.common.block;

import gloridifice.watersource.data.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

public class PalmTreeSaplingBlock extends SaplingBlock {
    public PalmTreeSaplingBlock(AbstractTreeGrower treeIn, Properties properties) {
        super(treeIn, properties);
        registerDefaultState(defaultBlockState().setValue(STAGE, 0));
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        ITag<Block> tag = ForgeRegistries.BLOCKS.tags().getTag(ModBlockTags.COCONUT_SOIL);
        return tag.contains(state.getBlock());
    }

    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.BEACH;
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return mayPlaceOn(state, world, pos);
    }
}
