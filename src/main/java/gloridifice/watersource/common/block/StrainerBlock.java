package gloridifice.watersource.common.block;

import gloridifice.watersource.common.block.entity.StrainerBlockEntity;
import gloridifice.watersource.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.Random;

public class StrainerBlock extends BaseEntityBlock{
    public static final VoxelShape STRAINER_SHAPE;
    static {
        STRAINER_SHAPE = Block.box(4, 0, 4, 12, 4, 12);
    }


    public StrainerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof StrainerBlockEntity && !level.isClientSide()) {
            blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(data -> {
                popResource(level, pos, data.getStackInSlot(0));
            });
        }
        super.playerWillDestroy(level, pos, state, player);
    }
    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return STRAINER_SHAPE;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity livingEntity, ItemStack stack) {
        BlockEntity tile = level.getBlockEntity(pos);
        if (tile instanceof StrainerBlockEntity && !level.isClientSide()) {
            tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(data -> {
                data.insertItem(0, stack, false);
                System.out.println("place: " + data.getStackInSlot(0));
            });
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState state1, boolean b) {
        BlockEntity tile = level.getBlockEntity(pos);
        if (tile instanceof StrainerBlockEntity && !level.isClientSide()) {
            tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(data -> {
                System.out.println("place2: " + data.getStackInSlot(0));
            });
        }
        super.onPlace(state, level, pos, state1, b);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (!level.isClientSide())
            level.getBlockEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(data -> {
                popResource(level, pos, data.getStackInSlot(0));
            });
        return InteractionResult.SUCCESS;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        ItemStack itemStack = getCloneItemStack(world, pos, state);
        if (world.getBlockEntity(pos) instanceof StrainerBlockEntity) {
            BlockEntity tile = world.getBlockEntity(pos);
            itemStack = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null).getStackInSlot(0);
        }
        return itemStack;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StrainerBlockEntity(pos, state);
    }


}
