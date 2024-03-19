package xyz.koiro.watersource

import net.minecraft.data.client.Model
import net.minecraft.data.client.Models

enum class ModelType(val model: Model){
    Generated(Models.GENERATED),
}

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class AutoGenData(val modelType: ModelType = ModelType.Generated, val enLang: String, val cnLang: String)