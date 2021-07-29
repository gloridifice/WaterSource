package gloridifice.watersource.common.recipe.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import gloridifice.watersource.common.recipe.WaterLevelItemRecipe;
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

public class WaterLevelRecipeSerializer<T extends WaterLevelItemRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {
    private final int waterLevel, waterSaturationLevel;
    private final WaterLevelRecipeSerializer.IFactory<T> factory;

    public WaterLevelRecipeSerializer(WaterLevelRecipeSerializer.IFactory<T> factory, int waterLevel, int waterSaturationLevel) {
        this.waterLevel = waterLevel;
        this.waterSaturationLevel = waterSaturationLevel;
        this.factory = factory;
    }

    public T read(ResourceLocation recipeId, JsonObject json) {
        String group = JSONUtils.getString(json, "group", "");
        JsonElement jsonelement = JSONUtils.isJsonArray(json, "ingredient") ? JSONUtils.getJsonArray(json, "ingredient") : JSONUtils.getJsonObject(json, "ingredient");
        Ingredient ingredient = Ingredient.deserialize(jsonelement);
        String fluidName = JSONUtils.getString(json, "fluid", "");
        String nbt = JSONUtils.getString(json, "nbt", "");

        CompoundNBT compoundNBT = null;
        try {
            compoundNBT = JsonToNBT.getTagFromJson(nbt);
        }
        catch (CommandSyntaxException e) {
//            System.out.println("No NBT.");
        }
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidName));
        List<ItemStack> list = new ArrayList();
        for (ItemStack stack : ingredient.getMatchingStacks()) {
            if (compoundNBT != null) stack.setTag(compoundNBT);
            list.add(FluidHelper.fillContainer(stack, fluid));
        }
        Ingredient ingredient1 = Ingredient.fromStacks(list.stream());
        int i = JSONUtils.getInt(json, "waterLevel", 2);
        int j = JSONUtils.getInt(json, "waterSaturationLevel", 2);
        return this.factory.create(recipeId, group, ingredient1, i, j);
    }

    @Override
    public T read(ResourceLocation resourceLocation, PacketBuffer packetBuffer) {
        String group = packetBuffer.readString(32767);
        Ingredient ingredient = Ingredient.read(packetBuffer);
        int wa = packetBuffer.readVarInt();
        int waS = packetBuffer.readVarInt();

        return this.factory.create(resourceLocation, group, ingredient, wa, waS);
    }

    public void write(PacketBuffer buffer, T recipe) {
        buffer.writeString(recipe.getGroup());
        recipe.getIngredient().write(buffer);
        buffer.writeVarInt(recipe.getWaterLevel());
        buffer.writeVarInt(recipe.getWaterSaturationLevel());
    }

    public interface IFactory<T extends WaterLevelItemRecipe> {
        T create(ResourceLocation resourceLocation, String group, Ingredient ingredient, int waterLevel, int waterSaturationLevel);
    }
}
