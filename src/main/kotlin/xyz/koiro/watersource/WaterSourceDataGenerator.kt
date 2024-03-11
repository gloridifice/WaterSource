package xyz.koiro.watersource

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import xyz.koiro.watersource.datagen.HydrationDataGenerator

object WaterSourceDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		fabricDataGenerator.createPack().addProvider(::HydrationDataGenerator)
	}
}