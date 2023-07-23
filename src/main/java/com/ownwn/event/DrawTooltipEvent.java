package com.ownwn.event;

import meteordevelopment.orbit.ICancellable;
import net.minecraft.text.Text;

import java.util.List;

public class DrawTooltipEvent implements ICancellable {
    private boolean cancelled;
    private List<Text> textList;

    public DrawTooltipEvent(List<Text> textList) {
        this.textList = textList;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    public List<Text> getText() {
        return this.textList;
    }

    public void setText(List<Text> textList) {
        this.textList = textList;
    }
}
