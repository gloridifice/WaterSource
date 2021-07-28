package gloridifice.watersource.registry;

import gloridifice.watersource.common.potion.NormalEffect;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class EffectRegistry extends RegistryModule {
    public static final Effect THIRST = new NormalEffect("thirst", EffectType.BENEFICIAL, 0xE4D49F);
    public static final Effect ACCOMPANYING_SOUL = new NormalEffect("accompanying_soul", EffectType.BENEFICIAL, 0x634C3E);
    public static final Effect WATER_RESTORING = new NormalEffect("water_restoring", EffectType.BENEFICIAL, 0x379AD6);
}
