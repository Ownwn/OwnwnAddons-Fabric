package com.ownwn;

import com.google.common.eventbus.Subscribe;
import com.ownwn.config.NewConfig;
import com.ownwn.event.ReceiveChatEvent;
import com.ownwn.event.SendChatEvent;
import com.ownwn.utils.UChat;

public class ChatMessageListener {

    @Subscribe
    public void onSendChat(SendChatEvent event) {

//        MinecraftClient.getInstance().player.sendMessage(Text.of("Sent:" + event.message()));
        if (event.message().equals(".toggle")) {

            if (NewConfig.optionFour.getValue()) {
                UChat.chat("its off");
            } else {
                UChat.chat("its off");
            }

        } else if (event.message().equals(".test")) {

            UChat.chat(NewConfig.optionFour.getValue());
            UChat.chat(NewConfig.hideExplosionParticles.getValue());





        }
    }
    @Subscribe
    public void onChat(ReceiveChatEvent event) {
//        MinecraftClient.getInstance().player.sendMessage(Text.empty().append("Received: ").append(event.message()));
        if (event.message().getString().contains("bad word")) {
            OwnwnAddons.EVENT_BUS.cancel(event, this);
        }
    }

}
