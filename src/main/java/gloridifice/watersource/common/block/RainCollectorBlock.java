package gloridifice.watersource.common.block;

import gloridifice.watersource.common.tile.RainCollectorTile;
import gloridifice.watersource.registry.TileEntityTypesRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;

public class RainCollectorBlock extends Block {
    public RainCollectorBlock(String name, Properties properties) {
        super(properties);
        this.setRegistryName(name);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityTypesRegistry.RAIN_COLLECTOR.create();
    }

    boolean flag = false;
    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        RainCollectorTile tile = (RainCollectorTile)world.getTileEntity(blockPos);
        tile.getTank().ifPresent(fluidTankUp -> {
            ItemStack heldItem = player.getHeldItem(hand);
            if (!heldItem.isEmpty()) {
                //液体交互
                flag = FluidUtil.interactWithFluidHandler(player, hand, fluidTankUp);
            }
        });
        return flag ? ActionResultType.SUCCESS : ActionResultType.PASS;
    }
}
