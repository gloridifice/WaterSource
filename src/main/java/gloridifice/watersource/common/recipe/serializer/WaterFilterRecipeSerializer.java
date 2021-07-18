package gloridifice.watersource.common.recipe.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import gloridifice.watersource.common.recipe.WaterFilterRecipe;
import gloridifice.watersource.helper.FluidHelper;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class WaterFilterRecipeSerializer<T extends WaterFilterRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {
    private final WaterFilterRecipeSerializer.IFactory<T> factory;
    private final Fluid inputFluid, outputFluid;

    public WaterFilterRecipeSerializer(IFactory<T> factory, Fluid inputFluid, Fluid outputFluid) {
        this.factory = factory;
        this.inputFluid = inputFluid;
        this.outputFluid = outputFluid;
    }

    public T read(ResourceLocation recipeId, JsonObject json) {
        String group = JSONUtils.getString(json, "group", "");
        JsonElement jsonelement = JSONUtils.isJsonArray(json, "strainerIngredient") ? JSONUtils.getJsonArray(json, "strainerIngredient") : JSONUtils.getJsonObject(json, "strainerIngredient");
        Ingredient ingredient = Ingredient.deserialize(jsonelement);
        String fluidName = JSONUtils.getString(json, "inputFluid", "");
        String fluidName1 = JSONUtils.getString(json, "outputFluid", "");
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidName));
        Fluid fluid1 = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidName1));
        return this.factory.create(recipeId, group, ingredient, fluid, fluid1);
    }

    @Override
    public T read(ResourceLocation resourceLocation, PacketBuffer packetBuffer) {
        String group = packetBuffer.readString(32767);
        Ingredient ingredient = Ingredient.read(packetBuffer);
        String input = packetBuffer.readString(19235);
        String output = packetBuffer.readString(19236);

        return this.factory.create(resourceLocation, group, ingredient, ForgeRegistries.FLUIDS.getValue(new ResourceLocation(input)), ForgeRegistries.FLUIDS.getValue(new ResourceLocation(output)));
    }

    public void write(PacketBuffer buffer, T recipe) {
        buffer.writeString(recipe.getGroup());
        recipe.getStrainerIngredient().write(buffer);
        buffer.writeString(recipe.getInputFluid().getRegistryName().toString(), 19235);
        buffer.writeString(recipe.getOutputFluid().getRegistryName().toString(), 19236);
    }

    public interface IFactory<T extends WaterFilterRecipe> {
        T create(ResourceLocation resourceLocation, String group, Ingredient ingredient, Fluid inputFluid, Fluid outputFluid);
    }
}
