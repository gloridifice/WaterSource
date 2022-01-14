package gloridifice.watersource.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class RedstoneWaterFilterBlock extends WaterFilterBlock {
    public RedstoneWaterFilterBlock(String name, Properties properties) {
        super(name, properties);
    }

    @Override
    public boolean shouldCheckWeakPower(BlockState state, LevelReader world, BlockPos pos, Direction side) {
        return true;
    }

/*
    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }


    @Override
    public int getComparatorInputOverride(BlockState blockState, Level level, BlockPos pos) {
        */
/*弱充能逻辑
         * 有可用滤网时，+5点弱充能
         * 当有液体时，+2点弱充能
         * 当液体满时，额外+4点弱充能*//*

        AtomicInteger power = new AtomicInteger();
        if (blockState.get(IS_UP)) {
            BlockEntity tile = level.getBlockEntity(pos);
            if (tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(data -> !data.getStackInSlot(0).isEmpty() && data.getStackInSlot(0).getItem() != BlockRegistry.ITEM_DIRTY_STRAINER).orElse(false)) {
                power.addAndGet(5);
            }
            tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent(data -> {
                if (!data.getFluidInTank(0).isEmpty()) {
                    power.addAndGet(2);
                    if (data.getFluidInTank(0).getAmount() == data.getTankCapacity(0)) {
                        power.addAndGet(2);

                    }
                }
            });
        }*/
/*else {
            BlockEntity UpTile = level.getBlockEntity(pos.above());
            BlockEntity DownTile = level.getBlockEntity(pos);
            if (UpTile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(data -> !data.getStackInSlot(0).isEmpty() && data.getStackInSlot(0).getItem() != BlockRegistry.ITEM_DIRTY_STRAINER).orElse(false)){
                power.addAndGet(5);
            }
            DownTile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent(data -> {
                if (!data.getFluidInTank(0).isEmpty()){
                    power.addAndGet(2);
                    if (data.getFluidInTank(0).getAmount() == data.getTankCapacity(0)){
                        power.addAndGet(2);
                    }
                }
            });
        }*//*

        return power.get();
    }

*/


/*    @Override
    public void addInformation(ItemStack stack,  IBlockReader level, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (Minecraft.getInstance().gameSettings.keyBindSneak.isPressed()){
            tooltip.add(new TranslationTextComponent("watersource.info.redstone_water_filter").setStyle(new Style().setColor(TextFormatting.GRAY)));
        }else tooltip.add(new TranslationTextComponent("watersource.info.press.sneak").setStyle(new Style().setColor(TextFormatting.GRAY).setBold(true)));
        super.addInformation(stack, level, tooltip, flagIn);
    }*/
}
