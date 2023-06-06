package com.ownwn.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ProgressScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    private boolean skipJoinWorldScreen = true;

    @Redirect(method = "joinWorld", at = @At(value = "NEW", target = "net/minecraft/client/gui/screen/ProgressScreen"))
    private ProgressScreen modifyProgressScreen(boolean darkenSky) {
        if (!skipJoinWorldScreen) {
            return null; // skip creation of ProgressScreen
        } else {
            ProgressScreen progressScreen = new ProgressScreen(darkenSky);
            progressScreen.setTask(Text.empty()); // set an empty handler to hide the screen
            return progressScreen;
        }
    }

    @Redirect(method = "joinWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ProgressScreen;setTitle(Lnet/minecraft/text/Text;)V"))
    private void modifyProgressScreenTitle(ProgressScreen progressScreen, Text title) {
        if (!skipJoinWorldScreen) {
            progressScreen.setTitle(title);
        }
    }
}
