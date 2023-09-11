package com.ownwn.feature.chat;

import com.ownwn.config.Config;
import com.ownwn.event.ChatEvent;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;

import java.util.regex.Pattern;

public class DungeonChatFilter {
    static final Pattern blessingName = Pattern.compile("^.{1,30} has obtained Blessing of ");
    static final Pattern blessingContent = Pattern.compile("^(?:Granted|Also granted) you \\+(?:\\d|\\.)+ & \\+(?:\\d|\\.)+x");
    static final Pattern trapMessages = Pattern.compile("^(?:The Flamethrower hit you for |The Arrow Trap hit you for |The Tripwire Trap hit you for )");

    static final String[] uselessTriviaMessages = {
            "I am Oruo the Omniscient. I have lived many lives. I have learned all there is to know.",
            "Though I sit stationary in this prison that is The Catacombs, my knowledge knows no bounds.",
            "Prove your knowledge by answering 3 questions and I shall reward you in ways that transcend time!",
            "Answer incorrectly, and your moment of ineptitude will live on for generations."
    };

    @EventHandler(priority = EventPriority.LOW)
    public static void onChat(ChatEvent.CancelEvent event) {
        String message = event.getString().trim();

        if (checkDungeonTrapMessage(message)) {
            event.cancel();
            return;
        }

        if (checkMiscMessages(message)) {
            event.cancel();
            return;
        }

        if (checkBlessingMessage(message)) {
            event.cancel();
            return;
        }

        if (checkTriviaMessages(message)) {
            event.cancel();
            return;
        }

        if (checkAbilityMessages(message)) {
            event.cancel();
            return;
        }

        if (checkWatcherMessages(message)) {
            event.cancel();
            return;
        }

        if (checkMortMessages(message)) {
            event.cancel();
            return;
        }
    }

    private static boolean checkTriviaMessages(String message) {
        if (!Config.get().hideUselessOruo) return false;
        if (!message.startsWith("[STATUE] Oruo the Omniscient: ")) return false;

        String trimmedMessage = message.replace("[STATUE] Oruo the Omniscient: ", "");
        for (String oruoMsg : uselessTriviaMessages) {
            if (trimmedMessage.equals(oruoMsg)) return true;
        }

        return false;
    }

    private static boolean checkDungeonTrapMessage(String message) {
        if (!Config.get().hideTrapMessages) return false;
        return trapMessages.matcher(message).find();
    }

    private static boolean checkAbilityMessages(String message) {
        if (!Config.get().hideUselessAbilities) return false;

        return switch (message) {
            case "Guided Sheep is now available!" -> true;
            case "Used Healing Circle!" -> true;
            case "Healing Circle is now available!" -> true;

            default -> false;
        };
    }


    private static boolean checkBlessingMessage(String message) {
        if (!Config.get().hideBlessingMessages) return false;

        if (message.startsWith("A Blessing of") || message.startsWith("DUNGEON BUFF!")) return true;

        if (blessingName.matcher(message).find()) return true;
        if (blessingContent.matcher(message).find()) return true;

        return false;
    }

    private static boolean checkWatcherMessages(String message) {
        if (!Config.get().hideBloodMessages) return false;
        return message.startsWith("[BOSS] The Watcher") || message.equals("A shiver runs down your spine...");
    }

    private static boolean checkMiscMessages(String message) {
        if (!Config.get().dungeonChatCleanup) return false;

        return switch (message) {
            case "Lost Adventurer used Dragon's Breath on you!" -> true;
            case "The Frozen Adventurer used Ice Spray on you!" -> true;
            case "RIGHT CLICK on the BLOOD DOOR to open it. This key can only be used to open 1 door!" -> true;
            case "RIGHT CLICK on a WITHER door to open it. This key can only be used to open 1 door!" -> true;


            default -> false;
        };
    }

    private static boolean checkMortMessages(String message) {
        if (!Config.get().hideMortMessages) return false;

        return switch (message) {
            case "[CHAT] §e[NPC] §bMort§f: Good luck." -> true;
            case "§e[NPC] §bMort§f: You should find it useful if you get lost." -> true;
            case "[CHAT] §e[NPC] §bMort§f: Here, I found this map when I first entered the dungeon." -> true;

            default -> false;
        };
    }
}
