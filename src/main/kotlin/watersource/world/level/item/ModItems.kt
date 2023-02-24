package watersource.world.level.item

import net.minecraft.world.item.Item
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.registerObject
import watersource.WaterSource

object ModItems {
    val REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, WaterSource.ID)

    val WOODEN_CUP by REGISTRY.registerObject("wooden_cup"){Item(Item.Properties().tab(ModCreativeModeTabs.TAB_WATERSOURCE))}
}