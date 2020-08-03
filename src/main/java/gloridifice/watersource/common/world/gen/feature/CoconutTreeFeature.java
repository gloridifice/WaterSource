package gloridifice.watersource.common.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import gloridifice.watersource.common.block.NaturalCoconutBlock;
import gloridifice.watersource.common.data.tag.ModTags;
import gloridifice.watersource.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.dispenser.Position;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.*;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class CoconutTreeFeature extends AbstractTreeFeature<TreeFeatureConfig> {
    public CoconutTreeFeature(String name, Function<Dynamic<?>, ? extends TreeFeatureConfig> function) {
        super(function);
        this.setRegistryName(name);
    }
    @Override
    public boolean place(IWorldGenerationReader generationReader, Random rand, BlockPos positionIn, Set<BlockPos> posSet1, Set<BlockPos> posSet2, MutableBoundingBox boundingBoxIn, TreeFeatureConfig configIn) {
        int a = rand.nextInt(4);
        int height = 7 + rand.nextInt(2);
        if (isSand(generationReader,positionIn.down(),configIn.getSapling()) && canPlace(generationReader,positionIn,height,a)){
            BlockPos pos = positionIn;
            for (int i = 0 ; i < height/2; i++){
                pos = positionIn.up(i);
                setBlockState(generationReader,pos,configIn.trunkProvider.getBlockState(rand,pos));
            }
            for (int i = 0; i < height/2; i++){
                pos = positionIn.up(height/2 +i).offset(Direction.byIndex(a + 2));
                if (i == height/2 - 1){
                    setBlockState(generationReader, pos, BlockRegistry.blockCoconutTreeHead.getDefaultState());
                    for (int n = 2; n <= 5; n++){
                        if (rand.nextInt(4) == 0){
                            placeLeaves(generationReader, pos.offset(Direction.byIndex(n)), BlockRegistry.blockNaturalCoconut.getDefaultState().with(NaturalCoconutBlock.AGE,3).with(NaturalCoconutBlock.HORIZONTAL_FACING, Direction.byIndex(n).getOpposite()));
                        }
                    }
                }else setBlockState(generationReader, pos, configIn.trunkProvider.getBlockState(rand,pos));
            }
            //generate leaves
            pos = pos.up();
            placeLeaves(generationReader,pos,configIn.leavesProvider.getBlockState(rand,pos).with(LeavesBlock.DISTANCE,1));
            for (int i = 1; i <= 5;i++){
                for (int j = 2; j <= 5; j++){
                    if (i <= 4){
                        placeLeaves(generationReader,pos.offset(Direction.byIndex(j),i),configIn.leavesProvider.getBlockState(rand,pos.west(i)).with(LeavesBlock.DISTANCE,1));
                    }
                    if (i == 2){
                        placeLeaves(generationReader,pos.offset(Direction.byIndex(j),i).up(),configIn.leavesProvider.getBlockState(rand,pos.west(i).up()).with(LeavesBlock.DISTANCE,1));
                        for (int u = 2; u <= 5; u++) {
                            placeLeaves(generationReader, pos.offset(Direction.byIndex(j),i).offset(Direction.byIndex(u)), configIn.leavesProvider.getBlockState(rand, pos.west(i).up()).with(LeavesBlock.DISTANCE, 1));
                        }
                    }
                    if (i == 4 || i == 2 || i == 5){
                        placeLeaves(generationReader,pos.offset(Direction.byIndex(j),i).down(),configIn.leavesProvider.getBlockState(rand,pos.west(i).down()).with(LeavesBlock.DISTANCE,1));
                    }
                }
            }
            return true;
        }else return false;
    }
    protected static boolean isSand(IWorldGenerationBaseReader reader, BlockPos pos, net.minecraftforge.common.IPlantable sapling) {
        if (!(reader instanceof net.minecraft.world.IBlockReader) || sapling == null)
            //todo 自定义生长方块
            return reader.hasBlockState(pos, (data) -> {
                return ModTags.Block.COCONUTS_SOIL.contains(data.getBlock());
            });
        return reader.hasBlockState(pos, state -> state.canSustainPlant((net.minecraft.world.IBlockReader)reader, pos, Direction.UP, sapling));
    }
    protected boolean placeLeaves(IWorldGenerationReader generationReader, BlockPos pos, BlockState blockState){
        if (isAirOrLeaves(generationReader,pos)){
            setBlockState(generationReader,pos,blockState);
            return true;
        }else return false;
    }
    protected boolean canPlace(IWorldGenerationBaseReader generationReader, BlockPos positionIn, int height, int randA) {
        boolean flag = true;
        BlockPos pos = positionIn;
        for (int i = 0; i < height / 2; i++) {
            pos = positionIn.up(i);
            flag = flag && canBeReplacedByLogs(generationReader, pos);
            if (!flag) return flag;
        }
        for (int i = 0; i < height / 2; i++) {
            pos = positionIn.up(height / 2 + i).offset(Direction.byIndex(randA + 2));
            if (i == height / 2 - 1) {
                flag = flag && canBeReplacedByLogs(generationReader, pos);
                if (!flag) return flag;
            }
        }
        return flag;
    }
}
