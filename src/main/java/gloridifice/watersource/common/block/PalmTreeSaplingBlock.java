package gloridifice.watersource.common.block;

import gloridifice.watersource.data.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class PalmTreeSaplingBlock extends SaplingBlock {
    public PalmTreeSaplingBlock(AbstractTreeGrower treeIn, Properties properties) {
        super(treeIn, properties);
        registerDefaultState(defaultBlockState().setValue(STAGE, 0));
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return state.is(ModBlockTags.COCONUT_SOIL);
    }

    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.BEACH;
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return mayPlaceOn(state, world, pos);
    }

    @Override
    public boolean isValidSpawn(BlockState state, BlockGetter level, BlockPos pos, SpawnPlacements.Type type, EntityType<?> entityType) {
        return state.is(ModBlockTags.COCONUT_SOIL);
    }

}
