package gloridifice.watersource.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.block.CoconutSaplingBlock;
import gloridifice.watersource.common.block.NaturalCoconutBlock;
import gloridifice.watersource.common.world.gen.config.CoconutTreeFeatureConfig;
import gloridifice.watersource.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.material.Material;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.common.IPlantable;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class CoconutTreeFeature extends Feature<CoconutTreeFeatureConfig> {
    public CoconutTreeFeature(Codec<CoconutTreeFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random rand, BlockPos positionIn, CoconutTreeFeatureConfig configIn) {
        System.out.println(positionIn);//todo test
        int a = rand.nextInt(4);
        int height = 7 + rand.nextInt(2);
        if (isSand(iSeedReader,positionIn.down()) && canPlace(iSeedReader,positionIn,height,a)){
            BlockPos pos = positionIn;
            for (int i = 0 ; i < height/2; i++){
                pos = positionIn.up(i);
                setBlockState(iSeedReader,pos,configIn.trunkProvider.getBlockState(rand,pos));
            }
            for (int i = 0; i < height/2; i++){
                pos = positionIn.up(height/2 +i).offset(Direction.byIndex(a + 2));
                if (i == height/2 - 1){
                    setBlockState(iSeedReader, pos, BlockRegistry.BLOCK_COCONUT_TREE_HEAD.getDefaultState());
                    for (int n = 2; n <= 5; n++){
                        if (rand.nextInt(4) == 0){
                            placeLeaves(iSeedReader, pos.offset(Direction.byIndex(n)), BlockRegistry.BLOCK_NATURAL_COCONUT.getDefaultState().with(NaturalCoconutBlock.AGE,3).with(NaturalCoconutBlock.HORIZONTAL_FACING, Direction.byIndex(n).getOpposite()));
                        }
                    }
                }else setBlockState(iSeedReader, pos, configIn.trunkProvider.getBlockState(rand,pos));
            }
            //generate leaves
            pos = pos.up();
            placeLeaves(iSeedReader,pos,configIn.leavesProvider.getBlockState(rand,pos).with(LeavesBlock.DISTANCE,1));
            for (int i = 1; i <= 5;i++){
                for (int j = 2; j <= 5; j++){
                    if (i <= 4){
                        placeLeaves(iSeedReader,pos.offset(Direction.byIndex(j),i),configIn.leavesProvider.getBlockState(rand,pos.west(i)).with(LeavesBlock.DISTANCE,1));
                    }
                    if (i == 2){
                        placeLeaves(iSeedReader,pos.offset(Direction.byIndex(j),i).up(),configIn.leavesProvider.getBlockState(rand,pos.west(i).up()).with(LeavesBlock.DISTANCE,1));
                        for (int u = 2; u <= 5; u++) {
                            placeLeaves(iSeedReader, pos.offset(Direction.byIndex(j),i).offset(Direction.byIndex(u)), configIn.leavesProvider.getBlockState(rand, pos.west(i).up()).with(LeavesBlock.DISTANCE, 1));
                        }
                    }
                    if (i == 4 || i == 2 || i == 5){
                        placeLeaves(iSeedReader,pos.offset(Direction.byIndex(j),i).down(),configIn.leavesProvider.getBlockState(rand,pos.west(i).down()).with(LeavesBlock.DISTANCE,1));
                    }
                }
            }
            return true;
        }else return false;
    }



    protected static boolean isSand(IWorldGenerationBaseReader reader, BlockPos pos) {
        if (!(reader instanceof net.minecraft.world.IBlockReader))
            //todo 自定义生长方块
            return reader.hasBlockState(pos, (data) -> {
                return BlockTags.getCollection().get(new ResourceLocation(WaterSource.MODID,"coconut_soil")).contains(data.getBlock());
            });
        return reader.hasBlockState(pos, state -> state.canSustainPlant((net.minecraft.world.IBlockReader)reader, pos, Direction.UP, (CoconutSaplingBlock) BlockRegistry.BLOCK_COCONUT_SAPLING));
    }
    protected boolean placeLeaves(IWorldGenerationReader generationReader, BlockPos pos, BlockState blockState){
        if (isAirOrLeavesAt(generationReader,pos)){
            setBlockState(generationReader,pos,blockState);
            return true;
        }else return false;
    }
    protected boolean canPlace(IWorldGenerationBaseReader generationReader, BlockPos positionIn, int height, int randA) {
        boolean flag = true;
        BlockPos pos;
        for (int i = 0; i < height / 2; i++) {
            pos = positionIn.up(i);
            flag = flag && isReplaceableAt(generationReader, pos);
            if (!flag) return flag;
        }
        for (int i = 0; i < height / 2; i++) {
            pos = positionIn.up(height / 2 + i).offset(Direction.byIndex(randA + 2));
            if (i == height / 2 - 1) {
                flag = flag && isReplaceableAt(generationReader, pos);
                if (!flag) return flag;
            }
        }
        return flag;
    }
    private static boolean isWaterAt(IWorldGenerationBaseReader p_236416_0_, BlockPos p_236416_1_) {
        return p_236416_0_.hasBlockState(p_236416_1_, (p_236413_0_) -> {
            return p_236413_0_.isIn(Blocks.WATER);
        });
    }
    private static boolean isTallPlantAt(IWorldGenerationBaseReader p_236419_0_, BlockPos p_236419_1_) {
        return p_236419_0_.hasBlockState(p_236419_1_, (p_236406_0_) -> {
            Material lvt_1_1_ = p_236406_0_.getMaterial();
            return lvt_1_1_ == Material.TALL_PLANTS;
        });
    }
    public static boolean isReplaceableAt(IWorldGenerationBaseReader p_236404_0_, BlockPos p_236404_1_) {
        return isAirOrLeavesAt(p_236404_0_, p_236404_1_) || isTallPlantAt(p_236404_0_, p_236404_1_) || isWaterAt(p_236404_0_, p_236404_1_);
    }
    public static boolean isAirOrLeavesAt(IWorldGenerationBaseReader p_236412_0_, BlockPos p_236412_1_) {
        return p_236412_0_.hasBlockState(p_236412_1_, (p_236411_0_) -> {
            return p_236411_0_.isAir() || p_236411_0_.isIn(BlockTags.LEAVES);
        });
    }
}
