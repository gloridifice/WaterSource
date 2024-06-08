package xyz.koiro.watersource.render

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin
import net.minecraft.client.util.ModelIdentifier


object ModModels: ModelLoadingPlugin {
    fun initialize() {
        ModelLoadingPlugin.register(ModModels)
    }

    val NATURAL_STRAINER_MODEL_ID: ModelIdentifier = newStrainerModelId("natural_strainer")

    override fun onInitializeModelLoader(pluginContext: ModelLoadingPlugin.Context) {
        pluginContext.addModels(NATURAL_STRAINER_MODEL_ID)
    }

    /** Create model identifier with path watersource:models/strainer/${path} */
    fun newStrainerModelId(path: String): ModelIdentifier{
        return ModelIdentifier("watersource", "strainer/${path}", "")
    }
}