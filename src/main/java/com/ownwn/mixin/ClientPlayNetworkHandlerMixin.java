package com.ownwn.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @ModifyVariable(method = "onGameMessage", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private GameMessageS2CPacket modifyGameMessage(GameMessageS2CPacket packet) {

//        MinecraftClient.getInstance().player.sendMessage(Text.literal("a").setStyle(Style.EMPTY.withColor(1)));
//        return TextReplacer.modifyGameMessage(packet, "Ownwn");
        return packet;


    }
}
