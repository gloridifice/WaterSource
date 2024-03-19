package xyz.koiro.watersource

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import xyz.koiro.watersource.datagen.HydrationDataGenerator
import xyz.koiro.watersource.datagen.ModChineseLangGenerator
import xyz.koiro.watersource.datagen.ModEnglishLangGenerator
import xyz.koiro.watersource.datagen.ModModelGenerator

object WaterSourceDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		val pack = fabricDataGenerator.createPack()
		pack.addProvider(::HydrationDataGenerator)
		pack.addProvider(::ModModelGenerator)
		pack.addProvider(::ModChineseLangGenerator)
		pack.addProvider(::ModEnglishLangGenerator)
	}
}