package gloridifice.watersource.common.block;

import com.google.common.collect.ImmutableMap;
import gloridifice.watersource.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class NaturalCoconutBlock extends HorizontalDirectionalBlock implements IPlantable, BonemealableBlock {
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);
    public static final VoxelShape NORTH_0_SHAPE, SOUTH_0_SHAPE, EAST_0_SHAPE, WEST_0_SHAPE, NORTH_1_SHAPE, SOUTH_1_SHAPE, EAST_1_SHAPE, WEST_1_SHAPE, NORTH_2_SHAPE, SOUTH_2_SHAPE, EAST_2_SHAPE, WEST_2_SHAPE;

    public NaturalCoconutBlock(BlockBehaviour.Properties properties) {
        super(properties);

    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, HORIZONTAL_FACING);
    }


    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        if (state.getValue(AGE) == 3) {
            ItemStack itemStack = new ItemStack(BlockRegistry.ITEM_COCONUT.get(), 3);
            level.setBlock(pos, state.setValue(AGE, 0), 3);
            if (!player.getInventory().add(itemStack)) {
                player.drop(itemStack, false);
            }
            return InteractionResult.SUCCESS;
        } else return InteractionResult.PASS;
    }


    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos neighbor, boolean bool) {
        if (pos.offset(state.getValue(HORIZONTAL_FACING).getNormal()).equals(neighbor)) {
            dropResources(state, level, pos);
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        }
    }


    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> list = new ArrayList<>();
        if (state.getValue(AGE) == 3) {
            list.add(new ItemStack(BlockRegistry.ITEM_COCONUT.get(), 3));
        }
        return list;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext collisionContext) {
        switch (state.getValue(AGE)) {
            case 0:
                switch (state.getValue(HORIZONTAL_FACING)) {
                    case NORTH:
                        return NORTH_0_SHAPE;
                    case SOUTH:
                        return SOUTH_0_SHAPE;
                    case EAST:
                        return EAST_0_SHAPE;
                    case WEST:
                        return WEST_0_SHAPE;
                }
            case 1:
                switch (state.getValue(HORIZONTAL_FACING)) {
                    case NORTH:
                        return NORTH_1_SHAPE;
                    case SOUTH:
                        return SOUTH_1_SHAPE;
                    case EAST:
                        return EAST_1_SHAPE;
                    case WEST:
                        return WEST_1_SHAPE;
                }
            default:
                switch (state.getValue(HORIZONTAL_FACING)) {
                    case NORTH:
                        return NORTH_2_SHAPE;
                    case SOUTH:
                        return SOUTH_2_SHAPE;
                    case EAST:
                        return EAST_2_SHAPE;
                    case WEST:
                        return WEST_2_SHAPE;
                }
        }
        return NORTH_0_SHAPE;
    }


    static {

        NORTH_0_SHAPE = Block.box(7, 11, 0, 9, 14, 2);
        SOUTH_0_SHAPE = Block.box(7, 11, 14, 9, 14, 16);
        EAST_0_SHAPE = Block.box(14, 11, 7, 16, 14, 9);
        WEST_0_SHAPE = Block.box(0, 11, 7, 2, 14, 9);
        NORTH_1_SHAPE = Block.box(6, 9, 0, 10, 14, 3);
        SOUTH_1_SHAPE = Block.box(6, 9, 13, 10, 14, 16);
        EAST_1_SHAPE = Block.box(13, 9, 6, 16, 14, 10);
        WEST_1_SHAPE = Block.box(0, 9, 6, 3, 14, 10);

        NORTH_2_SHAPE = Block.box(1, 8, 0, 15, 14, 7);
        SOUTH_2_SHAPE = Block.box(1, 8, 9, 15, 14, 16);
        EAST_2_SHAPE = Block.box(9, 8, 1, 16, 14, 15);
        WEST_2_SHAPE = Block.box(0, 8, 1, 7, 14, 15);
    }


    public void grow(ServerLevel level, Random rand, BlockPos pos, BlockState state) {
        if (state.getValue(AGE) < 3)
            level.setBlock(pos, state.setValue(AGE, Math.max(level.getBlockState(pos).getValue(AGE) + 1, 3)), 3);

    }

    @Override
    public void tick(BlockState state, ServerLevel serverLevel, BlockPos pos, Random random) {
        if (!serverLevel.isAreaLoaded(pos, 1))
            return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(serverLevel, pos, state, random.nextInt((int) (25.0F) + 1) == 0)) {
            grow(serverLevel, random, pos, state);
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(serverLevel, pos, state);
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
    public void performBonemeal(ServerLevel serverLevel, Random random, BlockPos pos, BlockState state) {
        serverLevel.setBlock(pos, state.setValue(AGE, Math.min(serverLevel.getBlockState(pos).getValue(AGE) + 1, 3)), 3);
    }

    @Override
    public BlockState getPlant(BlockGetter world, BlockPos pos) {
        return this.defaultBlockState();
    }
}
