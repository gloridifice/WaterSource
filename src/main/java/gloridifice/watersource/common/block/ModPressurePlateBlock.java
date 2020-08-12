package gloridifice.watersource.common.block;

import net.minecraft.block.PressurePlateBlock;

public class ModPressurePlateBlock extends PressurePlateBlock {
    public ModPressurePlateBlock(String name,Sensitivity sensitivityIn, Properties propertiesIn) {
        super(sensitivityIn, propertiesIn);
        this.setRegistryName(name);
    }
}
