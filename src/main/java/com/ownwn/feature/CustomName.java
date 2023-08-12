package com.ownwn.feature;

import com.ownwn.config.Config;
import com.ownwn.event.ChatEvent;
import com.ownwn.event.DrawTooltipEvent;
import com.ownwn.util.TextUtils;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class CustomName {

    @EventHandler(priority = EventPriority.LOW)
    public static void onChatHud(ChatEvent.ModifyEvent event) {
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
        assert MinecraftClient.getInstance().player  != null;
        for (PlayerListEntry entry : originalList) {

            if (!entry.getProfile().getName().contains(MinecraftClient.getInstance().player.getName().getString())) {
                continue;
            }

            System.out.println(entry.getDisplayName());
            System.out.println(entry.getProfile().getName());

        }
        return originalList;
    }

    @EventHandler
    public static void drawTooltip(DrawTooltipEvent event) {

        List<Text> newList = new ArrayList<>();
        assert MinecraftClient.getInstance().player != null;
        Text playerName = MinecraftClient.getInstance().player.getName();

        for (Text insideText : event.getText()) {
            insideText = TextUtils.modifyText(insideText, playerName, TextUtils.dynamicTextSpectrum(Config.get().customNameString));
            newList.add(insideText);
        }

        event.setText(newList);
    }
}


