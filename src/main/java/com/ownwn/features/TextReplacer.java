package com.ownwn.features;

import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class TextReplacer {
    public static GameMessageS2CPacket modifyGameMessage(GameMessageS2CPacket packet, String text) {
        String originalMessage = packet.content().getString();
        Text newText = Text.literal(text).setStyle(Style.EMPTY.withColor(6941983));

        if (originalMessage.contains(text)) {
            String[] parts = originalMessage.split(text);
            MutableText newMessage = Text.empty();

            for (int i = 0; i < parts.length; i++) {
                newMessage.append(parts[i]);
                if (i < parts.length - 1) { // If this is not the last part
                    newMessage.append(newText);
                }
            }

            if (parts.length <= 1) {
                // message is either **only** the text, or a single character followed by the text
                newMessage.append(newText);
            }

            return new GameMessageS2CPacket(newMessage, packet.overlay());
        }
        return packet;
    }

}
