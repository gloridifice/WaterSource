package gloridifice.watersource.registry;

import gloridifice.watersource.common.potion.NormalEffect;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class EffectRegistry extends RegistryModule{
    public static final Effect THIRST = new NormalEffect("thirst",EffectType.BENEFICIAL,0xE4D49F);
}
