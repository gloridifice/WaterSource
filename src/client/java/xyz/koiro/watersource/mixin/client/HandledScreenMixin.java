package xyz.koiro.watersource.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.koiro.watersource.event.ModClientEvents;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;Ljava/util/Optional;II)V", shift = At.Shift.AFTER), method = "drawMouseoverTooltip")
    private void drawMouseoverTooltip(DrawContext context, int x, int y, CallbackInfo info, @Local ItemStack itemStack) {
        ModClientEvents.INSTANCE.getDRAW_MOUSEOVER_TOOLTIP().invoker().interact(context, x, y, itemStack);
    }
}