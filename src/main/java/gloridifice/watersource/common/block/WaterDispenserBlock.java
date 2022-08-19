package gloridifice.watersource.common.block;

import gloridifice.watersource.common.block.entity.WaterDispenserBlockEntity;
import gloridifice.watersource.common.block.entity.WaterFilterUpBlockEntity;
import gloridifice.watersource.helper.WaterLevelUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class WaterDispenserBlock extends BaseEntityBlock {
    public static final BooleanProperty IS_UP = BooleanProperty.create("up");
    public static final VoxelShape NORTH_SHAPE,SOUTH_SHAPE,WEST_SHAPE,EAST_SHAPE;
    static {
        NORTH_SHAPE = Block.box(2,0,0,14,16,16);
        SOUTH_SHAPE = Block.box(2,0,0,14,16,16);
        WEST_SHAPE = Block.box(0,0,2,16,16,14);
        EAST_SHAPE = Block.box(0,0,2,16,16,14);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext collisionContext) {
        switch (state.getValue(HORIZONTAL_FACING)){
            case EAST : return EAST_SHAPE;
            case NORTH : return NORTH_SHAPE;
            case SOUTH : return SOUTH_SHAPE;
            case WEST:  return WEST_SHAPE;
            default: return null;
        }

    }

    public WaterDispenserBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(IS_UP, false).setValue(HORIZONTAL_FACING, Direction.NORTH));
    }
    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        BlockEntity tile = level.getBlockEntity(pos);
        if (tile instanceof WaterFilterUpBlockEntity) {
            ItemStack stack = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(data -> data.getStackInSlot(0)).orElse(ItemStack.EMPTY);
            if (!stack.isEmpty()) {
                popResource(level, pos, stack);
            }
        }
        if (state.getValue(IS_UP)) {
            level.setBlock(pos.below(), Blocks.AIR.defaultBlockState(), 4);
        } else level.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 4);
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @javax.annotation.Nullable LivingEntity livingEntity, ItemStack itemStack) {
        level.setBlock(pos.above(), state.setValue(IS_UP, true), 3);
    }

    @javax.annotation.Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (context.getLevel().getBlockState(context.getClickedPos().above()).isAir() && context.getClickedPos().above().getY() < 255) {
            return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection());
        } else return null;
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(IS_UP, HORIZONTAL_FACING);
    }
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        boolean flag = true;
        BlockPos blockEntityPos = state.getValue(IS_UP) ? pos.below() : pos;
        if (level.getBlockEntity(blockEntityPos) instanceof WaterDispenserBlockEntity blockEntity) {
            ItemStack handItem = player.getItemInHand(interactionHand);
            IFluidHandler fluidHandler = blockEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).orElse(null);
            FluidStack fluidStack = fluidHandler.getFluidInTank(0);
            if (handItem.isEmpty()){
                if (player.getPose() == Pose.CROUCHING){
                    fluidHandler.drain(fluidHandler.getTankCapacity(0), IFluidHandler.FluidAction.EXECUTE);
                    player.playSound(SoundEvents.BUCKET_EMPTY, 0.6F, 1.0F);
                }else {
                    if (fluidStack.getAmount() >= 250 && WaterLevelUtil.canPlayerAddWaterExhaustionLevel(player)){
                        WaterLevelUtil.drink(player, fluidStack.getFluid());
                        fluidHandler.drain(250, IFluidHandler.FluidAction.EXECUTE);
                    }else flag = false;
                }
            }else {
                flag &= FluidUtil.interactWithFluidHandler(player, interactionHand, fluidHandler);
            }
        } else flag = false;
        return flag ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(IS_UP) ? null : new WaterDispenserBlockEntity(pos, state);
    }
}
