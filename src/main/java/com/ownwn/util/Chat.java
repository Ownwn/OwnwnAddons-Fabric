package com.ownwn.util;

import com.ownwn.OwnwnAddons;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class Chat {
    // Slightly simplifies sending messages to the player

    public static void chat(String message) {
        internalMessage(Text.of(message));
    }

    public static void chat(Text message) {
        internalMessage(message);
    }

    public static void chat(boolean message) {
        internalMessage(Text.of(String.valueOf(message)));
    }

    public static void prefixInfo(String text) {
        chat(OwnwnAddons.Prefix().copy().append(Text.literal(" ")).append(Text.literal(text).setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
    }

    public static void prefixError(String text) {
        chat(OwnwnAddons.Prefix().copy().append(Text.literal(" ")).append(Text.literal(text).setStyle(Style.EMPTY.withColor(Formatting.RED))));

    }

    private static void internalMessage(Text message) {
        if (MinecraftClient.getInstance().player == null) {
            return;
        }
        MinecraftClient.getInstance().player.sendMessage(message);
    }
}
