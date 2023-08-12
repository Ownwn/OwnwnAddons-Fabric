package com.ownwn.feature.chat;

import com.ownwn.config.Config;
import com.ownwn.event.ChatEvent;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;

public class DungeonChatFilter {

    @EventHandler(priority = EventPriority.LOW)
    public static void onChat(ChatEvent.CancelEvent event) {
        if (!Config.get().dungeonChatCleanup) {
            return;
        }
        String message = event.getUnformattedString();

        boolean shouldCancel = switch (message) {
            case "Lost Adventurer used Dragon's Breath on you!" -> true;
            case "Used Healing Circle!" -> true;
            case "Healing Circle is now available!" -> true;

            default -> false;
        };

        if (shouldCancel) {
            event.setCancelled(true);
        }
    }
}
