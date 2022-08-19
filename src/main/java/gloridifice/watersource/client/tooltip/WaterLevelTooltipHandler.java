package gloridifice.watersource.client.tooltip;

import com.mojang.datafixers.util.Either;
import gloridifice.watersource.WaterSource;
import gloridifice.watersource.client.tooltip.component.WaterTooltipComponent;
import gloridifice.watersource.common.recipe.ModRecipeManager;
import gloridifice.watersource.common.recipe.WaterLevelAndEffectRecipe;
import gloridifice.watersource.data.ModItemTags;
import gloridifice.watersource.registry.ConfigRegistry;
import net.minecraft.client.Minecraft;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = WaterSource.MODID)
public class WaterLevelTooltipHandler {
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onRenderTooltipEvent(RenderTooltipEvent.GatherComponents event) {
        int x = 1;
        if (ConfigRegistry.OPEN_FOOD_WATER_LEVEL.get()) {
            Minecraft mc = Minecraft.getInstance();
            ItemStack stack= event.getItemStack();
            if (mc.level != null){
                WaterLevelAndEffectRecipe recipe = ModRecipeManager.getWERecipeFromItem(mc.level, stack);
                TagKey<Item> dItemTag = ModItemTags.DRINKABLE_CONTAINERS;
                if (recipe != null && (event.getItemStack().is(dItemTag) || !FluidUtil.getFluidHandler(stack).isPresent())) {
                    int max = Math.max(recipe.getWaterLevel(), recipe.getWaterSaturationLevel());
                    int width = (int) Math.ceil((double) max / 2) * 9 + 1;
                    //if (max > 8) width = 28;
                    event.getTooltipElements().add(x, Either.right(new WaterTooltipComponent(stack, width, 10)));
                }
            }
        }
    }
}
