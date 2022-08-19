package gloridifice.watersource.client;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.client.color.item.FluidBottleColor;
import gloridifice.watersource.client.color.item.WoodenCupColor;
import gloridifice.watersource.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = WaterSource.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ColorRegistry {
    public static final ItemColor CUP_ITEM = new WoodenCupColor();
    public static final ItemColor FLUID_BOTTLE_ITEM = new FluidBottleColor();

    @SubscribeEvent
    public static void registerColors(ColorHandlerEvent.Item event) {
        event.getItemColors().register(CUP_ITEM, ItemRegistry.WOODEN_CUP_DRINK.get());
        event.getItemColors().register(FLUID_BOTTLE_ITEM, ItemRegistry.FLUID_BOTTLE.get());
    }
}
