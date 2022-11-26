package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.potion.NormalMobEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EffectRegistry{

    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, WaterSource.MODID);

    public static final RegistryObject<MobEffect> THIRST =
            MOB_EFFECTS.register("thirst",
                    () -> new NormalMobEffect(MobEffectCategory.BENEFICIAL, 0xE4D49F));

    public static final RegistryObject<MobEffect> ACCOMPANYING_SOUL =
            MOB_EFFECTS.register("accompanying_soul",
                    () -> new NormalMobEffect(MobEffectCategory.BENEFICIAL, 0x634C3E));
    public static final RegistryObject<MobEffect> WATER_RESTORING =
            MOB_EFFECTS.register("water_restoring",
                    () -> new NormalMobEffect(MobEffectCategory.BENEFICIAL, 0x379AD6));
}
