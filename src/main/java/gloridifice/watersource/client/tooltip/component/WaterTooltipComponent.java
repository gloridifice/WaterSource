package gloridifice.watersource.client.tooltip.component;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class WaterTooltipComponent implements TooltipComponent {
    public ItemStack itemStack;
    public int width,height;
    public WaterTooltipComponent(ItemStack itemStack, int width, int height){
        this.itemStack= itemStack;
        this.width = width;
        this.height = height;
    }
}
