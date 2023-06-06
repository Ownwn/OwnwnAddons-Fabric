package com.ownwn.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class UChat {

    public static void chat(String message) {
        MinecraftClient.getInstance().player.sendMessage(Text.of(message));
    }
}
