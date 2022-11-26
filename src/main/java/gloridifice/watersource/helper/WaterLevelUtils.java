package gloridifice.watersource.helper;

import gloridifice.watersource.common.recipe.IThirstRecipe;
import gloridifice.watersource.common.recipe.ModRecipeManager;
import gloridifice.watersource.common.recipe.WaterLevelAndEffectRecipe;
import gloridifice.watersource.registry.CapabilityRegistry;
import gloridifice.watersource.registry.EffectRegistry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Random;


public class WaterLevelUtils {
    public static void drink(Player player, ItemStack stack){
        Level level = player.getLevel();
        Random rand = new Random();

        WaterLevelAndEffectRecipe wRecipe = ModRecipeManager.getWERecipeFromItem(level, stack);
        IThirstRecipe tRecipe = ModRecipeManager.getThirstRecipeFromItem(level, stack);
        if (wRecipe != null) {
            player.getCapability(CapabilityRegistry.PLAYER_WATER_LEVEL).ifPresent(data -> {
                if (player.getRemainingFireTicks() > 0 && wRecipe.getWaterLevel() >= 4) {//extinguish player
                    if (!player.getLevel().isClientSide()) {
                        data.addWaterLevel(player, wRecipe.getWaterLevel() - 4);
                        if (tRecipe == null) {
                            data.addWaterSaturationLevel(player, Math.max(wRecipe.getWaterSaturationLevel() - 4, 0));
                        }
                    }
                    player.playSound(SoundEvents.FIRE_EXTINGUISH, 1.0F, 1.0F);
                    player.clearFire();
                } else {//add water level
                    data.addWaterLevel(player, wRecipe.getWaterLevel());
                    if (tRecipe == null) {
                        data.addWaterSaturationLevel(player, wRecipe.getWaterSaturationLevel());
                    }
                }
            });
            for (MobEffectInstance mobEffectInstance : wRecipe.getMobEffectInstances()) {
                player.addEffect(mobEffectInstance);
            }
        }
        if (tRecipe != null) {
            if (rand.nextDouble() < tRecipe.getProbability()) {
                player.addEffect(new MobEffectInstance(EffectRegistry.THIRST.get(), tRecipe.getDuration(), tRecipe.getAmplifier()));
            }
        }
    }
}
