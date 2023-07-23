package com.ownwn.features;

import com.ownwn.config.Config;
import com.ownwn.event.ChatHudEvent;
import com.ownwn.utils.TextUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.List;

public class CustomName {

    @EventHandler
    public static void onChatHud(ChatHudEvent event) {
        if (!Config.get().customNameToggle) {
            return;
        }
        event.setText(replaceNameChat(event.getText()));
    }

    public static Text replaceNameChat(Text inputText) {
        assert MinecraftClient.getInstance().player != null;

        if (Config.get().nameType == Config.NameType.COLOUR_SPECTRUM) {
            return TextUtils.modifyText(inputText, MinecraftClient.getInstance().player.getName(), TextUtils.solidTextSpectrum(Text.literal(Config.get().customNameString)));
        }

        return TextUtils.modifyText(inputText, MinecraftClient.getInstance().player.getName(), Text.literal(Config.get().customNameString).setStyle(Style.EMPTY.withColor(Config.get().customNameColour)));

    }

    public static List<PlayerListEntry> editPlayerList(List<PlayerListEntry> originalList) {


        // TODO: Make this work :(
        assert MinecraftClient.getInstance().player != null;
        for (PlayerListEntry entry : originalList) {

            if (!entry.getProfile().getName().contains(MinecraftClient.getInstance().player.getName().getString())) {
                continue;
            }

            System.out.println(entry.getDisplayName());
            System.out.println(entry.getProfile().getName());

        }
        return originalList;
    }}
