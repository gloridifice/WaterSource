package gloridifice.watersource.common.recipe.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import gloridifice.watersource.common.recipe.ThirstItemRecipe;
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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ThirstItemRecipeSerializer<T extends ThirstItemRecipe> extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<T> {
    private final int duration, amplifier, probability;
    private final IFactory<T> factory;

    public ThirstItemRecipeSerializer(IFactory<T> factory, int duration, int amplifier, int probability) {
        this.probability = probability;
        this.duration = duration;
        this.amplifier = amplifier;
        this.factory = factory;
    }
    
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
        buffer.writeUtf(recipe.getGroup());
        recipe.getIngredient().toNetwork(buffer);
        buffer.writeVarInt(recipe.getDuration());
        buffer.writeVarInt(recipe.getAmplifier());
        buffer.writeVarInt(recipe.getProbability());
    }
    @Nullable
    @Override
    public T fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf byteBuf) {
        String group = byteBuf.readUtf(32767);
        Ingredient ingredient = Ingredient.fromNetwork(byteBuf);
        int du = byteBuf.readVarInt();
        int am = byteBuf.readVarInt();
        int pr = byteBuf.readVarInt();

        return this.factory.create(resourceLocation, group, ingredient, du, am, pr);

    }

    @Override
    public T fromJson(ResourceLocation recipeId, JsonObject json) {
        String group = GsonHelper.getAsString(json, "group", "");
        JsonElement jsonelement = GsonHelper.isArrayNode(json, "ingredient") ? GsonHelper.getAsJsonArray(json, "ingredient") : GsonHelper.getAsJsonObject(json, "ingredient");
        Ingredient ingredient = Ingredient.fromJson(jsonelement);
        String fluidName = GsonHelper.getAsString(json, "fluid", "");
        String nbtString = GsonHelper.getAsString(json, "nbt", "");

        CompoundTag CompoundTag = null;
        try {
            CompoundTag = NbtUtils.snbtToStructure(nbtString);
        }
        catch (CommandSyntaxException e) {
            System.out.println(recipeId + ": No NBT.");
        }
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidName));
        List<ItemStack> list = new ArrayList();
        for (ItemStack stack : ingredient.getItems()) {
            if (CompoundTag != null) stack.setTag(CompoundTag);
            list.add(FluidHelper.fillContainer(stack, fluid));
        }
        Ingredient ingredient1 = Ingredient.of(list.stream());
        int i = GsonHelper.getAsInt(json, "duration", 2000);
        int j = GsonHelper.getAsInt(json, "amplifier", 0);
        int k = GsonHelper.getAsInt(json, "probability", 75);
        return this.factory.create(recipeId, group, ingredient1, i, j, k);
    }
    

    public interface IFactory<T extends ThirstItemRecipe> {
        T create(ResourceLocation resourceLocation, String group, Ingredient ingredient, int duration, int amplifier, int probability);
    }

}
