package xyz.koiro.watersource.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import xyz.koiro.watersource.world.item.ModItems
import xyz.koiro.watersource.world.tag.ModTags
import java.util.concurrent.CompletableFuture

class ModItemTagGenerator(
    output: FabricDataOutput?,
    completableFuture: CompletableFuture<RegistryWrapper.WrapperLookup>?
) :
    ItemTagProvider(output, completableFuture) {
    override fun configure(arg: RegistryWrapper.WrapperLookup?) {
        ModItems.reflectAutoGenDataItems().forEach {
            it.second.tags.forEach { id ->
                getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, Identifier(id))).add(it.first)
            }
        }

        getOrCreateTagBuilder(ModTags.Item.PURIFICATION_STRAINER)
            .add(ModItems.PAPER_STRAINER)
            .add(ModItems.NATURAL_STRAINER)

        getOrCreateTagBuilder(ModTags.Item.BASICS_INGOT)
            .add(Items.IRON_INGOT)
            .add(Items.COPPER_INGOT)
    }
}
