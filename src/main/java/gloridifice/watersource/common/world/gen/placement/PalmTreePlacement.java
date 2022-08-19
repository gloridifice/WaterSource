package gloridifice.watersource.common.world.gen.placement;

import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.Random;
import java.util.stream.Stream;

public class PalmTreePlacement extends PlacementModifier {
    private final IntProvider count;

    private PalmTreePlacement(IntProvider intProvider) {
        this.count = intProvider;
    }

    public static PalmTreePlacement of(IntProvider intProvider) {
        return new PalmTreePlacement(intProvider);
    }

    public static PalmTreePlacement of(int count) {
        return of(ConstantInt.of(count));
    }

    public Stream<BlockPos> getPositions(PlacementContext context, Random random, BlockPos pos) {
        Stream.Builder<BlockPos> builder = Stream.builder();
        int i = 0;

        boolean flag;
        do {
            flag = false;

            for (int j = 0; j < this.count.sample(random); ++j) {
                int k = random.nextInt(16) + pos.getX();
                int l = random.nextInt(16) + pos.getZ();
                int i1 = context.getHeight(Heightmap.Types.MOTION_BLOCKING, k, l);
                int j1 = findOnGroundYPosition(context, k, i1, l, i);
                if (j1 != Integer.MAX_VALUE) {
                    if (random.nextDouble() < 0.5) {
                        builder.add(new BlockPos(k, j1, l));
                        flag = true;
                    }
                }
            }

            ++i;
        } while (flag);

        return builder.build();
    }

    public PlacementModifierType<?> type() {
        return PlacementModifierType.COUNT_ON_EVERY_LAYER;
    }

    private static int findOnGroundYPosition(PlacementContext context, int p_191614_, int p_191615_, int p_191616_, int p_191617_) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(p_191614_, p_191615_, p_191616_);
        int i = 0;
        BlockState blockstate = context.getBlockState(blockpos$mutableblockpos);

        for (int j = p_191615_; j >= context.getMinBuildHeight() + 1; --j) {
            blockpos$mutableblockpos.setY(j - 1);
            BlockState blockstate1 = context.getBlockState(blockpos$mutableblockpos);
            if (!isEmpty(blockstate1) && isEmpty(blockstate) && !blockstate1.is(Blocks.BEDROCK)) {
                if (i == p_191617_) {
                    return blockpos$mutableblockpos.getY() + 1;
                }

                ++i;
            }

            blockstate = blockstate1;
        }

        return Integer.MAX_VALUE;
    }

    private static boolean isEmpty(BlockState p_191609_) {
        return p_191609_.isAir() || p_191609_.is(Blocks.WATER) || p_191609_.is(Blocks.LAVA);
    }
}
