package com.ownwn.feature;

import com.ownwn.OwnwnAddons;
import com.ownwn.event.ChatEvent;
import com.ownwn.event.ClientTickEvent;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.KeepAliveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class DevTesting {
    public static boolean devMode = false;

    public static List<Text> chatList = new ArrayList<>();

    @EventHandler
    public static void onTick(ClientTickEvent.TickClassic event) {
        if (true) return;
        if (!devMode) return;

        for (Text text : chatList) {
            OwnwnAddons.LOGGER.info(text.getString());
        }
        devMode = false;
    }



    @EventHandler(priority = EventPriority.HIGH)
    public static void onChat(ChatEvent.CancelEvent event) {
        Text message = event.getText();
//        Chat.chat(event.getString());
        chatList.remove(message);
        chatList.add(message);
        if (chatList.size() > 300) {
            chatList.remove(0);
        }
    }



    public static void test(Packet<?> packet) {
        if (packet instanceof PlayerMoveC2SPacket || packet instanceof KeepAliveC2SPacket) {
            return;
        }
//        if (packet instanceof CloseScreenS2CPacket) {
//            System.out.println(packet);
//        }
//        System.out.println(packet);
    }

}
