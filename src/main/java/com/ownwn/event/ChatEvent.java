package com.ownwn.event;

import meteordevelopment.orbit.ICancellable;
import net.minecraft.text.Text;


public class ChatEvent {

    public static class ModifyEvent {
        private Text text;

        public ModifyEvent(Text text) {
            this.text = text;
        }

        public Text getText() {
            return this.text;
        }
        public String getString() {
            return this.text.getString();
        }

        public void setText(Text text) {
            this.text = text;
        }

        public String getUnformattedString() {
            return this.text.getString().replaceAll("§.", "");
        }

    }

    public static class CancelEvent implements ICancellable {
        private boolean cancelled;
        private final Text text;

        public CancelEvent(Text text) {
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

        public String getString() {
            return this.text.getString();
        }

        public String getUnformattedString() { // gets the message without legacy colour formatting
            return this.text.getString().replaceAll("§.", "");
        }
    }


}
