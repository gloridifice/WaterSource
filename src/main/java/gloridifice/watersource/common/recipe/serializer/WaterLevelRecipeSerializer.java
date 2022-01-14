package gloridifice.watersource.common.recipe.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import gloridifice.watersource.common.recipe.WaterLevelItemRecipe;
import gloridifice.watersource.helper.FluidHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class WaterLevelRecipeSerializer<T extends WaterLevelItemRecipe> extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<T> {
    private final int waterLevel, waterSaturationLevel;
    private final WaterLevelRecipeSerializer.IFactory<T> factory;

    public WaterLevelRecipeSerializer(WaterLevelRecipeSerializer.IFactory<T> factory, int waterLevel, int waterSaturationLevel) {
        this.waterLevel = waterLevel;
        this.waterSaturationLevel = waterSaturationLevel;
        this.factory = factory;
    }

    public T fromJson(ResourceLocation recipeId, JsonObject json) {
        String group = GsonHelper.getAsString(json, "group", "");
        JsonElement jsonelement = GsonHelper.isArrayNode(json, "ingredient") ? GsonHelper.getAsJsonArray(json, "ingredient") : GsonHelper.getAsJsonObject(json, "ingredient");
        Ingredient ingredient = Ingredient.fromJson(jsonelement);
        String fluidName = GsonHelper.getAsString(json, "fluid", "");
        String nbt = GsonHelper.getAsString(json, "nbt", "");

        CompoundTag CompoundTag = null;
        try {
            CompoundTag = NbtUtils.snbtToStructure(nbt);
        }
        catch (CommandSyntaxException e) {
//            System.out.println("No NBT.");
        }
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidName));
        List<ItemStack> list = new ArrayList();
        for (ItemStack stack : ingredient.getItems()) {
            if (CompoundTag != null) stack.setTag(CompoundTag);
            list.add(FluidHelper.fillContainer(stack, fluid));
        }
        Ingredient ingredient1 = Ingredient.of(list.stream());
        int i = GsonHelper.getAsInt(json, "waterLevel", 2);
        int j = GsonHelper.getAsInt(json, "waterSaturationLevel", 2);
        return this.factory.create(recipeId, group, ingredient1, i, j);
    }

    @Override
    public T fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf packetBuffer) {
        String group = packetBuffer.readUtf(32767);
        Ingredient ingredient = Ingredient.fromNetwork(packetBuffer);
        int wa = packetBuffer.readVarInt();
        int waS = packetBuffer.readVarInt();

        return this.factory.create(resourceLocation, group, ingredient, wa, waS);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
        buffer.writeUtf(recipe.getGroup());
        recipe.getIngredient().toNetwork(buffer);
        buffer.writeVarInt(recipe.getWaterLevel());
        buffer.writeVarInt(recipe.getWaterSaturationLevel());
    }

    public interface IFactory<T extends WaterLevelItemRecipe> {
        T create(ResourceLocation resourceLocation, String group, Ingredient ingredient, int waterLevel, int waterSaturationLevel);
    }
}
