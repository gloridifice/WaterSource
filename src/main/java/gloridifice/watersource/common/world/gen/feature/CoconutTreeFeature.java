package gloridifice.watersource.common.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import gloridifice.watersource.common.block.NaturalCoconutBlock;
import gloridifice.watersource.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
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
    protected boolean place(IWorldGenerationReader generationReader, Random rand, BlockPos positionIn, Set<BlockPos> posSet1, Set<BlockPos> posSet2, MutableBoundingBox boundingBoxIn, TreeFeatureConfig configIn) {
        int height = 7 + rand.nextInt(2);
        BlockPos pos = positionIn;
        for (int i = 0 ; i < height/2; i++){
            pos = positionIn.up(i);
            setBlockState(generationReader,pos,configIn.trunkProvider.getBlockState(rand,pos));
        }
        int a = rand.nextInt(4);
        for (int i = 0; i < height/2; i++){
            switch (a) {
                case 0 :
                    pos = positionIn.up(height/2 +i).north();
                    break;
                case 1:
                    pos = positionIn.up(height/2 +i).east();
                    break;
                case 2:
                    pos = positionIn.up(height/2 +i).south();
                    break;
                case 3:
                    pos = positionIn.up(height/2 +i).west();
                    break;
                default:
                    pos = positionIn.up(height/2 +i);
            }
            if (i == height/2 - 1){
                setBlockState(generationReader, pos, BlockRegistry.blockCoconutTreeHead.getDefaultState());
                if (rand.nextInt(4) == 0){
                    setBlockState(generationReader, pos.west(), BlockRegistry.blockNaturalCoconut.getDefaultState().with(NaturalCoconutBlock.AGE,3).with(NaturalCoconutBlock.HORIZONTAL_FACING, Direction.EAST));
                }
                if (rand.nextInt(4) == 0){
                    setBlockState(generationReader, pos.east(), BlockRegistry.blockNaturalCoconut.getDefaultState().with(NaturalCoconutBlock.AGE,3).with(NaturalCoconutBlock.HORIZONTAL_FACING, Direction.WEST));
                }
                if (rand.nextInt(4) == 0){
                    setBlockState(generationReader, pos.south(), BlockRegistry.blockNaturalCoconut.getDefaultState().with(NaturalCoconutBlock.AGE,3).with(NaturalCoconutBlock.HORIZONTAL_FACING, Direction.NORTH));
                }
                if (rand.nextInt(4) == 0){
                    setBlockState(generationReader, pos.north(), BlockRegistry.blockNaturalCoconut.getDefaultState().with(NaturalCoconutBlock.AGE,3).with(NaturalCoconutBlock.HORIZONTAL_FACING, Direction.SOUTH));
                }
            }else setBlockState(generationReader, pos, configIn.trunkProvider.getBlockState(rand,pos));
        }
        pos = pos.up();
        setBlockState(generationReader,pos,configIn.leavesProvider.getBlockState(rand,pos).with(LeavesBlock.DISTANCE,1));
        for (int i = 1; i <= 5;i++){
            if (i <= 4){
                setBlockState(generationReader,pos.west(i),configIn.leavesProvider.getBlockState(rand,pos.west(i)).with(LeavesBlock.DISTANCE,1));
                setBlockState(generationReader,pos.east(i),configIn.leavesProvider.getBlockState(rand,pos.east(i)).with(LeavesBlock.DISTANCE,1));
                setBlockState(generationReader,pos.south(i),configIn.leavesProvider.getBlockState(rand,pos.south(i)).with(LeavesBlock.DISTANCE,1));
                setBlockState(generationReader,pos.north(i),configIn.leavesProvider.getBlockState(rand,pos.north(i)).with(LeavesBlock.DISTANCE,1));
            }
            if (i == 2){
                setBlockState(generationReader,pos.west(i).up(),configIn.leavesProvider.getBlockState(rand,pos.west(i).up()).with(LeavesBlock.DISTANCE,1));
                setBlockState(generationReader,pos.east(i).up(),configIn.leavesProvider.getBlockState(rand,pos.east(i).up()).with(LeavesBlock.DISTANCE,1));
                setBlockState(generationReader,pos.south(i).up(),configIn.leavesProvider.getBlockState(rand,pos.south(i).up()).with(LeavesBlock.DISTANCE,1));
                setBlockState(generationReader,pos.north(i).up(),configIn.leavesProvider.getBlockState(rand,pos.north(i).up()).with(LeavesBlock.DISTANCE,1));
            }
            if (i == 4 || i == 2 || i == 5){
                setBlockState(generationReader,pos.west(i).down(),configIn.leavesProvider.getBlockState(rand,pos.west(i).down()).with(LeavesBlock.DISTANCE,1));
                setBlockState(generationReader,pos.east(i).down(),configIn.leavesProvider.getBlockState(rand,pos.east(i).down()).with(LeavesBlock.DISTANCE,1));
                setBlockState(generationReader,pos.south(i).down(),configIn.leavesProvider.getBlockState(rand,pos.south(i).down()).with(LeavesBlock.DISTANCE,1));
                setBlockState(generationReader,pos.north(i).down(),configIn.leavesProvider.getBlockState(rand,pos.north(i).down()).with(LeavesBlock.DISTANCE,1));
            }
        }
        return true;
    }
}
