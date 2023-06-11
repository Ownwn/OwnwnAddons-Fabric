package com.ownwn.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class UChat {
    // this is called UChat because I'm used to typing it

    public static void chat(String message) {
        MinecraftClient.getInstance().player.sendMessage(Text.of(message));
    }

    public static void chat(Text message) {
        MinecraftClient.getInstance().player.sendMessage(message);
    }

    public static void chat(boolean message) {
        MinecraftClient.getInstance().player.sendMessage(Text.of(String.valueOf(message)));
    }
}
