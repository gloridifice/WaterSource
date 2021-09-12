package gloridifice.watersource.common.block;


import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.block.entity.RainCollectorBlockEntity;
import gloridifice.watersource.common.block.entity.WaterFilterDownBlockEntity;
import gloridifice.watersource.common.block.entity.WaterFilterUpBlockEntity;
import gloridifice.watersource.common.network.SimpleNetworkHandler;
import gloridifice.watersource.common.network.WaterFilterMessage;
import gloridifice.watersource.registry.BlockEntityTypesRegistry;
import gloridifice.watersource.registry.FluidRegistry;
import gloridifice.watersource.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
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
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

public class WaterFilterBlock extends BaseEntityBlock {
    public static final BooleanProperty IS_UP = BooleanProperty.create("up");
    private boolean flag = false;

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
            level.setBlock(pos.below(), Blocks.AIR.defaultBlockState() , 4);
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
        return state.getValue(IS_UP) ? BlockEntityTypesRegistry.WATER_FILTER_UP_TILE.create(pos, state) : BlockEntityTypesRegistry.WATER_FILTER_DOWN_TILE.create(pos, state);
    }



    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        //if (!level.isClientSide()) {
        flag = false;
        //if (!level.isClientSide()) {
        if (state.getValue(IS_UP)) {
            WaterFilterUpBlockEntity tile = (WaterFilterUpBlockEntity) level.getBlockEntity(pos);
            //WaterFilterDownTile downTile = (WaterFilterDownTile) level.getBlockEntity(pos.down());
            tile.getUpTank().ifPresent(fluidTankUp -> {
                tile.getStrainer().ifPresent(strainerHandler -> {
                    tile.getProps().ifPresent(propsHandler -> {
                        ItemStack strainerStack = strainerHandler.getStackInSlot(0);
                        ItemStack propsStack = propsHandler.getStackInSlot(0);
                        ItemStack heldItem = player.getItemInHand(interactionHand);
                        if (!heldItem.isEmpty()) {
                            //液体交互
                            if (!strainerStack.isEmpty()) {
                                flag = FluidUtil.interactWithFluidHandler(player, interactionHand, fluidTankUp);
                            }
                        } else if (player.getPose() == Pose.CROUCHING) {
                            if (fluidTankUp.getFluid().isEmpty()) {
                                //取出滤层
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
                                flag = true;
                            } else {
                                //清空液体
                                fluidTankUp.setFluid(FluidStack.EMPTY);
                                level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.DROWNED_HURT_WATER, SoundSource.BLOCKS, 0.3F, 1.0F, true);
                                flag = true;
                            }
                        }

                        if (player.getPose() != Pose.CROUCHING && !heldItem.isEmpty()) {
                            if (ItemTags.getAllTags().getTag(new ResourceLocation(WaterSource.MODID, "strainers")).contains(heldItem.getItem())) {
                                if (strainerStack.isEmpty()) {
                                    //填装滤层
                                    strainerHandler.insertItem(0, heldItem.copy(), true);
                                    if (!player.isCreative()) {
                                        ItemStack itemStack1 = heldItem.copy();
                                        itemStack1.setCount(heldItem.getCount() - 1);
                                        player.setItemInHand(interactionHand, itemStack1);
                                    }
                                    flag = true;
                                } else {
                                    //替换滤层
                                    ItemStack heldItem1 = heldItem.copy();
                                    player.setItemInHand(interactionHand, strainerStack);
                                    strainerHandler.setStackInSlot(0, heldItem1);
                                    flag = true;
                                }
                            }
                            if (heldItem.getItem() == Items.HEART_OF_THE_SEA)
                                player.setItemInHand(interactionHand, propsHandler.insertItem(0, heldItem.copy(), false));
                        }
                        SimpleNetworkHandler.CHANNEL.send(PacketDistributor.ALL.with(() -> null), new WaterFilterMessage(fluidTankUp.getFluid(), pos.getX(), pos.getY(), pos.getZ(), strainerHandler.getStackInSlot(0)));
                    });
                });
            });
        } else {//下
            WaterFilterDownBlockEntity tile = (WaterFilterDownBlockEntity) level.getBlockEntity(pos);
            tile.getDownTank().ifPresent(fluidTankDown -> {
                ItemStack heldItem = player.getItemInHand(interactionHand);
                if (!heldItem.isEmpty()) {
                    player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(playerInventory -> {
                        FluidActionResult fluidActionResult = FluidUtil.tryFillContainerAndStow(heldItem, fluidTankDown, playerInventory, Integer.MAX_VALUE, player, true);
                        if (fluidActionResult.isSuccess()) {
                            player.setItemInHand(interactionHand, fluidActionResult.getResult());
                            flag = true;
                        }
                    });
                    if (heldItem.getItem() == Items.GLASS_BOTTLE) {
                        FluidStack downFluidStack = fluidTankDown.getFluid();
                        if (!downFluidStack.isEmpty() && (downFluidStack.getFluid() == FluidRegistry.PURIFIED_WATER.get() || downFluidStack.getFluid() == FluidRegistry.SOUL_WATER.get())) {
//                            填装水瓶
                            ItemStack itemStack = ItemStack.EMPTY;
                            if (downFluidStack.getFluid() == FluidRegistry.PURIFIED_WATER.get()) {
                                itemStack = new ItemStack(ItemRegistry.itemPurifiedWaterBottle);
                            } else if (downFluidStack.getFluid() == FluidRegistry.SOUL_WATER.get()) {
                                itemStack = new ItemStack(ItemRegistry.itemSoulWaterBottle);
                            }
                            if (!itemStack.isEmpty() && downFluidStack.getAmount() >= 250) {
                                flag = player.getInventory().add(itemStack);
                                if (!flag) {
                                    player.drop(itemStack, false);
                                }
                                fluidTankDown.drain(250, IFluidHandler.FluidAction.EXECUTE);
                                if (!player.isCreative()) {
                                    heldItem.setCount(heldItem.getCount() - 1);
                                }
                                level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 0.8F, 1.0F, true);
                            }
                        }
                    }
                } else if (player.getPose() == Pose.CROUCHING && !fluidTankDown.getFluid().isEmpty()) {
                    //清空液体
                    fluidTankDown.setFluid(FluidStack.EMPTY);
                    level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 0.6F, 1.0F, true);
                    flag = true;
                }
                SimpleNetworkHandler.CHANNEL.send(PacketDistributor.ALL.with(() -> null), new WaterFilterMessage(fluidTankDown.getFluid(), pos.getX(), pos.getY(), pos.getZ(),ItemStack.EMPTY));
            });
        }
        //}
        //}
        return InteractionResult.SUCCESS;
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide && !state.getValue(IS_UP) ? null : createTickerHelper(blockEntityType, BlockEntityTypesRegistry.WATER_FILTER_UP_TILE, WaterFilterUpBlockEntity::serveTick);
    }
}
