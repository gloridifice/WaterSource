package gloridifice.watersource.common.recipe.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gloridifice.watersource.common.recipe.ThirstItemRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ThirstItemRecipeSerializer<T extends ThirstItemRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T>{
    private final int duration,amplifier,probability;
    private final IFactory<T> factory;

    public ThirstItemRecipeSerializer(IFactory<T> factory, int duration,int amplifier,int probability)
    {
        this.probability = probability;
        this.duration = duration;
        this.amplifier = amplifier;
        this.factory = factory;
    }

    public T read(ResourceLocation recipeId, JsonObject json)
    {
        String group = JSONUtils.getString(json, "group", "");
        JsonElement jsonelement = JSONUtils.isJsonArray(json, "ingredient") ? JSONUtils.getJsonArray(json, "ingredient") : JSONUtils.getJsonObject(json, "ingredient");
        Ingredient ingredient = Ingredient.deserialize(jsonelement);
        int i = JSONUtils.getInt(json, "duration", this.duration);
        int j = JSONUtils.getInt(json, "amplifier", this.amplifier);
        int k = JSONUtils.getInt(json, "probability", this.probability);
        return this.factory.create(recipeId, group, ingredient, i, j, k);
    }

    @Override
    public T read(ResourceLocation resourceLocation, PacketBuffer packetBuffer) {
        String group = packetBuffer.readString(32767);
        Ingredient ingredient = Ingredient.read(packetBuffer);
        int du = packetBuffer.readVarInt();
        int am = packetBuffer.readVarInt();
        int pr = packetBuffer.readVarInt();
        return this.factory.create(resourceLocation,group,ingredient,du,am,pr);
    }

    public void write(PacketBuffer buffer, T recipe)
    {
        buffer.writeItemStack(recipe.getItemStack());
        buffer.writeVarInt(recipe.getDuration());
        buffer.writeVarInt(recipe.getAmplifier());
        buffer.writeVarInt(recipe.getProbability());
    }
    interface IFactory<T extends ThirstItemRecipe>
    {
        T create(ResourceLocation resourceLocation, String group, Ingredient ingredient, int duration,int amplifier,int probability);
    }
}
