package gloridifice.watersource.common.block;

import gloridifice.watersource.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class CoconutBlock extends Block implements IGrowable {
    public static final EnumProperty GROUND_TYPE = EnumProperty.create("groundtype",GroundType.class);
    public static final VoxelShape ORDINARY_SHAPE;
    public static final VoxelShape GROWING_SHAPE;

    public CoconutBlock(Properties properties,String name) {
        super(properties);
        this.setRegistryName(name);
        this.setDefaultState(this.getStateContainer().getBaseState().with(GROUND_TYPE, GroundType.ORDINARY));
    }
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        GroundType type = GroundType.ORDINARY;
        Block block = context.getWorld().getBlockState(context.getPos().down()).getBlock();
        if (!context.getPlayer().isSneaking()){
            if (Blocks.SAND == block) {
                type = GroundType.SAND;
            }else if (Blocks.RED_SAND == block){
                type = GroundType.RED_SAND;
            }
        }
        return getDefaultState().with(GROUND_TYPE, type);
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (pos.down().equals(fromPos)){
            if (worldIn.getBlockState(fromPos).getBlock() == Blocks.SAND){
                worldIn.setBlockState(pos,this.getDefaultState().with(GROUND_TYPE,GroundType.SAND));
            }
            if (worldIn.getBlockState(fromPos).getBlock() == Blocks.RED_SAND){
                worldIn.setBlockState(pos,this.getDefaultState().with(GROUND_TYPE,GroundType.RED_SAND));
            }
            if (worldIn.getBlockState(fromPos).isAir()){
                worldIn.setBlockState(pos,this.getDefaultState().with(GROUND_TYPE,GroundType.ORDINARY));
            }
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(GROUND_TYPE);
    }

    static {
        ORDINARY_SHAPE = Block.makeCuboidShape(5,0,5,11,6,11);
        GROWING_SHAPE = Block.makeCuboidShape(5,0,5,11,4,11);
    }
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.get(GROUND_TYPE) == GroundType.ORDINARY ? ORDINARY_SHAPE : GROWING_SHAPE;
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(GROUND_TYPE) != GroundType.ORDINARY;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return (double)worldIn.rand.nextFloat() < 0.7D;
    }

    @Override
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        if (canGrow(worldIn,pos,state,false)) worldIn.setBlockState(pos, BlockRegistry.BLOCK_COCONUT_SAPLING.getDefaultState());
    }

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        super.tick(state, worldIn, pos, rand);
        if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (worldIn.getLight(pos.up()) >= 9 && rand.nextInt(7) == 0) {
            grow(worldIn,rand,pos,state);
        }
    }

    public enum GroundType implements IStringSerializable {
        ORDINARY("ordinary"),
        SAND("sand"),
        RED_SAND("redsand");


        private final String name;
        GroundType(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return this.getName();
        }

        @Override
        public String getString() {
            return name;
        }
    }
}
