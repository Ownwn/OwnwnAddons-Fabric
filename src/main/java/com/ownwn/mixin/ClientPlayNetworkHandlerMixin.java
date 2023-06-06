package com.ownwn.mixin;

import com.ownwn.features.TextReplacer;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @ModifyVariable(method = "onGameMessage", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private GameMessageS2CPacket modifyGameMessage(GameMessageS2CPacket packet) {


        return TextReplacer.modifyGameMessage(packet, "Ownwn");

    }
}
