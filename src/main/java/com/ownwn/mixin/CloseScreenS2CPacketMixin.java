package com.ownwn.mixin;


import com.ownwn.config.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.CloseScreenS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CloseScreenS2CPacket.class)
public class CloseScreenS2CPacketMixin {
    // original idea on 1.8 from Patcher https://github.com/Sk1erLLC/Patcher/blob/master/src/main/java/club/sk1er/patcher/mixins/features/network/packet/S2EPacketCloseWindowMixin_NoCloseMyChat.java
    @Inject(method = "apply(Lnet/minecraft/network/listener/ClientPlayPacketListener;)V", at = @At("HEAD"), cancellable = true)
    public void apply(ClientPlayPacketListener clientPlayPacketListener, CallbackInfo ci) {
        if (MinecraftClient.getInstance().currentScreen instanceof ChatScreen && Config.get().doNotCloseChat) {
            ci.cancel();
        }

    }

}
