package com.ownwn.event;

import net.minecraft.text.Text;

public record ReceiveChatEvent(Text message) {
    public ReceiveChatEvent {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
    }
}
