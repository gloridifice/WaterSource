package watersource.data.tag.provider

import net.minecraft.data.DataGenerator
import net.minecraft.data.tags.BlockTagsProvider
import net.minecraft.data.tags.ItemTagsProvider
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.common.data.ForgeItemTagsProvider
import watersource.data.tag.ModTags
import watersource.world.level.item.ModItems

class ModItemTagProvider(
    generator: DataGenerator,
    blockTagsProvider: BlockTagsProvider,
    modId: String,
    existingFileHelper: ExistingFileHelper?
) : ItemTagsProvider(generator, blockTagsProvider, modId, existingFileHelper) {
    override fun addTags() {
        tag(ModTags.Item.DRINKABLE).add(ModItems.WOODEN_CUP)
    }
}