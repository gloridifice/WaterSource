package gloridifice.watersource.common.item;

import gloridifice.watersource.registry.GroupRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ModNormalBlockItem extends BlockItem {
    public ModNormalBlockItem(Block blockIn) {
        super(blockIn, new Item.Properties().group(GroupRegistry.waterSourceGroup));
        this.setRegistryName(blockIn.getRegistryName());
    }
    public ModNormalBlockItem(Block blockIn,String name) {
        super(blockIn, new Item.Properties().group(GroupRegistry.waterSourceGroup));
        this.setRegistryName(name);
    }
    public ModNormalBlockItem(Block blockIn,Properties properties) {
        super(blockIn, properties);
        this.setRegistryName(blockIn.getRegistryName());
    }
    public ModNormalBlockItem(Block blockIn,Properties properties,String name) {
        super(blockIn, properties);
        this.setRegistryName(name);
    }
}
