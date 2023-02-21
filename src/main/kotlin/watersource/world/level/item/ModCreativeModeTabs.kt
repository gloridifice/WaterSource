package watersource.world.level.item

import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraftforge.eventbus.api.SubscribeEvent
import watersource.world.level.block.ModBlocks


object ModCreativeModeTabs {
    val TAB_WATERSOURCE: CreativeModeTab = object : CreativeModeTab("watersource") {
        override fun makeIcon(): ItemStack = ItemStack(ModBlocks.WATER_FILTER_BLOCK.asItem())
    }
}
