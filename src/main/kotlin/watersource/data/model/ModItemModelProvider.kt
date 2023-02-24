package watersource.data.model

import net.minecraft.data.DataGenerator
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.client.model.generators.ModelProvider
import net.minecraftforge.common.data.ExistingFileHelper
import watersource.world.level.item.ModItems

class ModItemModelProvider(generator: DataGenerator?, modid: String?, existingFileHelper: ExistingFileHelper?) : ItemModelProvider(generator, modid, existingFileHelper) {
    override fun registerModels() {
        this.basicItem(ModItems.WOODEN_CUP)

    }
}