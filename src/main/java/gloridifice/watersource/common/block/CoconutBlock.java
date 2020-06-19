package gloridifice.watersource.common.block;

import gloridifice.watersource.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;

import java.util.List;

public class CoconutBlock extends HorizontalBlock {
    public static final IntegerProperty AGE = IntegerProperty.create("age",0,3);
    public static final BooleanProperty IS_NATURAL = BooleanProperty.create("isnatural");
    public static final VoxelShape GROUND_SHAPE,NORTH_0_SHAPE,SOUTH_0_SHAPE,EAST_0_SHAPE,WEST_0_SHAPE,NORTH_1_SHAPE,SOUTH_1_SHAPE,EAST_1_SHAPE,WEST_1_SHAPE,NORTH_2_SHAPE,SOUTH_2_SHAPE,EAST_2_SHAPE,WEST_2_SHAPE;
    public CoconutBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(IS_NATURAL, false).with(AGE,0).with(HORIZONTAL_FACING, Direction.NORTH));
    }
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(AGE,HORIZONTAL_FACING,IS_NATURAL);
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return 1.0F;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (state.get(IS_NATURAL) && state.get(AGE) == 3){
            ItemStack itemStack = new ItemStack(BlockRegistry.itemCoconut,3);
            worldIn.setBlockState(pos,state.with(AGE,0));
            if (!player.inventory.addItemStackToInventory(itemStack)) {
                player.dropItem(itemStack, false);
            }
            return ActionResultType.SUCCESS;
        }else return ActionResultType.PASS;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(IS_NATURAL)){
            switch (state.get(AGE)){
                case 0:
                    switch (state.get(HORIZONTAL_FACING)){
                        case NORTH: return NORTH_0_SHAPE;
                        case SOUTH: return SOUTH_0_SHAPE;
                        case EAST: return EAST_0_SHAPE;
                        case WEST: return WEST_0_SHAPE;
                    }
                case 1:
                    switch (state.get(HORIZONTAL_FACING)){
                        case NORTH: return NORTH_1_SHAPE;
                        case SOUTH: return SOUTH_1_SHAPE;
                        case EAST: return EAST_1_SHAPE;
                        case WEST: return WEST_1_SHAPE;
                    }
                default:
                    switch (state.get(HORIZONTAL_FACING)){
                        case NORTH: return NORTH_2_SHAPE;
                        case SOUTH: return SOUTH_2_SHAPE;
                        case EAST: return EAST_2_SHAPE;
                        case WEST: return WEST_2_SHAPE;
                    }
            }
            return GROUND_SHAPE;
        }else return GROUND_SHAPE;
    }
    static {
        GROUND_SHAPE = Block.makeCuboidShape(5,0,5,11,6,11);
        NORTH_0_SHAPE = Block.makeCuboidShape(7,11,0,9,14,2);
        SOUTH_0_SHAPE = Block.makeCuboidShape(9,11,14,7,14,16);
        EAST_0_SHAPE = Block.makeCuboidShape(14,11,9,16,14,7);
        WEST_0_SHAPE = Block.makeCuboidShape(0,11,7,2,14,9);
        NORTH_1_SHAPE = Block.makeCuboidShape(6,9,0,10,14,3);
        SOUTH_1_SHAPE = Block.makeCuboidShape(6,9,13,10,14,16);
        EAST_1_SHAPE = Block.makeCuboidShape(13,9,6,16,14,10);
        WEST_1_SHAPE = Block.makeCuboidShape(0,9,6,3,14,10);

        NORTH_2_SHAPE = Block.makeCuboidShape(1,8,0,15,14,7);
        SOUTH_2_SHAPE = Block.makeCuboidShape(1,8,9,15,14,16);
        EAST_2_SHAPE = Block.makeCuboidShape(9,8,1,16,14,15);
        WEST_2_SHAPE = Block.makeCuboidShape(0,8,1,7,14,15);
    }
}
