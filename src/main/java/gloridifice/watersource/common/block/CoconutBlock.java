package gloridifice.watersource.common.block;

import gloridifice.watersource.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Random;

public class CoconutBlock extends Block implements BonemealableBlock {
    public static final EnumProperty GROUND_TYPE = EnumProperty.create("groundtype", GroundType.class);
    public static final VoxelShape ORDINARY_SHAPE;
    public static final VoxelShape GROWING_SHAPE;

    public CoconutBlock(Properties properties) {
        super(properties);
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        GroundType type = GroundType.ORDINARY;
        Block block = context.getLevel().getBlockState(context.getClickedPos().below()).getBlock();
        if (context.getPlayer().getPose() != Pose.CROUCHING) {
            if (Blocks.SAND == block) {
                type = GroundType.SAND;
            }
            else if (Blocks.RED_SAND == block) {
                type = GroundType.RED_SAND;
            }
        }
        return defaultBlockState().setValue(GROUND_TYPE, type);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (pos.below().equals(fromPos)) {
            if (level.getBlockState(fromPos).getBlock() == Blocks.SAND) {
                level.setBlock(pos, this.defaultBlockState().setValue(GROUND_TYPE, GroundType.SAND), 3);
            }
            if (level.getBlockState(fromPos).getBlock() == Blocks.RED_SAND) {
                level.setBlock(pos, this.defaultBlockState().setValue(GROUND_TYPE, GroundType.RED_SAND), 3);
            }
            if (level.getBlockState(fromPos).isAir()) {
                level.setBlock(pos, this.defaultBlockState().setValue(GROUND_TYPE, GroundType.ORDINARY), 3);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(GROUND_TYPE);
    }

    static {
        ORDINARY_SHAPE = Block.box(5, 0, 5, 11, 6, 11);
        GROWING_SHAPE = Block.box(5, 0, 5, 11, 4, 11);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext collisionContext) {
        return state.getValue(GROUND_TYPE) == GroundType.ORDINARY ? ORDINARY_SHAPE : GROWING_SHAPE;
    }
    

    boolean canGrow(ServerLevel level, BlockPos pos, BlockState state, boolean isClient) {
        return state.getValue(GROUND_TYPE) != GroundType.ORDINARY;
    }

    
    public void grow(ServerLevel level, Random rand, BlockPos pos, BlockState state) {
        if (canGrow(level, pos, state, false))
            level.setBlock(pos, BlockRegistry.BLOCK_PALM_TREE_SAPLING.get().defaultBlockState(), 4);
    }

    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random rand) {
        super.tick(state, level, pos, rand);
        if (!level.isAreaLoaded(pos, 1))
            return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (level.getLightEmission(pos.above()) >= 9 && rand.nextInt(7) == 0) {
            grow(level, rand, pos, state);
        }
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter p_50897_, BlockPos p_50898_, BlockState p_50899_, boolean p_50900_) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level p_50901_, Random p_50902_, BlockPos p_50903_, BlockState p_50904_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, Random random, BlockPos blockPos, BlockState state) {
        if (random.nextDouble() <= 0.5) grow(level, random, blockPos, state);
    }


    public enum GroundType implements StringRepresentable {
        ORDINARY("ordinary"), SAND("sand"), RED_SAND("redsand");

        private final String name;

        GroundType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public String toString() {
            return this.getName();
        }

        public String getString() {
            return name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
