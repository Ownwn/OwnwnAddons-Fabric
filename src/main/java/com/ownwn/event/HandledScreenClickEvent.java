package com.ownwn.event;

import meteordevelopment.orbit.ICancellable;
import net.minecraft.screen.slot.Slot;

public class HandledScreenClickEvent implements ICancellable {
    private boolean cancelled;
    private Slot slot;

    public HandledScreenClickEvent(Slot slot) {
        this.slot = slot;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    public Slot getSlot() {
        return this.slot;
    }
}
