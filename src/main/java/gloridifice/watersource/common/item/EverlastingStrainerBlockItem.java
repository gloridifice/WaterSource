package gloridifice.watersource.common.item;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.text.*;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class EverlastingStrainerBlockItem extends StrainerBlockItem{
    public EverlastingStrainerBlockItem(Block block) {
        super(block);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 1000;
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (getDamage(stack) < getMaxDamage(stack)) setDamage(stack, getMaxDamage());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("info.watersource.everlastingstrainer").setStyle(Style.EMPTY.setItalic(true).setColor(Color.fromTextFormatting(TextFormatting.GRAY))));
    }
}
