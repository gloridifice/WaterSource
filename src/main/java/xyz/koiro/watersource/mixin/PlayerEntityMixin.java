package xyz.koiro.watersource.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.koiro.watersource.WaterSource;
import xyz.koiro.watersource.event.ModServerEvents;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(at = @At(value = "RETURN"), method = "jump()V")
    private void onJump(final CallbackInfo info){
        var player = (net.minecraft.entity.player.PlayerEntity) (Object) this;
        if (player instanceof ServerPlayerEntity) {
            ActionResult result = ModServerEvents.INSTANCE.getPLAYER_JUMP().invoker().interact(player);
        }
    }

    @Inject(at = @At(value = "INVOKE"), method = "readCustomDataFromNbt")
    private void onReadCustomNbt(final NbtCompound nbt, final CallbackInfo info){
        var player = (net.minecraft.entity.player.PlayerEntity) (Object) this;
        if (player instanceof ServerPlayerEntity serverPlayer) {
            ActionResult result = ModServerEvents.INSTANCE.getPLAYER_READ_CUSTOM_NBT().invoker().interact(serverPlayer, nbt);
        }
    }

    @Inject(at = @At(value = "INVOKE"), method = "writeCustomDataToNbt")
    private void onWriteCustomNbt(final NbtCompound nbt, final CallbackInfo info){
        var player = (net.minecraft.entity.player.PlayerEntity) (Object) this;
        if (player instanceof ServerPlayerEntity serverPlayer) {
            ActionResult result = ModServerEvents.INSTANCE.getPLAYER_WRITE_CUSTOM_NBT().invoker().interact(serverPlayer, nbt);
        }
    }
}
