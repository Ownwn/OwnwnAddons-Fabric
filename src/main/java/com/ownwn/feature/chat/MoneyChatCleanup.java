package com.ownwn.feature.chat;

import com.ownwn.config.Config;
import com.ownwn.event.ChatEvent;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;

public class MoneyChatCleanup {
    @EventHandler(priority = EventPriority.LOW)
    public static void onChat(ChatEvent.CancelEvent event) {
        if (!Config.get().bazaarChatCleanup) {
            return;
        }
        String message = event.getString();

        if (bazaarMessages(message) || bankMessages(message)) {
            event.setCancelled(true);
        }
    }

    public static boolean bazaarMessages(String message) {
        if (!message.startsWith("[Bazaar]")) {
            return false;
        }

        return switch (message) {
            case "[Bazaar] Executing instant sell..." -> true;
            case "[Bazaar] Executing instant buy..." -> true;
            case "[Bazaar] Submitting buy order..." -> true;
            case "[Bazaar] Submitting sell offer..." -> true;
            case "[Bazaar] Claiming order..." -> true;
            case "[Bazaar] Putting goods in escrow..." -> true;
            default -> false;
        };
    }

    public static boolean bankMessages(String message) {

        return switch (message) {
            case "Depositing coins..." -> true;
            case "Withdrawing coins..." -> true;
            default -> false;
        };
    }
}
