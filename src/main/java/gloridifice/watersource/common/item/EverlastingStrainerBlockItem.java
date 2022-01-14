package gloridifice.watersource.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

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
    public void inventoryTick(ItemStack stack, Level level, Entity entityIn, int itemSlot, boolean isSelected) {
        if (getDamage(stack) < getMaxDamage(stack)) setDamage(stack, getMaxDamage());
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(new TranslatableComponent("info.watersource.everlastingstrainer").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY).withItalic(true)));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }
}
