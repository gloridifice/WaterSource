package gloridifice.watersource.registry;

import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.potion.NormalMobEffect;


import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MobEffectRegistry {
    public static final DeferredRegister<MobEffect> MOD_MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, WaterSource.MODID);
    public static final RegistryObject<MobEffect> THIRST = MOD_MOB_EFFECTS.register("thirst",() -> new NormalMobEffect(MobEffectCategory.BENEFICIAL, 0xE4D49F));
    public static final RegistryObject<MobEffect> ACCOMPANYING_SOUL = MOD_MOB_EFFECTS.register("accompanying_soul",() -> new NormalMobEffect(MobEffectCategory.BENEFICIAL, 0x634C3E));
    public static final RegistryObject<MobEffect> WATER_RESTORING = MOD_MOB_EFFECTS.register("water_restoring",() -> new NormalMobEffect(MobEffectCategory.BENEFICIAL, 0x379AD6));

    public static RegistryObject<MobEffect> register(String name, MobEffect effect){
        return MOD_MOB_EFFECTS.register(name, () -> effect);
    }
}
