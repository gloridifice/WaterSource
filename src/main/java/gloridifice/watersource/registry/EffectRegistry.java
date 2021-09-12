package gloridifice.watersource.registry;

import gloridifice.watersource.common.potion.NormalMobEffect;


import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class EffectRegistry extends RegistryModule {
    public static final MobEffect THIRST = new NormalMobEffect("thirst", MobEffectCategory.BENEFICIAL, 0xE4D49F);
    public static final MobEffect ACCOMPANYING_SOUL = new NormalMobEffect("accompanying_soul", MobEffectCategory.BENEFICIAL, 0x634C3E);
    public static final MobEffect WATER_RESTORING = new NormalMobEffect("water_restoring", MobEffectCategory.BENEFICIAL, 0x379AD6);
}
