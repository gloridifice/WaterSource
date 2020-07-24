package gloridifice.watersource.common.block;

import gloridifice.watersource.common.item.StrainerBlockItem;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
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
    public boolean causesSuffocation(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
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
        if (state.get(IS_UP)) {
            worldIn.setBlockState(pos.down(), Blocks.AIR.getDefaultState());
        } else worldIn.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
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
        } else return null;
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
            if (state.get(IS_UP)) {
                WaterFilterUpTile tile = (WaterFilterUpTile) worldIn.getTileEntity(pos);
                //WaterFilterDownTile downTile = (WaterFilterDownTile) worldIn.getTileEntity(pos.down());
                tile.getUpTank().ifPresent(fluidTank -> {
                    if (!player.getHeldItem(handIn).isEmpty()){
                        flag = FluidUtil.interactWithFluidHandler(player, handIn, fluidTank);
                    }else if (player.isSneaking()){
                        tile.getStrainer().ifPresent(itemStackHandler -> {
                            if (fluidTank.getFluid().isEmpty()){
                                if (!itemStackHandler.getStackInSlot(0).isEmpty()){
                                    flag = player.inventory.addItemStackToInventory(itemStackHandler.getStackInSlot(0));
                                    if (flag){
                                        itemStackHandler.getStackInSlot(0).setCount(0);
                                    }
                                }
                            }else {
                                fluidTank.setFluid(FluidStack.EMPTY);
                                worldIn.playSound(pos.getX(),pos.getY(),pos.getZ(), SoundEvents.ENTITY_DROWNED_HURT_WATER, SoundCategory.BLOCKS, 0.3F,1.0F,true);
                                flag = true;
                            }
                        });
                    }
                });
                tile.getStrainer().ifPresent(itemStackHandler -> {
                    ItemStack itemStack = itemStackHandler.getStackInSlot(0);
                    if (itemStack.isEmpty()){
                        if (!player.getHeldItem(handIn).isEmpty() && player.getHeldItem(handIn).getItem() instanceof StrainerBlockItem){
                            itemStackHandler.insertItem(0,player.getHeldItem(handIn).copy(),false);
                            if (!player.isCreative()){
                                ItemStack itemStack1 = player.getHeldItem(handIn).copy();
                                itemStack1.setCount(player.getHeldItem(handIn).getCount() - 1);
                                player.setHeldItem(handIn,itemStack1);
                            }
                            flag = true;
                        }
                    }
                });
            } else {
                WaterFilterDownTile tile = (WaterFilterDownTile) worldIn.getTileEntity(pos);
                tile.getDownTank().ifPresent(fluidTankDown -> {
                    if (!player.getHeldItem(handIn).isEmpty()){
                        player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                                .map(playerInventory -> {
                                    flag = FluidUtil.tryFillContainerAndStow(player.getHeldItem(handIn), fluidTankDown, playerInventory, Integer.MAX_VALUE, player, true) != FluidActionResult.FAILURE;
                                    return flag;
                                });
                        if (ItemStack.areItemStackTagsEqual(new ItemStack(Items.GLASS_BOTTLE), player.getHeldItem(handIn))){
                            FluidStack downFluidStack = fluidTankDown.getFluid();
                            if (!downFluidStack.isEmpty() && downFluidStack.getFluid() == FluidRegistry.purifiedWaterFluid.get()){
                                if (downFluidStack.getAmount() >= 100){
                                    flag = player.inventory.addItemStackToInventory(new ItemStack(ItemRegistry.itemPurifiedWaterBottle));
                                    if (!flag) {
                                        player.dropItem(new ItemStack(ItemRegistry.itemPurifiedWaterBottle), false);
                                    }
                                    fluidTankDown.drain(100, IFluidHandler.FluidAction.EXECUTE);
                                    if (!player.isCreative()){
                                        player.getHeldItem(handIn).setCount(player.getHeldItem(handIn).getCount() - 1);
                                    }
                                    worldIn.playSound(pos.getX(),pos.getY(),pos.getZ(), SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.PLAYERS, 0.8F,1.0F,true);
                                }
                            }
                        }
                    }else if (player.isSneaking() && !fluidTankDown.getFluid().isEmpty()){
                        fluidTankDown.setFluid(FluidStack.EMPTY);
                        worldIn.playSound(pos.getX(),pos.getY(),pos.getZ(), SoundEvents.ITEM_BUCKET_FILL, SoundCategory.PLAYERS, 0.6F,1.0F,true);
                        flag = true;
                    }
                });
            }
        //}
        return flag ? ActionResultType.SUCCESS : ActionResultType.PASS;
    }
}
