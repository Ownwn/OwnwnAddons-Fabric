package com.ownwn.mixin;

import com.ownwn.OwnwnAddons;
import com.ownwn.event.ClientSpeakEvent;
import com.ownwn.feature.DevTesting;
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
		DevTesting.test(packet);
		if (!(packet instanceof ChatMessageC2SPacket messagePacket)) {
			return;
		}

		String message = messagePacket.chatMessage();
		if (OwnwnAddons.EVENT_BUS.post(new ClientSpeakEvent(message)).isCancelled()) {
			ci.cancel();
		}

	}
}