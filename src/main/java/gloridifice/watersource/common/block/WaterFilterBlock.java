package gloridifice.watersource.common.block;


import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.block.entity.WaterFilterDownBlockEntity;
import gloridifice.watersource.common.block.entity.WaterFilterUpBlockEntity;
import gloridifice.watersource.helper.FluidHelper;
import gloridifice.watersource.registry.BlockEntityRegistry;
import gloridifice.watersource.registry.FluidRegistry;
import gloridifice.watersource.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class WaterFilterBlock extends BaseEntityBlock {
    public static final BooleanProperty IS_UP = BooleanProperty.create("up");

    public WaterFilterBlock(String name, Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(IS_UP, false));
        this.setRegistryName(name);
    }



    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(IS_UP);
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
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        level.setBlock(pos.above(), state.setValue(IS_UP, true), 3);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (context.getLevel().getBlockState(context.getClickedPos().above()).isAir() && context.getClickedPos().above().getY() < 255) {
            return this.defaultBlockState();
        } else return null;
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(IS_UP) ? BlockEntityRegistry.WATER_FILTER_UP_TILE.get().create(pos, state) : BlockEntityRegistry.WATER_FILTER_DOWN_TILE.get().create(pos, state);
    }


    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        boolean flag = false;
        if (state.getValue(IS_UP)) {
            WaterFilterUpBlockEntity tile = (WaterFilterUpBlockEntity) level.getBlockEntity(pos);
            FluidTank fluidTankUp = tile.getUpTank().orElse(null);
            ItemStackHandler strainerHandler = tile.getStrainer().orElse(null);
            ItemStackHandler propsHandler = tile.getProps().orElse(null);
            ItemStack strainerStack = strainerHandler.getStackInSlot(0);
            ItemStack propsStack = propsHandler.getStackInSlot(0);
            ItemStack heldItem = player.getItemInHand(interactionHand);
            if (!heldItem.isEmpty()) {
                //interact with fluid handler
                if (!strainerStack.isEmpty()) {
                    flag = FluidUtil.interactWithFluidHandler(player, interactionHand, fluidTankUp);
                }
                if (ItemTags.getAllTags().getTag(new ResourceLocation(WaterSource.MODID, "strainers")).contains(heldItem.getItem())) {
                    if (strainerStack.isEmpty()) {
                        //install strainer
                        strainerHandler.insertItem(0, heldItem.copy(), true);
                        if (!player.isCreative()) {
                            ItemStack itemStack1 = heldItem.copy();
                            itemStack1.setCount(heldItem.getCount() - 1);
                            player.setItemInHand(interactionHand, itemStack1);
                        }
                    } else {
                        //replace strainer
                        ItemStack heldItem1 = heldItem.copy();
                        player.setItemInHand(interactionHand, strainerStack);
                        strainerHandler.setStackInSlot(0, heldItem1);
                    }
                    flag = true;
                }
                if (heldItem.getItem() == Items.HEART_OF_THE_SEA) player.setItemInHand(interactionHand, propsHandler.insertItem(0, heldItem.copy(), false));
            } else if (player.getPose() == Pose.CROUCHING) {
                if (fluidTankUp.getFluid().isEmpty()) {
                    //take out strainer
                    if (!strainerStack.isEmpty()) {
                        if (!player.getInventory().add(strainerStack)) {
                            player.drop(strainerStack, false);
                        }
                        strainerHandler.setStackInSlot(0, ItemStack.EMPTY);
                    } else if (!propsStack.isEmpty()) {
                        if (!player.getInventory().add(propsStack)) {
                            player.drop(propsStack, false);
                        }
                        propsHandler.setStackInSlot(0, ItemStack.EMPTY);
                    }
                } else {
                    //clear fluid
                    fluidTankUp.setFluid(FluidStack.EMPTY);
                    player.playSound(SoundEvents.BUCKET_EMPTY, 0.6F, 1.0F);
                }
                flag = true;
            }
        } else {//down
            WaterFilterDownBlockEntity tile = (WaterFilterDownBlockEntity) level.getBlockEntity(pos);
            FluidTank fluidTankDown = tile.getDownTank().orElse(null);
            ItemStack heldItem = player.getItemInHand(interactionHand);
            IItemHandler playInventoryHandler = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
            if (!heldItem.isEmpty()) {
                FluidActionResult fluidActionResult = FluidUtil.tryFillContainerAndStow(heldItem, fluidTankDown, playInventoryHandler, Integer.MAX_VALUE, player, true);
                if (fluidActionResult.isSuccess()) {
                    player.setItemInHand(interactionHand, fluidActionResult.getResult());
                    flag = true;
                }
                if (heldItem.getItem() == Items.GLASS_BOTTLE) {
                    FluidStack downFluidStack = fluidTankDown.getFluid();
                    if (!downFluidStack.isEmpty() && (downFluidStack.getFluid() == FluidRegistry.PURIFIED_WATER.get() || downFluidStack.getFluid() == FluidRegistry.SOUL_WATER.get())) {
                        //fill bottle
                        ItemStack filledItem = this.getFilledBottleItem(fluidTankDown);
                        if (!filledItem.isEmpty()){
                            flag = true;
                            fluidTankDown.drain(250, IFluidHandler.FluidAction.EXECUTE);
                            if (player.getInventory().add(filledItem)) {
                                player.drop(filledItem, false);
                            }
                            if (!player.isCreative()) heldItem.setCount(heldItem.getCount() - 1);
                            player.playSound(SoundEvents.BUCKET_FILL, 0.8F, 1.0F);
                        }
                    }
                }
            } else if (player.getPose() == Pose.CROUCHING && !fluidTankDown.getFluid().isEmpty()) {
                //clear fluid
                fluidTankDown.setFluid(FluidStack.EMPTY);
                player.playSound(SoundEvents.BUCKET_EMPTY, 0.6F, 1.0F);
                flag = true;
            }
        }
        return flag ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }


    public ItemStack getFilledBottleItem(IFluidHandler fluidHandler) {
        ItemStack filledItem = ItemStack.EMPTY;
        FluidStack fluidStack = fluidHandler.getFluidInTank(0);
        if (fluidStack.getFluid() == FluidRegistry.PURIFIED_WATER.get()) {
            filledItem = FluidHelper.fillContainer(new ItemStack(ItemRegistry.FLUID_BOTTLE), FluidRegistry.PURIFIED_WATER.get());
        } else if (fluidStack.getFluid() == FluidRegistry.SOUL_WATER.get()) {
            filledItem = FluidHelper.fillContainer(new ItemStack(ItemRegistry.FLUID_BOTTLE), FluidRegistry.SOUL_WATER.get());
        }
        if (!filledItem.isEmpty() && fluidStack.getAmount() >= 250) {
            return filledItem;
        }
        return ItemStack.EMPTY;
    }

    @Nullable
    @Override
    public <T extends
            BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide && !state.getValue(IS_UP) ? null : createTickerHelper(blockEntityType, BlockEntityRegistry.WATER_FILTER_UP_TILE.get(), WaterFilterUpBlockEntity::serveTick);
    }
}
