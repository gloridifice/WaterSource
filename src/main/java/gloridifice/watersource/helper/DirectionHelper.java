package gloridifice.watersource.helper;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import java.util.*;

public class DirectionHelper {
    /*获得blockPos1相对于blockPos2的位置*/
    public static Direction getDirectionFromBlockToBlock(BlockPos blockPos1, BlockPos blockPos2){
        BlockPos pos = blockPos1.add(-blockPos2.getX(), -blockPos2.getY(), -blockPos2.getZ());
        Direction direction = null;
        Map<String,Integer> map1 = new HashMap<>();
        Map<String,Integer> map2 = new HashMap<>();
        map2.put("x", pos.getX());
        map2.put("y", pos.getY());
        map2.put("z", pos.getZ());

        map1.put("x", Math.abs(pos.getX()));
        map1.put("y", Math.abs(pos.getY()));
        map1.put("z", Math.abs(pos.getZ()));

        List<Map.Entry<String,Integer>> list = new ArrayList(map1.entrySet());
        Collections.sort(list, Comparator.comparingInt(Map.Entry::getValue));
        String dir = list.get(list.size() - 1).getKey();
        int result = map2.get(dir);
        switch (dir){
            case "x":
                if (result == 0) return null;
                direction = result > 0 ? Direction.EAST : Direction.WEST;
                break;
            case "y":
                if (result == 0) return null;
                direction = result > 0 ? Direction.UP : Direction.DOWN;
                break;
            case "z":
                if (result == 0) return null;
                direction = result > 0 ? Direction.SOUTH : Direction.NORTH;
                break;
        }
        return direction;
    }
}
