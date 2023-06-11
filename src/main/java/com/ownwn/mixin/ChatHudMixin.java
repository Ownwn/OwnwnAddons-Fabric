package com.ownwn.mixin;

import com.ownwn.config.NewConfig;
import com.ownwn.features.ChatFilter;
import com.ownwn.utils.TextUtils;
import com.ownwn.utils.UChat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public class ChatHudMixin {
    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V", at = @At("HEAD"), cancellable = true)
    private void addMessage(Text message, MessageSignatureData signature, MessageIndicator indicator, CallbackInfo ci) {

        if (ChatFilter.shouldBlockMessage(message)) {
            ci.cancel();
            UChat.chat("Cancelled!");
        }
    }

    @ModifyVariable(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private Text modifyMessage(Text message) {

        if (!NewConfig.modifyPlayerName.getValue()) {
            return message;
        }


        assert MinecraftClient.getInstance().player != null;

        message = TextUtils.modifyTextColour(message, MinecraftClient.getInstance().player.getName(), 6941983);

        return message;
    }
}

