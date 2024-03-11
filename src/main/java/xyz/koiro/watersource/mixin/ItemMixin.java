package xyz.koiro.watersource.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.koiro.watersource.event.ModServerEvents;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(at = @At("RETURN"), method = "finishUsing")
    private void finishUsing(final ItemStack stack, final World world, final LivingEntity user, final CallbackInfo info){
        if (world instanceof ServerWorld serverWorld){
            ActionResult result = ModServerEvents.INSTANCE.getFINISH_USING_ITEM().invoker().interact(user, serverWorld, stack);
            if(result == ActionResult.FAIL) {
                info.cancel();
            } else info.cancel();
        }
    }
}
