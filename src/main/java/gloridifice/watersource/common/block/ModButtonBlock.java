package gloridifice.watersource.common.block;

import net.minecraft.block.WoodButtonBlock;

public class ModButtonBlock extends WoodButtonBlock {
    public ModButtonBlock(String name,Properties properties) {
        super(properties);
        this.setRegistryName(name);
    }
}
