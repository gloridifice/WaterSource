package gloridifice.watersource.common.block;


import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.tile.WaterFilterDownTile;
import gloridifice.watersource.common.tile.WaterFilterUpTile;
import gloridifice.watersource.registry.FluidRegistry;
import gloridifice.watersource.registry.ItemRegistry;
import gloridifice.watersource.registry.TileEntityTypesRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

public class WaterFilterBlock extends Block {
    public static final BooleanProperty IS_UP = BooleanProperty.create("up");
    private boolean flag = false;

    public WaterFilterBlock(String name, Properties properties) {
        super(properties);
        this.setRegistryName(name);
        this.setDefaultState(this.getStateContainer().getBaseState().with(IS_UP, false));
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1.0F;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(IS_UP);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile instanceof WaterFilterUpTile) {
            ItemStack stack = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(data -> data.getStackInSlot(0)).orElse(ItemStack.EMPTY);
            if (!stack.isEmpty()) {
                spawnAsEntity(worldIn, pos, stack);
            }
        }
        if (tile instanceof WaterFilterDownTile) {
            ItemStack stack = worldIn.getTileEntity(pos.up()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(data -> data.getStackInSlot(0)).orElse(ItemStack.EMPTY);
            if (!stack.isEmpty()) {
                spawnAsEntity(worldIn, pos, stack);
            }
        }
        if (state.get(IS_UP)) {
            worldIn.setBlockState(pos.down(), Blocks.AIR.getDefaultState());
        }
        else worldIn.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        worldIn.setBlockState(pos.up(), state.with(IS_UP, true), 3);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        if (context.getWorld().isAirBlock(context.getPos().up()) && context.getPos().up().getY() < 255) {
            return this.getDefaultState();
        }
        else return null;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return state.get(IS_UP) ? TileEntityTypesRegistry.WATER_FILTER_UP_TILE.create() : TileEntityTypesRegistry.WATER_FILTER_DOWN_TILE.create();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        //if (!worldIn.isRemote()) {
        flag = false;
        //if (!worldIn.isRemote()) {
        if (state.get(IS_UP)) {
            WaterFilterUpTile tile = (WaterFilterUpTile) worldIn.getTileEntity(pos);
            //WaterFilterDownTile downTile = (WaterFilterDownTile) worldIn.getTileEntity(pos.down());
            tile.getUpTank().ifPresent(fluidTankUp -> {
                tile.getStrainer().ifPresent(strainerHandler -> {
                    tile.getProps().ifPresent(propsHandler -> {
                        ItemStack strainerStack = strainerHandler.getStackInSlot(0);
                        ItemStack propsStack = propsHandler.getStackInSlot(0);
                        ItemStack heldItem = player.getHeldItem(handIn);
                        if (!heldItem.isEmpty()) {
                            //液体交互
                            if (!strainerStack.isEmpty()) {
                                flag = FluidUtil.interactWithFluidHandler(player, handIn, fluidTankUp);
                            }
                        }
                        else if (player.isSneaking()) {
                            if (fluidTankUp.getFluid().isEmpty()) {
                                //取出滤层
                                if (!strainerStack.isEmpty()) {
                                    if (!player.inventory.addItemStackToInventory(strainerStack)) {
                                        player.dropItem(strainerStack, false);
                                    }
                                    strainerHandler.setStackInSlot(0, ItemStack.EMPTY);

                                }
                                else if (!propsStack.isEmpty()) {
                                    if (!player.inventory.addItemStackToInventory(propsStack)) {
                                        player.dropItem(propsStack, false);
                                    }
                                    propsHandler.setStackInSlot(0, ItemStack.EMPTY);
                                }
                                flag = true;
                            }
                            else {
                                //清空液体
                                fluidTankUp.setFluid(FluidStack.EMPTY);
                                worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_DROWNED_HURT_WATER, SoundCategory.BLOCKS, 0.3F, 1.0F, true);
                                flag = true;
                            }
                        }

                        if (!player.isSneaking() && !heldItem.isEmpty()) {
                            if (ItemTags.getCollection().get(new ResourceLocation(WaterSource.MODID, "strainers")).contains(heldItem.getItem())) {
                                if (strainerStack.isEmpty()) {
                                    //填装滤层
                                    strainerHandler.insertItem(0, heldItem.copy(), true);
                                    if (!player.isCreative()) {
                                        ItemStack itemStack1 = heldItem.copy();
                                        itemStack1.setCount(heldItem.getCount() - 1);
                                        player.setHeldItem(handIn, itemStack1);
                                    }
                                    flag = true;
                                }
                                else {
                                    //替换滤层
                                    ItemStack heldItem1 = heldItem.copy();
                                    player.setHeldItem(handIn, strainerStack);
                                    strainerHandler.setStackInSlot(0, heldItem1);
                                    flag = true;
                                }
                            }
                            if (heldItem.getItem() == Items.HEART_OF_THE_SEA)
                                player.setHeldItem(handIn, propsHandler.insertItem(0, heldItem.copy(), false));
                        }
                        //SimpleNetworkHandler.CHANNEL.send(PacketDistributor.ALL.with(() -> null), new WaterFilterMessage(fluidTankUp.getFluid(), pos.getX(), pos.getY(), pos.getZ(),itemStackHandler.getStackInSlot(0)));
                    });
                });
            });
        }
        else {//下
            WaterFilterDownTile tile = (WaterFilterDownTile) worldIn.getTileEntity(pos);
            tile.getDownTank().ifPresent(fluidTankDown -> {
                ItemStack heldItem = player.getHeldItem(handIn);
                if (!heldItem.isEmpty()) {
                    player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(playerInventory -> {
                        FluidActionResult fluidActionResult = FluidUtil.tryFillContainerAndStow(heldItem, fluidTankDown, playerInventory, Integer.MAX_VALUE, player, true);
                        if (fluidActionResult.isSuccess()) {
                            player.setHeldItem(handIn, fluidActionResult.getResult());
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
                            }
                            else if (downFluidStack.getFluid() == FluidRegistry.SOUL_WATER.get()) {
                                itemStack = new ItemStack(ItemRegistry.itemSoulWaterBottle);
                            }
                            if (!itemStack.isEmpty() && downFluidStack.getAmount() >= 250) {
                                flag = player.inventory.addItemStackToInventory(itemStack);
                                if (!flag) {
                                    player.dropItem(itemStack, false);
                                }
                                fluidTankDown.drain(250, IFluidHandler.FluidAction.EXECUTE);
                                if (!player.isCreative()) {
                                    heldItem.setCount(heldItem.getCount() - 1);
                                }
                                worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.PLAYERS, 0.8F, 1.0F, true);
                            }
                        }
                    }
                }
                else if (player.isSneaking() && !fluidTankDown.getFluid().isEmpty()) {
                    //清空液体
                    fluidTankDown.setFluid(FluidStack.EMPTY);
                    worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_BUCKET_FILL, SoundCategory.PLAYERS, 0.6F, 1.0F, true);
                    flag = true;
                }
                //SimpleNetworkHandler.CHANNEL.send(PacketDistributor.ALL.with(() -> null), new WaterFilterMessage(fluidTankDown.getFluid(), pos.getX(), pos.getY(), pos.getZ(),ItemStack.EMPTY));
            });
        }
        //}
        //}
        return ActionResultType.SUCCESS;
    }
}
