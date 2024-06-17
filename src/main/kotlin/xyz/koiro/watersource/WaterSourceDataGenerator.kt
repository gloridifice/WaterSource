package xyz.koiro.watersource

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import xyz.koiro.watersource.datagen.*

object WaterSourceDataGenerator : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
        val pack = fabricDataGenerator.createPack()
        pack.addProvider(::HydrationDataGenerator)
        pack.addProvider(::FilterRecipeDataGenerator)
        pack.addProvider(::ModModelGenerator)
        pack.addProvider(::ModChineseLangGenerator)
        pack.addProvider(::ModEnglishLangGenerator)
        pack.addProvider(::ModItemTagGenerator)
        pack.addProvider(::ModFluidTagGenerator)
        pack.addProvider(::ModRecipeGenerator)
        pack.addProvider(::ModBlockLootTableGenerator)
    }
}