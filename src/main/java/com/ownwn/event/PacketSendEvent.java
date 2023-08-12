package com.ownwn.event;

import meteordevelopment.orbit.ICancellable;
import net.minecraft.network.packet.Packet;

public class PacketSendEvent implements ICancellable {
    private boolean cancelled;
    private Packet<?> packet;

    public PacketSendEvent(Packet<?> packet) {
        this.packet = packet;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    public Packet<?> getPacket() {
        return this.packet;
    }
}
