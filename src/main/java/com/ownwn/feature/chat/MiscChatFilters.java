package com.ownwn.feature.chat;

import com.ownwn.config.Config;
import com.ownwn.event.ChatEvent;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;

import java.util.regex.Pattern;

public class MiscChatFilters {
    static final Pattern guildExp = Pattern.compile("^You earned \\d+ GEXP(?: \\+ (?:\\d|,)+ Event EXP|) from playing SkyBlock!$");
    static final String[] abilityMessages = {
          "Your Implosion hit ",
          "Your Guided Sheep hit "
    };

    @EventHandler(priority = EventPriority.LOW)
    public static void onChat(ChatEvent.CancelEvent event) {
        String message = event.getString();

        if (Config.get().hideAbilityDamage) {
            for (String abilityMesage : abilityMessages) {
                if (message.startsWith(abilityMesage)) {
                    event.cancel();
                    return;
                }
            }

        }

        if (Config.get().hideGexpMessages && guildExp.matcher(message).find()) {
            event.cancel();
            return;
        }
        if (message.startsWith("Mage Milestone")) event.cancel();





    }
}
