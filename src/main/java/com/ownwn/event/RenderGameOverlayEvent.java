package com.ownwn.event;

import net.minecraft.client.gui.DrawContext;

public class RenderGameOverlayEvent {
    public DrawContext context;

    public RenderGameOverlayEvent(DrawContext context) {
        this.context = context;
    }
}
