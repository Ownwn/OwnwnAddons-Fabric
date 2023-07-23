package com.ownwn.event;

import meteordevelopment.orbit.ICancellable;

public class ClientSpeakEvent implements ICancellable {
    private boolean cancelled;
    private String message;

    public ClientSpeakEvent(String message) {
        this.message = message;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    public String getString() {
        return this.message;
    }

    public void setString(String message) {
        this.message = message;
    }
}
