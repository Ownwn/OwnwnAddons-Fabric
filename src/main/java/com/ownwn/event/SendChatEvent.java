package com.ownwn.event;


public record SendChatEvent(String message) {
    public SendChatEvent {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
    }
}
