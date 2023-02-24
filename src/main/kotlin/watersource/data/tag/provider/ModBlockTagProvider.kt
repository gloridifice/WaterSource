package watersource.data.tag.provider

import net.minecraft.data.DataGenerator
import net.minecraft.data.tags.BlockTagsProvider
import net.minecraftforge.common.data.ExistingFileHelper

class ModBlockTagProvider(generator: DataGenerator, modId: String, existingFileHelper: ExistingFileHelper?) :
    BlockTagsProvider(generator, modId, existingFileHelper) {
    override fun addTags() {

    }
}