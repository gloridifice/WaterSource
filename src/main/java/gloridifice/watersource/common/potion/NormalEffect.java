package gloridifice.watersource.common.potion;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class NormalEffect extends Effect {
    public NormalEffect(String name,EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
        this.setRegistryName(name);
    }
}
