package xyz.koiro.watersource

import net.minecraft.data.client.Model
import net.minecraft.data.client.Models
import net.minecraft.item.Item
import net.minecraft.registry.tag.TagKey

enum class ModelType(val model: Model){
    DontGen(Models.GENERATED),
    Generated(Models.GENERATED),
}

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class AutoGenItemData(val modelType: ModelType = ModelType.Generated, val enLang: String, val cnLang: String, val tags: Array<String> = [])

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class AutoGenBlockData(val enLang: String, val cnLang: String, val genItemModel: Boolean)


