package com.ownwn.features;


import net.minecraft.text.Text;

public class ChatFilter {

    public static boolean shouldBlockMessage(Text message) {
        String string = message.getString();
        return string.contains("bad word");
    }

    public static boolean shouldBlockMessage(String message) {
        return message.contains("aa");
    }

    
}
