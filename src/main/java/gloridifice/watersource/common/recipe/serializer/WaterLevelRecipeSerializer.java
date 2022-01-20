package gloridifice.watersource.common.recipe.serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import gloridifice.watersource.common.recipe.WaterLevelAndEffectRecipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class WaterLevelRecipeSerializer<T extends WaterLevelAndEffectRecipe> extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<T> {


    public WaterLevelRecipeSerializer() {
    }

    public T fromJson(ResourceLocation recipeId, JsonObject json) {
        /* Default if values not be written
        * nbt null
        * fluid null
        * mobEffects List(Empty)
        * ingredient Ingredient.EMPTY
        * waterLevel 0
        * waterSaturationLevel 0
        * */
        int waterLevel = 0;
        int waterSaturationLevel = 0;
        Fluid fluid = null;
        CompoundTag compoundTag = null;
        List<MobEffectInstance> effectInstances = new ArrayList<>();

        String group = GsonHelper.getAsString(json, "group", "");

        //ingredient
        Ingredient ingredient = Ingredient.EMPTY;
        if (GsonHelper.isValidNode(json,"ingredient")) {
            JsonElement jsonelement = GsonHelper.isArrayNode(json, "ingredient") ? GsonHelper.getAsJsonArray(json, "ingredient") : GsonHelper.getAsJsonObject(json, "ingredient");
            ingredient = Ingredient.fromJson(jsonelement);
        }


        //mobEffects
        if (GsonHelper.isArrayNode(json ,"mob_effects")){
            JsonArray effectsJsonArray = GsonHelper.getAsJsonArray(json, "mob_effects");
            for (JsonElement effect : effectsJsonArray) {
                JsonObject mobEffectJsonObj = effect.getAsJsonObject();
                int duration = GsonHelper.getAsInt(mobEffectJsonObj, "duration");
                int amplifier = GsonHelper.getAsInt(mobEffectJsonObj, "amplifier");
                String name = GsonHelper.getAsString(mobEffectJsonObj, "name");
                if (duration > 0 && amplifier >= 0) {
                    MobEffect mobEffect = ForgeRegistries.MOB_EFFECTS.getValue(ResourceLocation.tryParse(name));
                    if (mobEffect != null) {
                        effectInstances.add(new MobEffectInstance(mobEffect, duration, amplifier));
                    }
                }
            }
        }


        //nbt
        if (GsonHelper.isValidNode(json ,"nbt")) {
            JsonObject nbt = GsonHelper.getAsJsonObject(json, "nbt");
            try {
                compoundTag = NbtUtils.snbtToStructure(nbt.toString());
            } catch (CommandSyntaxException e) {
                System.out.println(recipeId + ": no nbt.");
            }
            //todo debug
        }

        //fluid
        if (GsonHelper.isValidNode(json ,"fluid")){
            String fluidName = GsonHelper.getAsString(json, "fluid", "");
            fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidName));
        }

        //water level
        waterLevel = GsonHelper.getAsInt(json, "water_level", 0);
        waterSaturationLevel = GsonHelper.getAsInt(json, "water_saturation_level", 0);

        return (T) new WaterLevelAndEffectRecipe(recipeId, group, ingredient, waterLevel, waterSaturationLevel, effectInstances, fluid, compoundTag);
    }

    @Override
    public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf packetBuffer) {
        List<MobEffectInstance> mobEffectInstances = new ArrayList<>();
        String group = packetBuffer.readUtf();//1
        Ingredient ingredient = Ingredient.fromNetwork(packetBuffer);//2
        int waterLevel = packetBuffer.readVarInt();//3
        int waterSaturationLevel = packetBuffer.readVarInt();//4
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(packetBuffer.readUtf()));//5
        CompoundTag compoundTag = packetBuffer.readNbt();//6
        for (int i = 0; i < packetBuffer.readByte(); i++) {
            String mobEffectName = packetBuffer.readUtf();
            int duration = packetBuffer.readVarInt();
            int amplifier = packetBuffer.readVarInt();

            MobEffect mobEffect = ForgeRegistries.MOB_EFFECTS.getValue(ResourceLocation.tryParse(mobEffectName));
            mobEffectInstances.add(new MobEffectInstance(mobEffect, duration, amplifier));
        }
        return (T) new WaterLevelAndEffectRecipe(recipeId, group, ingredient, waterLevel, waterSaturationLevel, mobEffectInstances, fluid, compoundTag);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
        buffer.writeUtf(recipe.getGroup());
        recipe.getIngredient().toNetwork(buffer);
        buffer.writeVarInt(recipe.getWaterLevel());
        buffer.writeVarInt(recipe.getWaterSaturationLevel());
        buffer.writeUtf(recipe.getFluid() == null ? "" : recipe.getFluid().getRegistryName().toString());
        buffer.writeNbt(recipe.getCompoundTag());
        buffer.writeByte(recipe.getMobEffectInstances().size());
        for (MobEffectInstance mobEffectInstance : recipe.getMobEffectInstances()) {
            buffer.writeUtf(mobEffectInstance.getEffect().getRegistryName().toString());
            buffer.writeVarInt(mobEffectInstance.getDuration());
            buffer.writeVarInt(mobEffectInstance.getAmplifier());
        }
    }
}
