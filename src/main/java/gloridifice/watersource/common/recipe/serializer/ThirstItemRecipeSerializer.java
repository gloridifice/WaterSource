package gloridifice.watersource.common.recipe.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import gloridifice.watersource.common.recipe.ThirstItemRecipe;
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

public class ThirstItemRecipeSerializer<T extends ThirstItemRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {
    private final int duration, amplifier, probability;
    private final IFactory<T> factory;

    public ThirstItemRecipeSerializer(IFactory<T> factory, int duration, int amplifier, int probability) {
        this.probability = probability;
        this.duration = duration;
        this.amplifier = amplifier;
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
            System.out.println("No NBT.");
        }
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidName));
        List<ItemStack> list = new ArrayList();
        for (ItemStack stack : ingredient.getMatchingStacks()) {
            if (compoundNBT != null) stack.setTag(compoundNBT);
            list.add(FluidHelper.fillContainer(stack, fluid));
        }
        Ingredient ingredient1 = Ingredient.fromStacks(list.stream());
        int i = JSONUtils.getInt(json, "duration", 2000);
        int j = JSONUtils.getInt(json, "amplifier", 0);
        int k = JSONUtils.getInt(json, "probability", 75);
        return this.factory.create(recipeId, group, ingredient1, i, j, k);
    }

    @Override
    public T read(ResourceLocation resourceLocation, PacketBuffer packetBuffer) {
        String group = packetBuffer.readString(32767);
        Ingredient ingredient = Ingredient.read(packetBuffer);
        int du = packetBuffer.readVarInt();
        int am = packetBuffer.readVarInt();
        int pr = packetBuffer.readVarInt();

        return this.factory.create(resourceLocation, group, ingredient, du, am, pr);
    }

    public void write(PacketBuffer buffer, T recipe) {
        buffer.writeString(recipe.getGroup());
        recipe.getIngredient().write(buffer);
        buffer.writeVarInt(recipe.getDuration());
        buffer.writeVarInt(recipe.getAmplifier());
        buffer.writeVarInt(recipe.getProbability());
    }

    public interface IFactory<T extends ThirstItemRecipe> {
        T create(ResourceLocation resourceLocation, String group, Ingredient ingredient, int duration, int amplifier, int probability);
    }

}
