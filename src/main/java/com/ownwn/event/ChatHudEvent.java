package com.ownwn.event;

import meteordevelopment.orbit.ICancellable;
import net.minecraft.text.Text;


public class ChatHudEvent implements ICancellable {
    /* TODO: Seperate this into different events, currently this event is called on:
    - Server messages
    - Messages from other players
    - Messages from the the client to itself
    */
    private boolean cancelled;
    private Text text;

    public ChatHudEvent(Text text) {
        this.text = text;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    public Text getText() {
        return this.text;
    }

    public void setText(Text text) {
        this.text = text;
    }
}
