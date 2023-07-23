package com.ownwn.features;

import com.ownwn.config.Config;
import com.ownwn.event.DrawTooltipEvent;
import com.ownwn.utils.TextUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ReplaceTooltip {

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
