package com.ownwn.mixin;

import com.ownwn.OwnwnAddons;
import com.ownwn.event.ChatEvent;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ChatHud.class, priority = 1200) // higher priority number = applied later. since we're cancelling/editing messages, it's a good idea to do this after other mods interact with the messages.
public class ChatHudMixin {
    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V", at = @At("HEAD"), cancellable = true)
    private void addMessage(Text message, MessageSignatureData signature, MessageIndicator indicator, CallbackInfo ci) {
        if (OwnwnAddons.EVENT_BUS.post(new ChatEvent.CancelEvent(message)).isCancelled()) {
            ci.cancel();
        }
    }

    @ModifyVariable(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private Text modifyMessage(Text message) {
        return OwnwnAddons.EVENT_BUS.post(new ChatEvent.ModifyEvent(message)).getText();
    }

    // taken from https://github.com/JackFred2/MoreChatHistory/blob/main/src/main/java/red/jackf/morechathistory/mixins/MixinChatHud.java
    // TODO: delete this before making a release, it's just here to make development easier
    @ModifyConstant(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", constant = @Constant(intValue = 100), expect = 2)
    public int changeChatHistory(int original) {
        return 16384;
    }
}

