package watersource.data.model

import net.minecraft.data.DataGenerator
import net.minecraftforge.client.model.generators.BlockStateProvider
import net.minecraftforge.common.data.ExistingFileHelper

class ModBlockStateProvider(gen: DataGenerator?, modid: String?, exFileHelper: ExistingFileHelper?) :
    BlockStateProvider(gen, modid, exFileHelper) {
    override fun registerStatesAndModels() {
        
    }
}