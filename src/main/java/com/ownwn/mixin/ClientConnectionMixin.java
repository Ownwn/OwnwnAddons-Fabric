package com.ownwn.mixin;

import com.ownwn.event.SendChatEvent;
import com.ownwn.OwnwnAddons;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
	@Inject(method = "sendImmediately", at = @At("HEAD"), cancellable = true)
	public void onSendPacket(Packet<?> packet, PacketCallbacks callbacks, CallbackInfo ci) { // called when the player sends something to the chat

		if (!(packet instanceof ChatMessageC2SPacket)) {
			return;
		}
			String message = ((ChatMessageC2SPacket) packet).chatMessage();
			SendChatEvent event = new SendChatEvent(message);
			OwnwnAddons.EVENT_BUS.post(event);

	}
}