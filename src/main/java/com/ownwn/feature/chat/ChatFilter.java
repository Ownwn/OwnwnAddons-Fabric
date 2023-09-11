package com.ownwn.feature.chat;


import com.ownwn.config.Config;
import com.ownwn.event.ChatEvent;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;

public class ChatFilter {

    @EventHandler(priority = EventPriority.LOW)
    public static void onChat(ChatEvent.CancelEvent event) {
        if (Config.get().hideEmptyMessages && event.getString().trim().isEmpty()) {
            event.cancel();
        }
    }
    
}
