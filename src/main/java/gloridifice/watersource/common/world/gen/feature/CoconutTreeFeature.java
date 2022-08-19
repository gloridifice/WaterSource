package gloridifice.watersource.common.world.gen.feature;

import com.mojang.serialization.Codec;
import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.block.NaturalCoconutBlock;
import gloridifice.watersource.data.ModBlockTags;
import gloridifice.watersource.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

import java.util.Random;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class CoconutTreeFeature extends Feature<NoneFeatureConfiguration> {
    public CoconutTreeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        if (!canPlace(context)) return false;
        Random rand = context.random();
        BlockPos blockPos = context.origin();
        WorldGenLevel level = context.level();
        BlockState trunk = BlockRegistry.PALM_TREE_LOG.get().defaultBlockState();
        BlockState head = BlockRegistry.PALM_TREE_HEAD.get().defaultBlockState();
        BlockState leaf = BlockRegistry.PALM_TREE_LEAF.get().defaultBlockState().setValue(LeavesBlock.DISTANCE, 1);

        int a = rand.nextInt(4);
        int height = 7 + rand.nextInt(2);
        if (isSand(level, blockPos.below())) {
            BlockPos pos = blockPos;
            for (int i = 0; i < height / 2; i++) {
                pos = blockPos.above(i);
                setBlock(level, pos, trunk);
            }
            for (int i = 0; i < height / 2; i++) {
                pos = blockPos.above(height / 2 + i).offset(Direction.from2DDataValue(a + 2).getNormal());
                if (i == height / 2 - 1) {
                    setBlock(level, pos, head);
                    for (int n = 2; n <= 5; n++) {
                        if (rand.nextInt(4) == 0) {
                            placeLeaves(level, pos.offset(Direction.from2DDataValue(n).getNormal()), BlockRegistry.BLOCK_NATURAL_COCONUT.get().defaultBlockState().setValue(NaturalCoconutBlock.AGE, 3).setValue(HORIZONTAL_FACING, Direction.from2DDataValue(n).getOpposite()));
                        }
                    }
                } else setBlock(level, pos, trunk);
            }
            //generate leaves
            pos = pos.above();
            placeLeaves(level, pos, leaf);
            placeLeaves(level, pos.above(), leaf);
            for (int i = 1; i <= 5; i++) {
                for (int j = 2; j <= 5; j++) {//direction
                    if (i == 1) {
                        placeLeaves(level, pos.above().offset(Direction.from2DDataValue(j).getNormal().multiply(i)), leaf);
                    }
                    if (i <= 4) {
                        placeLeaves(level, pos.offset(Direction.from2DDataValue(j).getNormal().multiply(i)), leaf);
                    }
                    if (i == 2) {
                        placeLeaves(level, pos.offset(Direction.from2DDataValue(j).getNormal().multiply(i)), leaf);
                        for (int u = 2; u <= 5; u++) {
                            placeLeaves(level, pos.offset(Direction.from2DDataValue(j).getNormal().multiply(i)).offset(Direction.from2DDataValue(u).getNormal()), leaf);
                        }
                    }
                    if (i == 2 || i == 4 || i == 5) {
                        placeLeaves(level, pos.offset(Direction.from2DDataValue(j).getNormal().multiply(i)).below(), leaf);
                    }
                }
            }
            return true;
        } else return false;
    }


    protected static boolean isSand(LevelReader reader, BlockPos pos) {
        ITag<Block> tag = ForgeRegistries.BLOCKS.tags().getTag(ModBlockTags.COCONUT_SOIL);
        if (tag != null) return tag.contains(reader.getBlockState(pos).getBlock());
        return false;
    }

    protected boolean placeLeaves(WorldGenLevel generationReader, BlockPos pos, BlockState blockState) {
        if (isAir(generationReader, pos)) {
            setBlock(generationReader, pos, blockState);
            return true;
        } else return false;
    }

    protected static boolean canPlace(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos blockPos = context.origin();
        WorldGenLevel level = context.level();
        ITag<Block> soilTag = ForgeRegistries.BLOCKS.tags().getTag(ModBlockTags.COCONUT_SOIL);
        return soilTag != null && soilTag.contains(level.getBlockState(blockPos.below()).getBlock()) && level.getFluidState(blockPos).isEmpty();
    }
}
