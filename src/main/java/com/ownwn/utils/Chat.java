package com.ownwn.utils;

import com.ownwn.OwnwnAddons;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class Chat {
    // Slightly simplifies sending messages to the player

    public static void chat(String message) {
        MinecraftClient.getInstance().player.sendMessage(Text.of(message));
    }

    public static void chat(Text message) {
        MinecraftClient.getInstance().player.sendMessage(message);
    }

    public static void chat(boolean message) {
        MinecraftClient.getInstance().player.sendMessage(Text.of(String.valueOf(message)));
    }

    public static void prefixInfo(String text) {
        chat(OwnwnAddons.Prefix().copy().append(Text.literal(" ")).append(Text.literal(text)).setStyle(Style.EMPTY.withColor(Formatting.GREEN)));
    }

    public static void prefixError(String text) {
        chat(OwnwnAddons.Prefix().copy().append(Text.literal(" ")).append(Text.literal(text).setStyle(Style.EMPTY.withColor(Formatting.RED))));

    }
}
