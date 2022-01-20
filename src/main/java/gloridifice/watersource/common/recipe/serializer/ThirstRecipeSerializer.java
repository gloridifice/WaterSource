package gloridifice.watersource.common.recipe.serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import gloridifice.watersource.common.recipe.ThirstRecipe;
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

public class ThirstRecipeSerializer<T extends ThirstRecipe> extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<T> {
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
        buffer.writeUtf(recipe.getGroup());
        recipe.getIngredient().toNetwork(buffer);
        buffer.writeVarInt(recipe.getDuration());
        buffer.writeVarInt(recipe.getAmplifier());
        buffer.writeFloat(recipe.getProbability());
        buffer.writeUtf(recipe.getFluid() == null ? "" : recipe.getFluid().getRegistryName().toString());
        buffer.writeNbt(recipe.getCompoundTag());
    }
    @Nullable
    @Override
    public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf byteBuf) {
        String group = byteBuf.readUtf();
        Ingredient ingredient = Ingredient.fromNetwork(byteBuf);
        int duration = byteBuf.readVarInt();
        int amplifier = byteBuf.readVarInt();
        float probability = byteBuf.readFloat();
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(byteBuf.readUtf()));
        CompoundTag compoundTag = byteBuf.readNbt();

        return (T) new ThirstRecipe(recipeId, group, duration, amplifier, probability, ingredient, fluid, compoundTag);

    }

    @Override
    public T fromJson(ResourceLocation recipeId, JsonObject json) {
        Fluid fluid = null;
        CompoundTag compoundTag = null;
        String group = GsonHelper.getAsString(json, "group", "");
        //fluid
        if (GsonHelper.isValidNode(json ,"fluid")) {
            String fluidName = GsonHelper.getAsString(json, "fluid", "");
            fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidName));
        }
        //nbt
        if (GsonHelper.isValidNode(json ,"nbt")) {
            JsonObject nbt = GsonHelper.getAsJsonObject(json, "nbt");
            try {
                compoundTag = NbtUtils.snbtToStructure(nbt.toString());
            } catch (CommandSyntaxException e) {
                System.out.println(recipeId + ": no nbt.");
            }
        }
        //ingredient
        Ingredient ingredient = Ingredient.EMPTY;
        if (GsonHelper.isValidNode(json,"ingredient")) {
            JsonElement jsonelement = GsonHelper.isArrayNode(json, "ingredient") ? GsonHelper.getAsJsonArray(json, "ingredient") : GsonHelper.getAsJsonObject(json, "ingredient");
            ingredient = Ingredient.fromJson(jsonelement);
        }

        int duration = GsonHelper.getAsInt(json, "duration", 2000);
        int amplifier = GsonHelper.getAsInt(json, "amplifier", 0);
        float probability = GsonHelper.getAsFloat(json, "probability", 0.75f);

        return (T) new ThirstRecipe(recipeId, group, duration, amplifier, probability, ingredient, fluid, compoundTag);
    }

}
