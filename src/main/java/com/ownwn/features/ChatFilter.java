package com.ownwn.features;


import com.ownwn.config.Config;
import net.minecraft.text.Text;

public class ChatFilter {

    public static boolean shouldBlockMessage(Text message) {
        String string = message.getString();
        if (string.contains("block this word! sdnfgsdngg")) {
            return true;
        }

        if (Config.get().hideEmptyMessages) {
            return (string.trim().isEmpty());
        }
        return false;
    }
    
}
