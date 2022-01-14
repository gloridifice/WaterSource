package gloridifice.watersource.common.block;

import net.minecraft.world.level.block.DoorBlock;

public class PalmTreeDoorBlock extends DoorBlock {
    public PalmTreeDoorBlock(String name, Properties builder) {
        super(builder);
        this.setRegistryName(name);
    }
}
