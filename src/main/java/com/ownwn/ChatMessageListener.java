package com.ownwn;

import com.google.common.eventbus.Subscribe;
import com.ownwn.event.ReceiveChatEvent;
import com.ownwn.event.SendChatEvent;
import com.ownwn.config.NewConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ChatMessageListener {

    @Subscribe
    public void onSendChat(SendChatEvent event) {

//        MinecraftClient.getInstance().player.sendMessage(Text.of("Sent:" + event.message()));
        if (event.message().equals(".toggle")) {
            boolean testing = false;
            boolean testing2 = true;
//            ConfigUtils.configMap.put("test", testing);
//            ConfigUtils.configMap.put("test2", testing2);
            if (NewConfig.optionFour.getValue()) {
                MinecraftClient.getInstance().player.sendMessage(Text.of("its on!"));
            } else {
                MinecraftClient.getInstance().player.sendMessage(Text.of("its off!"));
            }

        } else if (event.message().equals(".test")) {
//            ConfigOption.optionThree.setValue(!ConfigOption.optionThree.getValue());

        }
    }
    @Subscribe
    public void onChat(ReceiveChatEvent event) {
//        MinecraftClient.getInstance().player.sendMessage(Text.empty().append("Received: ").append(event.message()));
        if (event.message().getString().contains("bad word")) {
            OwnwnAddons.EVENT_BUS.cancel(event, this);
        }
    }

    @Subscribe
    public void onChatTwo(ReceiveChatEvent event) {
//        MinecraftClient.getInstance().player.sendMessage(Text.empty().append("Received 2: ").append(event.message()));
    }
}
