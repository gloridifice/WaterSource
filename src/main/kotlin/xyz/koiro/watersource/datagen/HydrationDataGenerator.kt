package xyz.koiro.watersource.datagen

import net.minecraft.data.DataOutput
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.Items
import net.minecraft.util.Identifier
import xyz.koiro.watersource.WaterSource
import xyz.koiro.watersource.data.HydrationData
import xyz.koiro.watersource.datagen.provider.HydrationDataProvider

class HydrationDataGenerator(output: DataOutput): HydrationDataProvider(output) {
    override fun addData(dataMap: HashMap<Identifier, HydrationData>) {
        dataMap[WaterSource.identifier("apple")] = item(Items.APPLE, 1, 1, StatusEffectInstance(StatusEffects.LUCK, 100))
    }
}